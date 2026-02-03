package solo.trader.weather.app.project

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            val permissionLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestMultiplePermissions(),
                onResult = { permissionsMap ->
                    val allGranted = permissionsMap.values.all { it }
                    if (allGranted) {
                        // NOP
                    } else {
                        // NOP
                    }
                }
            )
            App(
                onThemeChange = { isDarkMode ->
                    val systemBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                        detectDarkMode = { isDarkMode }
                    )
                    enableEdgeToEdge(
                        statusBarStyle = systemBarStyle,
                        navigationBarStyle = systemBarStyle,
                    )
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        // Don't enforce scrim https://issuetracker.google.com/issues/298296168
                        window.isNavigationBarContrastEnforced = false
                    }
                },
                permissionRequest = { permissionLauncher.launch(permissions) }
            )
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
    }
}
