package solo.trader.weather.app.project

import coil3.PlatformContext
import okio.Path
import okio.Path.Companion.toPath

actual fun getCachePath(context: PlatformContext): Path {
    return context.cacheDir.absolutePath.toPath()
}


