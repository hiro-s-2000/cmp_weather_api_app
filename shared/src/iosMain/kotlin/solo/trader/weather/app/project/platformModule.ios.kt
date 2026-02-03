package solo.trader.weather.app.project

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.mobile
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

fun platformModule(): Module {
    return module {
        single<ObservableSettings> {
            NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
        }
        single { Geolocator.mobile() }
    }
}