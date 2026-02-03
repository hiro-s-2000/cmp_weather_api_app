package solo.trader.weather.app.project.screens.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import solo.trader.weather.app.project.common.SearchCity
import solo.trader.weather.app.project.generated.resources.Res.string
import solo.trader.weather.app.project.generated.resources.api_access_error
import solo.trader.weather.app.project.generated.resources.api_access_idle
import solo.trader.weather.app.project.generated.resources.api_access_loading
import solo.trader.weather.app.project.generated.resources.api_access_retry
import solo.trader.weather.app.project.ui.components.ForecastLazyColumn
import solo.trader.weather.app.project.ui.components.RetryButton
import solo.trader.weather.app.project.ui.components.StateCheckText
import solo.trader.weather.app.project.ui.components.WeatherColumn

@Composable
fun WeatherContentScreen(viewModel: WeatherViewModel = koinViewModel(), searchCity: SearchCity) {
    val weatherState by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) { viewModel.fetchWeather(searchCity = searchCity) }

    WeatherColumn {
        when (val state = weatherState) {
            WeatherState.Loading -> {
                StateCheckText(stringResource(string.api_access_loading), isWeatherScreen = true)
                WeatherColumn {
                    Box(modifier = Modifier.size(64.dp).padding(16.dp)) {
                        CircularProgressIndicator(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant,
                        )
                    }
                }
            }

            WeatherState.Idle -> StateCheckText(stringResource(string.api_access_idle), isWeatherScreen = true)
            is WeatherState.Error -> {
                StateCheckText("${stringResource(string.api_access_error)}\n (${state.message})")
                WeatherColumn {
                    RetryButton(onRetry = { viewModel.retry(searchCity = searchCity) }, res = string.api_access_retry)
                }
            }

            is WeatherState.Done -> {
                state.data?.let {
                    StateCheckText("${it.list.size}のデータの取得に成功しました。", isWeatherScreen = true)
                    WeatherColumn {
                        ForecastLazyColumn(it)
                    }
                }
            }
        }
    }
}
