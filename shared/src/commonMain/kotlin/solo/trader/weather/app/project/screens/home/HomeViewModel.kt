package solo.trader.weather.app.project.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import solo.trader.weather.app.project.common.Forecast
import solo.trader.weather.app.project.local.entity.toDomainList
import solo.trader.weather.app.project.repository.WeatherLocalRepository
import solo.trader.weather.app.project.storage.ApplicationStorage
import kotlin.time.Clock
import kotlin.time.Instant

sealed class HomeState {
    object Idle : HomeState()
    object Loading : HomeState()
    data class Done(val data: List<Forecast>) : HomeState()
    object Error : HomeState()
}

class HomeViewModel(
    private val repository: WeatherLocalRepository, private val storage: ApplicationStorage
) : ViewModel() {

    // state
    private val _state = MutableStateFlow<HomeState>(HomeState.Idle)
    val state: StateFlow<HomeState> = _state.asStateFlow()

    // open function
    fun cacheDayCheck() {
        viewModelScope.launch {
            val now = Clock.System.now().toEpochMilliseconds() // Get current Instant
            val today: LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())
            val localDateTime = storage.getDateTime()
            val instant = Instant.fromEpochMilliseconds(localDateTime)
            val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
            if (today != localDate) {
                storage.setDateTime(now)
                storage.setCurrentCity("")
                repository.deleteAll()
            }
            getCache()
        }
    }

    // private function
    private fun getCache() {
        viewModelScope.launch {
            _state.value = HomeState.Loading
            repository.getData()?.let { entity ->
                _state.value = if (entity.isNotEmpty()) HomeState.Done(entity.toDomainList())
                else HomeState.Error
            }
        }
    }
}
