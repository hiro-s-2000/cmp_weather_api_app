package solo.trader.weather.app.project

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import dev.jordond.compass.geolocation.Geolocator
import dev.jordond.compass.geolocation.mobile
import dev.jordond.compass.permissions.LocationPermissionController
import org.koin.core.module.Module
import org.koin.dsl.module

fun platformModule(application: Application): Module {
    return module {
        single<Application> { application }
        single<Context> { application.applicationContext }
        single<ObservableSettings> {
            SharedPreferencesSettings(PreferenceManager.getDefaultSharedPreferences(application))
        }
        single<LocationPermissionController> { AndroidLocationPermissionController(get()) }
        single { Geolocator.mobile(get()) }
    }
}
