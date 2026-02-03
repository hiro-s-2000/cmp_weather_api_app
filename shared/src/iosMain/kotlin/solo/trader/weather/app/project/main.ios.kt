package solo.trader.weather.app.project

import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

@Suppress("unused") // Called from Swift
fun initApp() = initApp(platformModule = platformModule())

@Suppress("unused") // Called from Swift
fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = { onFocusBehavior = OnFocusBehavior.DoNothing },
) {
    App()
}
