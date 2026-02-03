package solo.trader.weather.app.project.ui.theme

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode

private object NoIndication : IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode =
        object : DelegatableNode, DrawModifierNode, Modifier.Node() {
            override fun ContentDrawScope.draw() = drawContent()
        }

    override fun equals(other: Any?): Boolean = other === NoIndication
    override fun hashCode(): Int = 0
}

val LocalColors = compositionLocalOf<Colors> {
    error("WeatherAppTheme must be part of the call hierarchy to provide colors")
}

val LocalShapes = compositionLocalOf<Shapes> {
    error("WeatherAppTheme must be part of the call hierarchy to provide dimensions")
}

val LocalTypography = compositionLocalOf<Typography> {
    error("WeatherAppTheme must be part of the call hierarchy to provide typography")
}

object WeatherAppTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

// Exposes custom theme value to Compose resources, https://youtrack.jetbrains.com/issue/CMP-4197
expect object LocalAppTheme {
    val current: Boolean @Composable get

    @Composable
    infix fun provides(value: Boolean?): ProvidedValue<*>
}

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    rippleEnabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        LocalColors provides if (darkTheme) WeatherAppDarkColors else WeatherAppLightColors,
        LocalShapes provides WeatherAppShapes,
        LocalTypography provides WeatherAppTypography,
        LocalIndication provides if (rippleEnabled) rememberRippleIndication() else NoIndication,
        LocalAppTheme provides darkTheme,
    ) {
        content()
    }
}
