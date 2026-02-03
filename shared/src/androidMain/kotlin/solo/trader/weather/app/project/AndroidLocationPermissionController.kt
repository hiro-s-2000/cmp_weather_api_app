package solo.trader.weather.app.project

import coil3.PlatformContext
import dev.jordond.compass.Priority
import dev.jordond.compass.permissions.LocationPermissionController
import dev.jordond.compass.permissions.PermissionState

class AndroidLocationPermissionController(context: PlatformContext) : LocationPermissionController {

    override fun hasPermission(): Boolean {
        return true
    }

    override suspend fun requirePermissionFor(priority: Priority): PermissionState {
        return PermissionState.Granted
    }
}