package solo.trader.weather.app.project.ui.components

import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.spring
import androidx.compose.ui.graphics.Color

internal val ColorSpringSpec = spring<Color>(DampingRatioNoBouncy, StiffnessMediumLow)
