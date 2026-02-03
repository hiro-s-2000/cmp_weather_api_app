package solo.trader.weather.app.project.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import solo.trader.weather.app.project.MarkdownScreenWithTitle
import solo.trader.weather.app.project.generated.resources.Res
import solo.trader.weather.app.project.generated.resources.general_terms
import solo.trader.weather.app.project.generated.resources.visitors_terms_title

@Composable
fun VisitorTermsOfUse(
    onBack: () -> Unit,
    onCodeOfConduct: () -> Unit,
    onVisitorPrivacyNotice: () -> Unit,
) {
    MarkdownScreenWithTitle(
        title = stringResource(Res.string.general_terms),
        header = stringResource(Res.string.visitors_terms_title),
        loadText = {
            @OptIn(ExperimentalResourceApi::class)
            Res.readBytes("files/visitors-terms.md")
        },
        onBack = onBack,
        onCustomUriClick = { uri ->
            when (uri) {
                "code-of-conduct.md" -> onCodeOfConduct()
                "visitors-privacy-notice.md" -> onVisitorPrivacyNotice()
            }
        },
    )
}
