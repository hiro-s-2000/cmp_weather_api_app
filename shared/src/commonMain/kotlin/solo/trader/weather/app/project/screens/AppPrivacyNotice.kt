package solo.trader.weather.app.project.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import solo.trader.weather.app.project.MarkdownScreenWithTitle
import solo.trader.weather.app.project.generated.resources.Res
import solo.trader.weather.app.project.generated.resources.app_privacy_notice_header
import solo.trader.weather.app.project.generated.resources.app_privacy_notice_title

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AppPrivacyNotice(
    onBack: () -> Unit,
    onAppTermsOfUse: () -> Unit,
) {
    MarkdownScreenWithTitle(
        title = stringResource(Res.string.app_privacy_notice_title),
        header = stringResource(Res.string.app_privacy_notice_header),
        loadText = { Res.readBytes("files/app-privacy-notice.md") },
        onBack = onBack,
        onCustomUriClick = { uri ->
            if (uri == "app-terms.md") {
                onAppTermsOfUse()
            }
        },
    )
}
