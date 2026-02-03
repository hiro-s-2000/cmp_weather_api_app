package solo.trader.weather.app.project.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import solo.trader.weather.app.project.ui.theme.KotlinConfTheme
import solo.trader.weather.app.project.ui.theme.PreviewHelper

@Composable
fun SectionTitle(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        style = KotlinConfTheme.typography.h2,
        color = KotlinConfTheme.colors.primaryText,
    )
}

@Preview
@Composable
internal fun SectionTitlePreview() {
    PreviewHelper {
        SectionTitle("Section title")
        SectionTitle("7:30")
    }
}
