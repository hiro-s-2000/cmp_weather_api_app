package solo.trader.weather.app.project.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import solo.trader.weather.app.project.common.SearchCity

@Serializable
sealed interface AppRoute

@Serializable
@SerialName("Home")
data object HomeScreen : AppRoute

@Serializable
@SerialName("Weather")
data class WeatherScreen(val searchCity: SearchCity) : AppRoute
