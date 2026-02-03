package solo.trader.weather.app.project.ui.theme

import androidx.compose.ui.graphics.Color
import solo.trader.weather.app.project.ui.theme.Brand.magenta100
import solo.trader.weather.app.project.ui.theme.Brand.magentaTextDark
import solo.trader.weather.app.project.ui.theme.Brand.orange
import solo.trader.weather.app.project.ui.theme.Brand.pink100
import solo.trader.weather.app.project.ui.theme.Brand.purple100
import solo.trader.weather.app.project.ui.theme.Brand.purpleTextDark
import solo.trader.weather.app.project.ui.theme.UI.black100
import solo.trader.weather.app.project.ui.theme.UI.black15
import solo.trader.weather.app.project.ui.theme.UI.black40
import solo.trader.weather.app.project.ui.theme.UI.black60
import solo.trader.weather.app.project.ui.theme.UI.black70
import solo.trader.weather.app.project.ui.theme.UI.grey400
import solo.trader.weather.app.project.ui.theme.UI.grey500
import solo.trader.weather.app.project.ui.theme.UI.white10
import solo.trader.weather.app.project.ui.theme.UI.white100
import solo.trader.weather.app.project.ui.theme.UI.white20
import solo.trader.weather.app.project.ui.theme.UI.white30
import solo.trader.weather.app.project.ui.theme.UI.white50
import solo.trader.weather.app.project.ui.theme.UI.white70

class Colors(
    val isDark: Boolean,

    val mainBackground: Color,
    val primaryBackground: Color,
    val tileBackground: Color,
    val errorBackground: Color,

    val strokeAccent: Color,
    val strokeHalf: Color,
    val strokePale: Color,

    val accentText: Color,
    val longText: Color,
    val noteText: Color,
    val primaryText: Color,
    val primaryTextWhiteFixed: Color,
    val secondaryText: Color,
    val tileText: Color,
    val errorText: Color,

    val toggleOn: Color,
    val toggleOff: Color,
    val divider: Color
)

val WeatherAppLightColors = Colors(
    isDark = false,
    mainBackground = white100,
    primaryBackground = magenta100,
    tileBackground = black70,
    errorBackground = grey500,
    strokeAccent = purple100,
    strokeHalf = black40,
    strokePale = black15,
    accentText = magenta100,
    longText = black70,
    noteText = black40,
    primaryText = black100,
    primaryTextWhiteFixed = white100,
    secondaryText = black60,
    tileText = white100,
    errorText = orange,
    toggleOff = grey400,
    toggleOn = purple100,
    divider = grey400
)

val WeatherAppDarkColors = Colors(
    isDark = true,
    mainBackground = black100,
    primaryBackground = magenta100,
    tileBackground = white10,
    errorBackground = grey500,
    strokeAccent = purpleTextDark,
    strokeHalf = white50,
    strokePale = white20,
    accentText = magentaTextDark,
    longText = white70,
    noteText = white50,
    primaryText = white100,
    primaryTextWhiteFixed = white100,
    secondaryText = white70,
    tileText = black70,
    errorText = orange,
    toggleOff = grey500,
    toggleOn = purpleTextDark,
    divider = grey500
)
