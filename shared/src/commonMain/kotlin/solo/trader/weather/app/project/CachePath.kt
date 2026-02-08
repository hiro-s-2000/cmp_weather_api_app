package solo.trader.weather.app.project

import coil3.PlatformContext
import okio.Path

expect fun getCachePath(context: PlatformContext): Path
