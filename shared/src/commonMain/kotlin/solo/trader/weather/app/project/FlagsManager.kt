package solo.trader.weather.app.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import solo.trader.weather.app.project.storage.ApplicationStorage

class FlagsManager(
    private val platformFlags: Flags,
    private val storage: ApplicationStorage,
    private val scope: CoroutineScope,
) {

    val flags = storage.getFlags()
        .map { it ?: platformFlags }
        .stateIn(scope, SharingStarted.Eagerly, platformFlags)

    init {
        scope.launch {
            val storedFlags = storage.getFlags().first()
            if (storedFlags == null) {
                storage.setFlags(platformFlags)
            }
        }
    }

    fun resetFlags() {
        scope.launch {
            storage.setFlags(platformFlags)
        }
    }

    fun updateFlags(newFlags: Flags) {
        scope.launch {
            storage.setFlags(newFlags)
        }
    }
}
