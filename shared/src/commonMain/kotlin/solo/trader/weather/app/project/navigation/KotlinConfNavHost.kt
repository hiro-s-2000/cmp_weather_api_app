package solo.trader.weather.app.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import kotlinx.coroutines.channels.Channel
import solo.trader.weather.app.project.LocalFlags
import solo.trader.weather.app.project.URLs
import solo.trader.weather.app.project.screens.AboutAppScreen
import solo.trader.weather.app.project.screens.AboutConference
import solo.trader.weather.app.project.screens.AppPrivacyNotice
import solo.trader.weather.app.project.screens.AppPrivacyNoticePrompt
import solo.trader.weather.app.project.screens.AppTermsOfUse
import solo.trader.weather.app.project.screens.CodeOfConduct
import solo.trader.weather.app.project.screens.DeveloperMenuScreen
import solo.trader.weather.app.project.screens.LicensesScreen
import solo.trader.weather.app.project.screens.MainScreen
import solo.trader.weather.app.project.screens.NestedMapScreen
import solo.trader.weather.app.project.screens.PartnerDetailScreen
import solo.trader.weather.app.project.screens.PartnersScreen
import solo.trader.weather.app.project.screens.SessionScreen
import solo.trader.weather.app.project.screens.SettingsScreen
import solo.trader.weather.app.project.screens.SingleLicenseScreen
import solo.trader.weather.app.project.screens.SpeakerDetailScreen
import solo.trader.weather.app.project.screens.StartNotificationsScreen
import solo.trader.weather.app.project.screens.VisitorPrivacyNotice
import solo.trader.weather.app.project.screens.VisitorTermsOfUse
import solo.trader.weather.app.project.utils.getStoreUrl

private val notificationNavRequests = Channel<AppRoute>(capacity = 1)

@Composable
private fun NotificationHandler(backStack: MutableList<AppRoute>) {
    LaunchedEffect(Unit) {
        while (true) {
            backStack.add(notificationNavRequests.receive())
        }
    }
}

@Composable
internal fun KotlinConfNavHost(isOnboardingComplete: Boolean) {
    val backstack: MutableList<AppRoute> =
        rememberSerializable(serializer = SnapshotStateListSerializer()) {
            val startDestination = if (isOnboardingComplete) MainScreen else StartPrivacyNoticeScreen
            mutableStateListOf(startDestination)
        }

    // TODO Integrate with browser navigation here https://github.com/JetBrains/kotlinconf-app/issues/557

    NotificationHandler(backstack)

    val onBack = {
        if (backstack.size > 1) backstack.removeLastOrNull()
    }

    NavDisplay(
        backStack = backstack,
        onBack = onBack,
        entryProvider = entryProvider {
            screens(backstack, onBack)
        },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
    )
}

