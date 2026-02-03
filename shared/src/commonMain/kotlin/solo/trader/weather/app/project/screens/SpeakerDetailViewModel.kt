package solo.trader.weather.app.project.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import solo.trader.weather.app.project.ConferenceService
import solo.trader.weather.app.project.SessionCardView
import solo.trader.weather.app.project.SessionId
import solo.trader.weather.app.project.Speaker
import solo.trader.weather.app.project.SpeakerId

class SpeakerDetailViewModel(
    private val service: ConferenceService,
    speakerId: SpeakerId,
) : ViewModel() {
    fun onBookmark(sessionId: SessionId, bookmarked: Boolean) {
        viewModelScope.launch {
            service.setFavorite(sessionId, bookmarked)
        }
    }

    val speaker: StateFlow<Speaker?> = service.speakerByIdFlow(speakerId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val sessions: StateFlow<List<SessionCardView>> = service.sessionsForSpeakerFlow(speakerId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
