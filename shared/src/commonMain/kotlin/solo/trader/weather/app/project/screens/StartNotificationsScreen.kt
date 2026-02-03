package solo.trader.weather.app.project.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import solo.trader.weather.app.project.generated.resources.Res
import solo.trader.weather.app.project.generated.resources.kodee_notifications
import solo.trader.weather.app.project.generated.resources.notifications_description
import solo.trader.weather.app.project.generated.resources.notifications_lets_get_started
import solo.trader.weather.app.project.generated.resources.notifications_title
import solo.trader.weather.app.project.ui.components.Button
import solo.trader.weather.app.project.ui.components.Text
import solo.trader.weather.app.project.ui.theme.KotlinConfTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StartNotificationsScreen(
    onDone: () -> Unit,
    viewModel: StartNotificationsViewModel = koinViewModel(),
) {
    val notificationSettings = viewModel.notificationSettings.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.permissionHandlingDone.collect { done ->
            if (done) onDone()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = KotlinConfTheme.colors.mainBackground)
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .weight(1f)
        ) {
            Image(
                imageVector = vectorResource(Res.drawable.kodee_notifications),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
                    .size(160.dp)
            )
            Text(
                stringResource(Res.string.notifications_title),
                style = KotlinConfTheme.typography.h1,
                modifier = Modifier.semantics {
                    heading()
                }
            )
            Text(
                stringResource(Res.string.notifications_description),
                color = KotlinConfTheme.colors.longText,
            )
            if (notificationSettings != null) {
                NotificationSettings(
                    notificationSettings = notificationSettings,
                    onChangeSettings = { viewModel.setNotificationSettings(it) }
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            Button(
                label = stringResource(Res.string.notifications_lets_get_started),
                onClick = {
                    viewModel.requestNotificationPermissions()
                },
                modifier = Modifier.weight(1f),
                primary = true,
            )
        }
    }
}
