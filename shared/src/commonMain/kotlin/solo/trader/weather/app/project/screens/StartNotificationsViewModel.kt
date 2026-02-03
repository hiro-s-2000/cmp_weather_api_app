package solo.trader.weather.app.project.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import solo.trader.weather.app.project.ConferenceService
import solo.trader.weather.app.project.NotificationSettings

class StartNotificationsViewModel(
    private val service: ConferenceService,
) : ViewModel() {
    private val _permissionRequestComplete = MutableStateFlow<Boolean>(false)
    val permissionHandlingDone = _permissionRequestComplete

    val notificationSettings: StateFlow<NotificationSettings?> = service.getNotificationSettings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun setNotificationSettings(settings: NotificationSettings) {
        viewModelScope.launch {
            service.setNotificationSettings(settings)
        }
    }

    fun requestNotificationPermissions() {
        viewModelScope.launch {
            val settings = notificationSettings.value
            _permissionRequestComplete.emit(true)
        }
    }
}
