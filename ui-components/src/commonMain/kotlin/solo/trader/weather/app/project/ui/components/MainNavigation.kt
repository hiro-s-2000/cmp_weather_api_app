package solo.trader.weather.app.project.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import solo.trader.weather.app.project.ui.theme.WeatherAppTheme

@Composable
private fun MainNavigationButton(
    iconResource: DrawableResource,
    iconFilledResource: DrawableResource,
    contentDescription: String?,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val iconColor by animateColorAsState(
        targetValue = if (selected) WeatherAppTheme.colors.primaryText
        else WeatherAppTheme.colors.secondaryText
    )
    Icon(
        modifier = modifier
            .clip(WeatherAppTheme.shapes.roundedCornerMd)
            .selectable(
                selected = selected,
                enabled = true,
                role = Role.Tab,
                onClick = onClick,
            )
            .padding(10.dp)
            .size(28.dp),
        painter = painterResource(if (selected) iconFilledResource else iconResource),
        contentDescription = contentDescription,
        tint = iconColor,
    )
}

data class MainNavDestination<T : Any>(
    val label: StringResource?,
    val icon: DrawableResource,
    val route: T,
    val iconSelected: DrawableResource = icon,
)

@Composable
fun <T : Any> MainNavigation(
    currentDestination: MainNavDestination<T>?,
    destinations: List<MainNavDestination<T>>,
    onSelect: (MainNavDestination<T>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        destinations.forEach { destination ->
            MainNavigationButton(
                iconResource = destination.icon,
                iconFilledResource = destination.iconSelected,
                contentDescription = destination.label?.let { stringResource(it) },
                selected = destination == currentDestination,
                onClick = { onSelect(destination) },
                modifier = Modifier.weight(1f),
            )
        }
    }
}
