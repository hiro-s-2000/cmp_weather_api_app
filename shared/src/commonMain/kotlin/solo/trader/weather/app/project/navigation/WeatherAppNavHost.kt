package solo.trader.weather.app.project.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.compose.serialization.serializers.SnapshotStateListSerializer
import org.jetbrains.compose.resources.stringResource
import solo.trader.weather.app.project.generated.resources.Res
import solo.trader.weather.app.project.generated.resources.navigate_back
import solo.trader.weather.app.project.screens.home.HomeScreen
import solo.trader.weather.app.project.screens.weather.WeatherContentScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WeatherAppNavHost() {
    val backstack: MutableList<AppRoute> =
        rememberSerializable(serializer = SnapshotStateListSerializer()) {
            mutableStateListOf(HomeScreen)
        }

    val onBack = { if (backstack.size > 1) backstack.removeLastOrNull() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.height(110.dp),
                title = { Text(text = "OpenWeatherApp") },
                navigationIcon = {
                    if (backstack.size > 1) {
                        Button(
                            modifier = Modifier.width(100.dp).height(40.dp),
                            onClick = onBack
                        ) {
                            Text(stringResource(Res.string.navigate_back))
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(innerPadding),
            backStack = backstack,
            onBack = onBack,
            entryProvider = entryProvider { screens(backstack) },
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
        )
    }
}

private fun EntryProviderScope<AppRoute>.screens(backStack: MutableList<AppRoute>) {
    entry<HomeScreen> { HomeScreen(onWeather = { searchCity -> backStack.add(WeatherScreen(searchCity)) }) }
    entry<WeatherScreen> { WeatherContentScreen(searchCity = it.searchCity) }
}
