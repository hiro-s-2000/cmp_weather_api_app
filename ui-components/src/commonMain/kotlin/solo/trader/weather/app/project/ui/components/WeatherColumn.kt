package solo.trader.weather.app.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import solo.trader.weather.app.project.ui.theme.WeatherAppTheme

@Composable
fun WeatherColumn(compose: @Composable () -> Unit) = Column(
    modifier = Modifier.fillMaxSize().background(WeatherAppTheme.colors.mainBackground),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    content = { compose() }
)
