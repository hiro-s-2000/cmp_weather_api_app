package solo.trader.weather.app.project.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import solo.trader.weather.app.project.ScrollToTopHandler
import solo.trader.weather.app.project.SessionId
import solo.trader.weather.app.project.SpeakerId
import solo.trader.weather.app.project.generated.resources.Res
import solo.trader.weather.app.project.generated.resources.schedule_in_x_minutes
import solo.trader.weather.app.project.generated.resources.speaker_detail_error_not_found
import solo.trader.weather.app.project.generated.resources.speaker_detail_title
import solo.trader.weather.app.project.toEmotion
import solo.trader.weather.app.project.ui.components.Divider
import solo.trader.weather.app.project.ui.components.MainHeaderTitleBar
import solo.trader.weather.app.project.ui.components.MajorError
import solo.trader.weather.app.project.ui.components.SpeakerAvatar
import solo.trader.weather.app.project.ui.components.TalkCard
import solo.trader.weather.app.project.ui.components.TalkStatus
import solo.trader.weather.app.project.ui.components.Text
import solo.trader.weather.app.project.ui.components.TopMenuButton
import solo.trader.weather.app.project.ui.generated.resources.UiRes
import solo.trader.weather.app.project.ui.generated.resources.arrow_left_24
import solo.trader.weather.app.project.ui.generated.resources.main_header_back
import solo.trader.weather.app.project.ui.theme.KotlinConfTheme
import solo.trader.weather.app.project.utils.FadingAnimationSpec
import solo.trader.weather.app.project.utils.bottomInsetPadding
import solo.trader.weather.app.project.utils.topInsetPadding
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SpeakerDetailScreen(
    speakerId: SpeakerId,
    onBack: () -> Unit,
    onSession: (SessionId) -> Unit,
    viewModel: SpeakerDetailViewModel = koinViewModel { parametersOf(speakerId) }
) {
    val speaker = viewModel.speaker.collectAsStateWithLifecycle().value
    val sessions = viewModel.sessions.collectAsStateWithLifecycle().value

    Column(
        Modifier
            .fillMaxSize()
            .background(color = KotlinConfTheme.colors.mainBackground)
            .padding(topInsetPadding())
    ) {
        MainHeaderTitleBar(
            title = stringResource(Res.string.speaker_detail_title),
            startContent = {
                TopMenuButton(
                    icon = UiRes.drawable.arrow_left_24,
                    contentDescription = stringResource(UiRes.string.main_header_back),
                    onClick = onBack,
                )
            }
        )
        Divider(thickness = 1.dp, color = KotlinConfTheme.colors.strokePale)

        AnimatedContent(
            targetState = speaker,
            contentKey = { speaker != null },
            transitionSpec = { FadingAnimationSpec },
            modifier = Modifier.fillMaxSize().weight(1f)
        ) { currentSpeaker ->
            if (currentSpeaker == null) {
                MajorError(
                    message = stringResource(Res.string.speaker_detail_error_not_found),
                    modifier = Modifier.fillMaxSize(),
                )
            } else {
                val scrollState = rememberScrollState()
                ScrollToTopHandler(scrollState)
                Column(
                    modifier = Modifier
                        .verticalScroll(scrollState)
                        .padding(vertical = 16.dp, horizontal = 12.dp)
                        .padding(bottomInsetPadding()),
                ) {
                    Text(
                        text = currentSpeaker.name,
                        style = KotlinConfTheme.typography.h2,
                        color = KotlinConfTheme.colors.primaryText,
                        selectable = true,
                        modifier = Modifier.semantics {
                            heading()
                        }
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = currentSpeaker.position,
                        style = KotlinConfTheme.typography.text2,
                        color = KotlinConfTheme.colors.secondaryText,
                        selectable = true,
                    )

                    Spacer(Modifier.height(16.dp))

                    SpeakerAvatar(
                        photoUrl = currentSpeaker.photoUrl,
                        modifier = Modifier.widthIn(max = 300.dp)
                            .aspectRatio(1f)
                    )

                    Spacer(Modifier.height(24.dp))

                    Text(
                        text = currentSpeaker.description,
                        style = KotlinConfTheme.typography.text2,
                        color = KotlinConfTheme.colors.longText,
                        selectable = true,
                    )

                    Spacer(Modifier.height(16.dp))

                    sessions.forEach { session ->
                        TalkCard(
                            title = session.title,
                            titleHighlights = emptyList(),
                            bookmarked = session.isFavorite,
                            onBookmark = { isBookmarked -> viewModel.onBookmark(session.id, isBookmarked) },
                            tags = session.tags,
                            tagHighlights = emptyList(),
                            speakers = session.speakerLine,
                            speakerHighlights = emptyList(),
                            location = session.locationLine,
                            lightning = session.isLightning,
                            time = session.fullTimeline,
                            timeNote = session.startsInMinutes?.let { count ->
                                stringResource(Res.string.schedule_in_x_minutes, count)
                            },
                            status = TalkStatus.Upcoming,
                            initialEmotion = session.vote?.toEmotion(),
                            feedbackEnabled = false, // Feedback not enabled on this screen
                            userSignedIn = false, // Feedback not enabled on this screen
                            onSubmitFeedback = { }, // Feedback not enabled on this screen
                            onSubmitFeedbackWithComment = { _, _ -> }, // Feedback not enabled on this screen
                            onRequestFeedbackWithComment = null, // Feedback not enabled on this screen
                            onClick = { onSession(session.id) },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        )
                    }
                }
            }
        }
    }
}
