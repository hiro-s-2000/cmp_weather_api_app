package solo.trader.weather.app.project.storage

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.coroutines.getStringOrNullFlow
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import solo.trader.weather.app.project.common.Theme

@OptIn(ExperimentalSettingsApi::class)
class MultiplatformSettingsStorage(private val settings: ObservableSettings) : ApplicationStorage {
    override fun getCurrentCity(): String = settings.getString(Keys.CITY, "")
    override suspend fun setCurrentCity(city: String) = settings.set(Keys.CITY, city)

    override fun getDateTime(): Long = settings.getLong(Keys.DATETIME, 0)
    override suspend fun setDateTime(seconds: Long) = settings.set(Keys.DATETIME, seconds)

    override fun getTheme(): Flow<Theme> = settings.getStringOrNullFlow(Keys.THEME)
        .map { it?.let { Theme.valueOf(it) } ?: Theme.SYSTEM }

    override suspend fun setTheme(value: Theme) = settings
        .set(Keys.THEME, value.name)

    override fun ensureCurrentVersion() {
        var version = settings.getInt(Keys.STORAGE_VERSION, 0)
    }

    private fun destructiveUpgrade() {
        settings.clear()
        settings.set(Keys.STORAGE_VERSION, CURRENT_STORAGE_VERSION)
    }

    private data class Migration(val from: Int, val to: Int, val migrate: () -> Unit)

    private val migrations = listOf(
        Migration(V2025, V2026) {
            // News were removed
            settings.remove(Keys.NEWS_CACHE)
        },
    )

    companion object {
        const val V2025 = 2025_000
        const val V2026 = 2026_000
        const val CURRENT_STORAGE_VERSION: Int = V2026
    }

    private object Keys {
        const val STORAGE_VERSION = "storageVersion"
        const val THEME = "theme"
        const val NEWS_CACHE = "newsCache"
        const val DATETIME = "datetime"
        const val CITY = "city"
    }
}
