package solo.trader.weather.app.project.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun WeatherText(res: StringResource) {
    Text(stringResource(res))
}