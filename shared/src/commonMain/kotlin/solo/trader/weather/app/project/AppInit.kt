package solo.trader.weather.app.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import solo.trader.weather.app.project.screens.AboutConferenceViewModel
import solo.trader.weather.app.project.screens.LicensesViewModel
import solo.trader.weather.app.project.screens.PrivacyNoticeViewModel
import solo.trader.weather.app.project.screens.ScheduleViewModel
import solo.trader.weather.app.project.screens.SessionViewModel
import solo.trader.weather.app.project.screens.SettingsViewModel
import solo.trader.weather.app.project.screens.SpeakerDetailViewModel
import solo.trader.weather.app.project.screens.SpeakersViewModel
import solo.trader.weather.app.project.screens.StartNotificationsViewModel
import solo.trader.weather.app.project.storage.ApplicationStorage
import solo.trader.weather.app.project.storage.MultiplatformSettingsStorage
import solo.trader.weather.app.project.utils.BufferedDelegatingLogger
import solo.trader.weather.app.project.utils.DebugLogger
import solo.trader.weather.app.project.utils.Logger
import solo.trader.weather.app.project.utils.NoopProdLogger
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun initApp(
    platformLogger: Logger,
    platformModule: Module,
    flags: Flags = Flags(),
) {
    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    val koin = initKoin(appScope, platformModule, flags)
    initLogging(
        appScope = appScope,
        platformLogger = platformLogger,
        bufferedDelegatingLogger = koin.get(),
        applicationStorage = koin.get(),
    )
}

private fun initKoin(
    appScope: CoroutineScope,
    platformModule: Module,
    platformFlags: Flags,
): Koin {
    return startKoin {
        val appModule = module {
            single { BufferedDelegatingLogger(get()) }
            single<Logger> { get<BufferedDelegatingLogger>() }

            single<ApplicationStorage> { MultiplatformSettingsStorage(get(), get()) }
            single {
                val flags = get<ApplicationStorage>().getFlagsBlocking()
                val endpoint = when {
                    flags != null && (flags != platformFlags) -> URLs.STAGING_URL
                    else -> URLs.PRODUCTION_URL
                }
                APIClient(endpoint, get())
            }
            single<TimeProvider> {
                val flags = get<ApplicationStorage>().getFlagsBlocking()
                when {
                    flags != null && flags.useFakeTime -> FakeTimeProvider(get())
                    else -> ServerBasedTimeProvider(get())
                }
            }
            single { FlagsManager(platformFlags, get(), get()) }
            single { appScope }
            singleOf(::ConferenceService)
        }

        val viewModelModule = module {
            viewModelOf(::AboutConferenceViewModel)
            viewModelOf(::LicensesViewModel)
            viewModelOf(::PrivacyNoticeViewModel)
            viewModelOf(::ScheduleViewModel)
            viewModelOf(::SessionViewModel)
            viewModelOf(::SettingsViewModel)
            viewModelOf(::SpeakerDetailViewModel)
            viewModelOf(::SpeakersViewModel)
            viewModelOf(::StartNotificationsViewModel)
        }

        // Note that the order of modules here is significant, later
        // modules can override dependencies from earlier modules
        modules(platformModule, appModule, viewModelModule)
    }.koin
}

private fun initLogging(
    appScope: CoroutineScope,
    platformLogger: Logger,
    bufferedDelegatingLogger: BufferedDelegatingLogger,
    applicationStorage: ApplicationStorage,
) {
    appScope.launch {
        val flags = applicationStorage.getFlags().first()
        bufferedDelegatingLogger.attach(
            when {
                flags != null && flags.debugLogging -> DebugLogger(platformLogger)
                else -> NoopProdLogger()
            }
        )
    }
}