package solo.trader.weather.app.project.screens.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.GeolocatorResult
import io.ktor.client.plugins.ResponseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import solo.trader.weather.app.project.WeatherService
import solo.trader.weather.app.project.common.Forecast
import solo.trader.weather.app.project.common.SearchCity
import solo.trader.weather.app.project.common.toEntity
import solo.trader.weather.app.project.local.entity.toDomain
import solo.trader.weather.app.project.repository.WeatherLocalRepository
import solo.trader.weather.app.project.storage.ApplicationStorage

sealed class WeatherState {
    object Idle : WeatherState()
    object Loading : WeatherState()
    data class Done(val data: Forecast?) : WeatherState()
    data class Error(val message: String?) : WeatherState()
}

class WeatherViewModel(
    private val service: WeatherService,
    private val repository: WeatherLocalRepository,
    private val geolocator: Geolocator,
    private val storage: ApplicationStorage
) : ViewModel() {

    // state
    private val _state = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val state: StateFlow<WeatherState> = _state.asStateFlow()
    var locationState by mutableStateOf("未取得")

    // open function
    fun fetchWeather(searchCity: SearchCity) = viewModelScope.launch {
        checkCache(cName = searchCity.cityName.ifEmpty { storage.getCurrentCity() })
    }

    fun retry(searchCity: SearchCity) {
        if (searchCity.cityName.isNotBlank()) {
            getWeatherByPlace(searchCity.q)
        } else {
            fetchLocation()
        }
    }

    // private function
    private suspend fun checkCache(cName: String) = repository.getDataByCityName(cName)?.let {
        _state.value = WeatherState.Done(it.toDomain())
    } ?: run {
        if (cName.isNotBlank()) getWeatherByPlace(cName)
        else fetchLocation()
    }

    private fun getWeatherByPlace(cityName: String = "") {
        viewModelScope.launch {
            _state.value = WeatherState.Loading
            service.getWeatherByPlace(cityName = cityName)
                .onSuccess { data ->
                    data?.let { insertCache(data) }
                    _state.value = WeatherState.Done(data)
                }
                .onFailure { error -> errorImp(error) }
        }
    }

    private fun getWeatherByLocation(lat: String = "", lon: String = "") {
        viewModelScope.launch {
            _state.value = WeatherState.Loading
            service.getWeatherByLocation(lat = lat, lon = lon)
                .onSuccess { data ->
                    data?.let {
                        storage.setCurrentCity(it.city.name)
                        insertCache(data)
                    }
                    _state.value = WeatherState.Done(data)
                }
                .onFailure { error -> errorImp(error) }
        }
    }

    private fun insertCache(data: Forecast) = viewModelScope.launch { repository.insert(data.toEntity()) }

    private fun errorImp(error: Throwable) {
        when (error) {
            is ResponseException -> {
                _state.value = WeatherState.Error(
                    "code : ${error.response.status.value}" +
                        ", description : ${error.response.status.description}"
                )
            }

            is Exception -> Logger.w { "error Exception ($error)" }
        }
    }

    private fun fetchLocation() {
        viewModelScope.launch {
            when (val result = geolocator.current()) {
                is GeolocatorResult.Success -> {
                    val coords = result.data.coordinates
                    locationState = "緯度: ${coords.latitude}, 経度: ${coords.longitude}"
                    getWeatherByLocation(coords.latitude.toString(), coords.longitude.toString())
                }

                is GeolocatorResult.Error -> {
                    locationState = "エラー: ${result.message}"
                }

                else -> {
                    locationState = "取得中..."
                }
            }
        }
    }
}
