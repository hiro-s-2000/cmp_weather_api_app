package solo.trader.weather.app.project.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import solo.trader.weather.app.project.ScrollToTopHandler
import solo.trader.weather.app.project.generated.resources.Res
import solo.trader.weather.app.project.generated.resources.kodee_privacy
import solo.trader.weather.app.project.generated.resources.privacy_notice_accept
import solo.trader.weather.app.project.generated.resources.privacy_notice_back
import solo.trader.weather.app.project.generated.resources.privacy_notice_description
import solo.trader.weather.app.project.generated.resources.privacy_notice_read_action
import solo.trader.weather.app.project.generated.resources.privacy_notice_reject
import solo.trader.weather.app.project.generated.resources.privacy_notice_title
import solo.trader.weather.app.project.ui.components.Action
import solo.trader.weather.app.project.ui.components.ActionSize
import solo.trader.weather.app.project.ui.components.Button
import solo.trader.weather.app.project.ui.components.Divider
import solo.trader.weather.app.project.ui.components.MainHeaderTitleBar
import solo.trader.weather.app.project.ui.components.MarkdownView
import solo.trader.weather.app.project.ui.components.Text
import solo.trader.weather.app.project.ui.components.TopMenuButton
import solo.trader.weather.app.project.ui.generated.resources.UiRes
import solo.trader.weather.app.project.ui.generated.resources.arrow_left_24
import solo.trader.weather.app.project.ui.generated.resources.arrow_right_24
import solo.trader.weather.app.project.ui.theme.KotlinConfTheme
import solo.trader.weather.app.project.utils.FadingAnimationSpec
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppPrivacyNoticePrompt(
    onRejectNotice: () -> Unit,
    onAcceptNotice: () -> Unit,
    onAppTermsOfUse: () -> Unit,
    confirmationRequired: Boolean,
    viewModel: PrivacyNoticeViewModel = koinViewModel(),
) {
    var detailsVisible by rememberSaveable { mutableStateOf(false) }
    val noticeState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(noticeState) {
        if (noticeState is PrivacyNoticeState.Done) {
            onAcceptNotice()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(color = KotlinConfTheme.colors.mainBackground)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        AnimatedContent(
            targetState = detailsVisible,
            modifier = Modifier.weight(1f),
            transitionSpec = { FadingAnimationSpec }
        ) { detailsVis ->
            if (detailsVis) {
                Column {
                    MainHeaderTitleBar(
                        stringResource(Res.string.privacy_notice_title),
                        startContent = {
                            TopMenuButton(
                                icon = UiRes.drawable.arrow_left_24,
                                contentDescription = stringResource(Res.string.privacy_notice_back),
                                onClick = { detailsVisible = false },
                            )
                        }
                    )
                    Divider(
                        thickness = 1.dp,
                        color = KotlinConfTheme.colors.strokePale,
                    )
                    val scrollState = rememberScrollState()
                    ScrollToTopHandler(scrollState)
                    MarkdownView(
                        loadText = {
                            @OptIn(ExperimentalResourceApi::class)
                            Res.readBytes("files/app-privacy-notice.md")
                        },
                        modifier = Modifier.padding(horizontal = 12.dp).verticalScroll(scrollState),
                        onCustomUriClick = { uri ->
                            if (uri == "app-terms.md") {
                                onAppTermsOfUse()
                            }
                        },
                    )
                    Spacer(Modifier.weight(1f))
                    Divider(
                        thickness = 1.dp,
                        color = KotlinConfTheme.colors.strokePale,
                    )
                }
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Image(
                        imageVector = vectorResource(Res.drawable.kodee_privacy),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                            .size(160.dp)
                    )
                    Text(
                        stringResource(Res.string.privacy_notice_title),
                        style = KotlinConfTheme.typography.h1
                    )
                    Text(
                        stringResource(Res.string.privacy_notice_description),
                        color = KotlinConfTheme.colors.longText,
                    )
                    Action(
                        stringResource(Res.string.privacy_notice_read_action),
                        icon = UiRes.drawable.arrow_right_24,
                        size = ActionSize.Large,
                        enabled = true,
                        onClick = { detailsVisible = true }
                    )
                }
                Spacer(Modifier.weight(1f))
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            Button(
                label = stringResource(Res.string.privacy_notice_reject),
                onClick = { onRejectNotice() },
                enabled = noticeState !is PrivacyNoticeState.Loading,
            )
            Button(
                label = stringResource(Res.string.privacy_notice_accept),
                onClick = { viewModel.acceptPrivacyNotice(confirmationRequired) },
                modifier = Modifier.weight(1f),
                primary = true,
                enabled = noticeState !is PrivacyNoticeState.Loading,
            )
        }
    }
}
