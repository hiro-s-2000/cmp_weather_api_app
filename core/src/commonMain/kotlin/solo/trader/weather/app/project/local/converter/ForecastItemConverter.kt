package solo.trader.weather.app.project.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import solo.trader.weather.app.project.common.ForecastItem

class ForecastItemConverter {
    @TypeConverter
    fun fromForecastItemList(value: List<ForecastItem>?): String {
        return Json.Default.encodeToString(value)
    }

    @TypeConverter
    fun toForecastItemList(value: String): List<ForecastItem>? {
        return Json.Default.decodeFromString(value)
    }
}