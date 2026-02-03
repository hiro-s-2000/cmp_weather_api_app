package solo.trader.weather.app.project.screens

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import solo.trader.weather.app.project.MarkdownScreenWithTitle
import solo.trader.weather.app.project.generated.resources.Res
import solo.trader.weather.app.project.generated.resources.privacy_notice_for_visitors
import solo.trader.weather.app.project.generated.resources.privacy_notice_for_visitors_title

@OptIn(ExperimentalResourceApi::class)
@Composable
fun VisitorPrivacyNotice(onBack: () -> Unit) {
    MarkdownScreenWithTitle(
        title = stringResource(Res.string.privacy_notice_for_visitors),
        header = stringResource(Res.string.privacy_notice_for_visitors_title),
        loadText = { Res.readBytes("files/visitors-privacy-notice.md") },
        onBack = onBack
    )
}
