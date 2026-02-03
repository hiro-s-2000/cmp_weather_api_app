package solo.trader.weather.app.project.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import solo.trader.weather.app.project.ConferenceService
import solo.trader.weather.app.project.NotificationSettings
import solo.trader.weather.app.project.Theme

class SettingsViewModel(
    private val service: ConferenceService,
) : ViewModel() {
    val theme: StateFlow<Theme> = service.getTheme()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Theme.SYSTEM)

    val notificationSettings: StateFlow<NotificationSettings?> = service.getNotificationSettings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun setTheme(theme: Theme) {
        service.setTheme(theme)
    }
}
