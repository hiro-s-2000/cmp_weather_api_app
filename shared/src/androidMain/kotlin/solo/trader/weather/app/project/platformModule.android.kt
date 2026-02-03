package solo.trader.weather.app.project

import android.app.Application
import androidx.preference.PreferenceManager
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.module.Module
import org.koin.dsl.module

fun platformModule(
    application: Application
): Module {
    return module {
        single<ObservableSettings> {
            SharedPreferencesSettings(PreferenceManager.getDefaultSharedPreferences(application))
        }
    }
}
