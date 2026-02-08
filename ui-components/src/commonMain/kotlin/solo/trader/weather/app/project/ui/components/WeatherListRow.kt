package solo.trader.weather.app.project.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import solo.trader.weather.app.project.common.ForecastItem
import solo.trader.weather.app.project.ui.preview.data.TestObject.testForecastItem
import solo.trader.weather.app.project.ui.theme.WeatherAppTheme

@Composable
fun WeatherListRow(item: ForecastItem, no: Int) {
    val iconId = item.weather[0].icon
    val temp = item.main.temp
    val dt = item.dt
    Row(
        modifier = Modifier.height(60.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Text(
            text = "No.$no",
            modifier = Modifier.width(50.dp),
            textAlign = TextAlign.Center
        )
        VerticalDivider(modifier = Modifier.height(60.dp))
        NetworkImage(
            modifier = Modifier.size(60.dp),
            photoUrl = getIconUrl(iconId)
        )
        VerticalDivider(modifier = Modifier.height(60.dp))
        Text(
            text = " $temp ",
            modifier = Modifier.width(50.dp),
            textAlign = TextAlign.Center
        )
        VerticalDivider(modifier = Modifier.height(60.dp))
        Text(
            text = "$dt",
            modifier = Modifier.weight(1.0f).padding(start = 10.dp)
        )
    }
}


@Composable
@Preview(backgroundColor = 0xFFFFFF, showBackground = true)
private fun WeatherListRowPreview() {
    val test = testForecastItem(index = 1)
    WeatherAppTheme {
        WeatherListRow(test, 0)
    }
}


private fun getIconUrl(iconId: String) = "https://openweathermap.org/payload/api/media/file/$iconId.png"