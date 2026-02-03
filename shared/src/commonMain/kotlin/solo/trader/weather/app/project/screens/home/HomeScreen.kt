package solo.trader.weather.app.project.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import solo.trader.weather.app.project.common.SearchCity
import solo.trader.weather.app.project.generated.resources.Res.string
import solo.trader.weather.app.project.generated.resources.api_access_click
import solo.trader.weather.app.project.generated.resources.api_access_hokkaido
import solo.trader.weather.app.project.generated.resources.api_access_hyogo
import solo.trader.weather.app.project.generated.resources.api_access_idle
import solo.trader.weather.app.project.generated.resources.api_access_loading
import solo.trader.weather.app.project.generated.resources.api_access_oita
import solo.trader.weather.app.project.generated.resources.api_access_tokyo
import solo.trader.weather.app.project.generated.resources.local_access_cache_error
import solo.trader.weather.app.project.ui.components.SearchButton
import solo.trader.weather.app.project.ui.components.StateCheckText
import solo.trader.weather.app.project.ui.components.WeatherColumn

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel(), onWeather: (SearchCity) -> Unit) {
    val homeState by viewModel.state.collectAsStateWithLifecycle()

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.cacheDayCheck()
    }

    WeatherColumn {
        SearchCity.entries.forEach { city ->
            SearchButton(
                onWeather = { onWeather(city) },
                res = when (city) {
                    SearchCity.Hokkaido -> string.api_access_hokkaido
                    SearchCity.Tokyo -> string.api_access_tokyo
                    SearchCity.Hyogo -> string.api_access_hyogo
                    SearchCity.Oita -> string.api_access_oita
                    else -> string.api_access_click
                }
            )
        }

        when (val state = homeState) {
            HomeState.Loading -> StateCheckText(res = string.api_access_loading)
            HomeState.Idle -> StateCheckText(res = string.api_access_idle)
            HomeState.Error -> StateCheckText(res = string.local_access_cache_error, isError = true)
            is HomeState.Done -> StateCheckText(
                text = "${state.data.joinToString { it.city.name }}の、\nCacheデータが保存されています。"
            )
        }
    }
}
