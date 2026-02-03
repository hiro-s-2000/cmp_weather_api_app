package solo.trader.weather.app.project.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun RetryButton(onRetry: () -> Unit, res: StringResource) {
    Button(
        modifier = Modifier.width(200.dp).height(50.dp),
        onClick = onRetry
    ) { Text(stringResource(res)) }
}