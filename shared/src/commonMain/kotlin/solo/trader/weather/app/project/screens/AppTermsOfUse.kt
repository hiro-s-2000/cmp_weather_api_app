package solo.trader.weather.app.project.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import solo.trader.weather.app.project.MarkdownScreenWithTitle
import solo.trader.weather.app.project.generated.resources.Res
import solo.trader.weather.app.project.generated.resources.app_terms
import solo.trader.weather.app.project.generated.resources.app_terms_header

@Composable
fun AppTermsOfUse(
    onBack: () -> Unit,
    onAppPrivacyNotice: () -> Unit,
) {
    MarkdownScreenWithTitle(
        title = stringResource(Res.string.app_terms),
        header = stringResource(Res.string.app_terms_header),
        loadText = {
            @OptIn(ExperimentalResourceApi::class)
            Res.readBytes("files/app-terms.md")
        },
        onBack = onBack,
        onCustomUriClick = { uri ->
            if (uri == "app-privacy-notice.md") {
                onAppPrivacyNotice()
            }
        }
    )
}
