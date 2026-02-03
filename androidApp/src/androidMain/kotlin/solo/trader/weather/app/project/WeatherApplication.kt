package solo.trader.weather.app.project

import android.app.Application

class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initApp(platformModule = platformModule(application = this))
    }
}