private fun EntryProviderScope<AppRoute>.screens(
    backStack: MutableList<AppRoute>,
    onBack: () -> Unit,
) {
    entry<StartPrivacyNoticeScreen> {
        val skipNotifications = LocalFlags.current.supportsNotifications.not()
        AppPrivacyNoticePrompt(
            onRejectNotice = {
                if (skipNotifications) {
                    backStack.clear()
                    backStack.add(MainScreen)
                } else {
                    backStack.add(StartNotificationsScreen)
                }
            },
            onAcceptNotice = {
                if (skipNotifications) {
                    backStack.clear()
                    backStack.add(MainScreen)
                } else {
                    backStack.add(StartNotificationsScreen)
                }
            },
            onAppTermsOfUse = { backStack.add(AppTermsOfUseScreen) },
            confirmationRequired = false,
        )
    }
    entry<StartNotificationsScreen> {
        StartNotificationsScreen(
            onDone = {
                backStack.clear()
                backStack.add(MainScreen)
            }
        )
    }

    entry<MainScreen> {
        MainScreen(onNavigate = { backStack.add(it) })
    }
    entry<SpeakerDetailScreen> {
        SpeakerDetailScreen(
            speakerId = it.speakerId,
            onBack = onBack,
            onSession = { backStack.add(SessionScreen(it)) },
        )
    }
    entry<SessionScreen> {
        val urlHandler = LocalUriHandler.current
        SessionScreen(
            sessionId = it.sessionId,
            openedForFeedback = it.openedForFeedback,
            onBack = onBack,
            onPrivacyNoticeNeeded = { backStack.add(AppPrivacyNoticePrompt) },
            onSpeaker = { speakerId -> backStack.add(SpeakerDetailScreen(speakerId)) },
            onWatchVideo = { videoUrl -> urlHandler.openUri(videoUrl) },
            onNavigateToMap = { roomName ->
                backStack.add(NestedMapScreen(roomName))
            },
        )
    }

    entry<AboutAppScreen> {
        val uriHandler = LocalUriHandler.current
        AboutAppScreen(
            onBack = onBack,
            onGitHubRepo = { uriHandler.openUri(URLs.GITHUB_REPO) },
            onRateApp = { getStoreUrl()?.let { uriHandler.openUri(it) } },
            onPrivacyNotice = { backStack.add(AppPrivacyNoticeScreen) },
            onTermsOfUse = { backStack.add(AppTermsOfUseScreen) },
            onLicenses = { backStack.add(LicensesScreen) },
            onJunie = { uriHandler.openUri(URLs.JUNIE_LANDING_PAGE) },
            onDeveloperMenu = { backStack.add(DeveloperMenuScreen) },
        )
    }
    entry<LicensesScreen> {
        LicensesScreen(
            onLicenseClick = { licenseName, licenseText ->
                backStack.add(SingleLicenseScreen(licenseName, licenseText))
            },
            onBack = onBack,
        )
    }
    entry<SingleLicenseScreen> {
        SingleLicenseScreen(
            licenseName = it.licenseName,
            licenseContent = it.licenseText,
            onBack = onBack,
        )
    }
    entry<AboutConferenceScreen> {
        val urlHandler = LocalUriHandler.current
        AboutConference(
            onPrivacyNotice = { backStack.add(VisitorPrivacyNoticeScreen) },
            onGeneralTerms = { backStack.add(TermsOfUseScreen) },
            onWebsiteLink = { urlHandler.openUri(URLs.KOTLINCONF_HOMEPAGE) },
            onBack = onBack,
            onSpeaker = { speakerId -> backStack.add(SpeakerDetailScreen(speakerId)) },
        )
    }
    entry<CodeOfConductScreen> {
        CodeOfConduct(onBack = onBack)
    }
    entry<SettingsScreen> {
        SettingsScreen(onBack = onBack)
    }
    entry<VisitorPrivacyNoticeScreen> {
        VisitorPrivacyNotice(onBack = onBack)
    }
    entry<AppPrivacyNoticeScreen> {
        AppPrivacyNotice(
            onBack = onBack,
            onAppTermsOfUse = { backStack.add(AppTermsOfUseScreen) },
        )
    }
    entry<TermsOfUseScreen> {
        VisitorTermsOfUse(
            onBack = onBack,
            onCodeOfConduct = { backStack.add(CodeOfConductScreen) },
            onVisitorPrivacyNotice = { backStack.add(VisitorPrivacyNoticeScreen) },
        )
    }
    entry<AppTermsOfUseScreen> {
        AppTermsOfUse(
            onBack = onBack,
            onAppPrivacyNotice = {
                backStack.add(AppPrivacyNoticeScreen)
            },
        )
    }
    entry<PartnersScreen> {
        PartnersScreen(
            onBack = onBack,
            onPartnerDetail = { partnerId ->
                backStack.add(PartnerDetailScreen(partnerId))
            }
        )
    }
    entry<PartnerDetailScreen> {
        PartnerDetailScreen(
            partnerId = it.partnerId,
            onBack = onBack,
        )
    }

    entry<AppPrivacyNoticePrompt> {
        AppPrivacyNoticePrompt(
            onRejectNotice = onBack,
            onAcceptNotice = onBack,
            onAppTermsOfUse = { backStack.add(AppTermsOfUseScreen) },
            confirmationRequired = true,
        )
    }

    entry<DeveloperMenuScreen> {
        DeveloperMenuScreen(onBack = onBack)
    }

    entry<NestedMapScreen> {
        NestedMapScreen(
            roomName = it.roomName,
            onBack = onBack,
        )
    }
}
