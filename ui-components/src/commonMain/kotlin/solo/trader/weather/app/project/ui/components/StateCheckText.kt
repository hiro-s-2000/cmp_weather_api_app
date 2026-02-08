package solo.trader.weather.app.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import solo.trader.weather.app.project.ui.theme.WeatherAppTheme

@Composable
fun StateCheckText(
    text: String,
    isError: Boolean = false,
    isWeatherScreen: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(
                if (isError) WeatherAppTheme.colors.errorBackground
                else WeatherAppTheme.colors.tileBackground
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = if (isError) WeatherAppTheme.colors.errorText else WeatherAppTheme.colors.tileText,
            modifier = Modifier.fillMaxWidth()
                .background(
                    if (isError) WeatherAppTheme.colors.errorBackground
                    else WeatherAppTheme.colors.tileBackground
                )
                .height(if(isWeatherScreen) 25.dp else 45.dp)
        )
    }
}

@Composable
fun StateCheckText(
    res: StringResource,
    isError: Boolean = false,
    isWeatherScreen: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .background(
                if (isError) WeatherAppTheme.colors.errorBackground
                else WeatherAppTheme.colors.tileBackground
            )
            .height(if(isWeatherScreen) 25.dp else 45.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(res),
            textAlign = TextAlign.Center,
            color = if (isError) WeatherAppTheme.colors.errorText else WeatherAppTheme.colors.tileText
        )
    }
}