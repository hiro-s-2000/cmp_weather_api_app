package solo.trader.weather.app.project.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import solo.trader.weather.app.project.common.Weather

class WeatherConverter {
    @TypeConverter
    fun fromWeatherList(value: List<Weather>?): String {
        return Json.Default.encodeToString(value)
    }

    @TypeConverter
    fun toWeatherList(value: String): List<Weather>? {
        return Json.Default.decodeFromString(value)
    }
}