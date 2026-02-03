package solo.trader.weather.app.project.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SearchButton(onWeather: () -> Unit, res: StringResource) {
    Button(
        modifier = Modifier.fillMaxWidth().height(80.dp).padding(10.dp),
        onClick = onWeather
    ) { Text(stringResource(res)) }
}