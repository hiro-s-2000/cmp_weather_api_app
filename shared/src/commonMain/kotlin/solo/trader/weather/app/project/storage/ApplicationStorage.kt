package solo.trader.weather.app.project.storage

import kotlinx.coroutines.flow.Flow
import solo.trader.weather.app.project.Conference
import solo.trader.weather.app.project.Flags
import solo.trader.weather.app.project.NotificationSettings
import solo.trader.weather.app.project.SessionId
import solo.trader.weather.app.project.Theme
import solo.trader.weather.app.project.VoteInfo

interface ApplicationStorage {
    fun getUserId(): Flow<String?>
    suspend fun setUserId(value: String?)

    fun getPendingUserId(): Flow<String?>
    suspend fun setPendingUserId(value: String?)

    fun isOnboardingComplete(): Flow<Boolean>
    suspend fun setOnboardingComplete(value: Boolean)

    fun getTheme(): Flow<Theme>
    suspend fun setTheme(value: Theme)

    fun getConferenceCache(): Flow<Conference?>
    suspend fun setConferenceCache(value: Conference)

    fun getFavorites(): Flow<Set<SessionId>>
    suspend fun setFavorites(value: Set<SessionId>)

    fun getNotificationSettings(): Flow<NotificationSettings?>
    suspend fun setNotificationSettings(value: NotificationSettings)

    fun getVotes(): Flow<List<VoteInfo>>
    suspend fun setVotes(value: List<VoteInfo>)

    fun getFlagsBlocking(): Flags?
    fun getFlags(): Flow<Flags?>
    suspend fun setFlags(value: Flags)

    fun ensureCurrentVersion()
}
