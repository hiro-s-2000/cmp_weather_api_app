package solo.trader.weather.app.project.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import solo.trader.weather.app.project.NotificationSettings
import solo.trader.weather.app.project.generated.resources.Res
import solo.trader.weather.app.project.generated.resources.notifications_schedule_update_description
import solo.trader.weather.app.project.generated.resources.notifications_schedule_update_title
import solo.trader.weather.app.project.generated.resources.notifications_session_reminders_description
import solo.trader.weather.app.project.generated.resources.notifications_session_reminders_title
import solo.trader.weather.app.project.ui.components.SettingsItem


@Composable
fun NotificationSettings(
    notificationSettings: NotificationSettings,
    onChangeSettings: (NotificationSettings) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SettingsItem(
            title = stringResource(Res.string.notifications_session_reminders_title),
            enabled = notificationSettings.sessionReminders,
            onToggle = { enabled -> onChangeSettings(notificationSettings.copy(sessionReminders = enabled)) },
            note = stringResource(Res.string.notifications_session_reminders_description),
        )
        SettingsItem(
            title = stringResource(Res.string.notifications_schedule_update_title),
            enabled = notificationSettings.scheduleUpdates,
            onToggle = { enabled -> onChangeSettings(notificationSettings.copy(scheduleUpdates = enabled)) },
            note = stringResource(Res.string.notifications_schedule_update_description),
        )
    }
}
