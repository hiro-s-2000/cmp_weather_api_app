package solo.trader.weather.app.project

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import solo.trader.weather.app.project.repository.WeatherLocalRepository
import solo.trader.weather.app.project.screens.home.HomeViewModel
import solo.trader.weather.app.project.screens.weather.WeatherViewModel
import solo.trader.weather.app.project.storage.ApplicationStorage
import solo.trader.weather.app.project.storage.MultiplatformSettingsStorage

fun initApp(platformModule: Module) {
    val appScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    initKoin(appScope, platformModule)
}

private fun initKoin(appScope: CoroutineScope, platformModule: Module): Koin {
    return startKoin {
        val appModule = module {
            single<ApplicationStorage> { MultiplatformSettingsStorage(get()) }
            single { APIClient(Defines.OPEN_WEATHER_API_URL) }
            single { appScope }
            singleOf(::WeatherService)
        }

        val databaseModule = module {
            single<AppDatabase> {
                getDatabaseBuilder()
                    .setDriver(BundledSQLiteDriver()) // commonMainで共通化可能
                    .build()
            }
            single { get<AppDatabase>().getDao() }
            single { WeatherLocalRepository(get()) }
        }

        val viewModelModule = module {
            factory { WeatherViewModel(get(), get(), get(), get()) }
            factory { HomeViewModel(get(), get()) }
        }

        modules(platformModule, appModule, databaseModule, viewModelModule)
    }.koin
}