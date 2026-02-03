package solo.trader.weather.app.project

import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.ObservableSettings
import solo.trader.weather.app.project.utils.Logger
import org.koin.dsl.module
import platform.Foundation.NSLog
import platform.Foundation.NSUserDefaults
import platform.UIKit.UIViewController

private val platformModule = module {
    single<ObservableSettings> {
        NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults)
    }
}

@Suppress("unused") // Called from Swift
fun initApp() = initApp(
    platformLogger = IOSLogger(),
    platformModule = platformModule,
    flags = Flags(
        enableBackOnMainScreens = false,
        rippleEnabled = false,
        hideKeyboardOnDrag = true,
    )
)

class IOSLogger : Logger {
    override fun log(tag: String, lazyMessage: () -> String) {
        NSLog("[$tag] ${lazyMessage()}")
    }
}

@Suppress("unused") // Called from Swift
fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = { onFocusBehavior = OnFocusBehavior.DoNothing },
) {
    App()
}
