package solo.trader.weather.app.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import solo.trader.weather.app.project.common.Theme
import solo.trader.weather.app.project.storage.ApplicationStorage
import kotlin.uuid.ExperimentalUuidApi

class WeatherService(
    private val client: APIClient,
    private val storage: ApplicationStorage,
    private val scope: CoroutineScope
) {
    init {
        storage.ensureCurrentVersion()
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getWeatherByLocation(lat: String = "", lon: String = "") =
        client.getWeatherByLocation(lat = lat, lon = lon)

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getWeatherByPlace(cityName: String) = client.getWeatherByPlace(cityName = cityName)

    fun getTheme(): Flow<Theme> = storage.getTheme()

    fun setTheme(theme: Theme) {
        scope.launch { storage.setTheme(theme) }
    }
}
