package solo.trader.weather.app.project.ui.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import solo.trader.weather.app.project.common.Forecast
import solo.trader.weather.app.project.ui.preview.data.TestObject.testForecast
import solo.trader.weather.app.project.ui.theme.WeatherAppTheme

@Composable
fun ForecastLazyColumn(forecast: Forecast) {
    LazyColumn {
        items(forecast.list.size) { no ->
            WeatherListRow(forecast.list[no], no + 1)
        }
    }
}

@Composable
@Preview
private fun ForecastLazyColumnPreview() {
    WeatherAppTheme {
        WeatherColumn {
            ForecastLazyColumn(forecast = testForecast)
        }
    }
}
