package solo.trader.weather.app.project.storage

import kotlinx.coroutines.flow.Flow
import solo.trader.weather.app.project.common.Theme

interface ApplicationStorage {
    fun getCurrentCity(): String
    suspend fun setCurrentCity(city: String)

    fun getDateTime(): Long
    suspend fun setDateTime(seconds: Long)

    fun getTheme(): Flow<Theme>
    suspend fun setTheme(value: Theme)

    fun ensureCurrentVersion()
}
