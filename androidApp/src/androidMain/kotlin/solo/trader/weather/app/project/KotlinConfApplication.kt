package solo.trader.weather.app.project

import android.app.Application
import solo.trader.weather.app.project.utils.AndroidLogger

class KotlinConfApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initApp(
            platformLogger = AndroidLogger(),
            platformModule = platformModule(application = this),
        )
    }
}
