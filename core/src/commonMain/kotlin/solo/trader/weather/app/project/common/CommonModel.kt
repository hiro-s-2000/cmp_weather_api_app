package solo.trader.weather.app.project.common

import androidx.room.Embedded
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import solo.trader.weather.app.project.local.entity.ForecastEntity

@Serializable
data class Forecast(
    val cod: String,           // 内部パラメータ
    val message: Int,          // 内部パラメータ
    val cnt: Int,              // レスポンスに含まれる予報の数
    val list: List<ForecastItem>, // 予報データのリスト
    val city: City             // 市区町村の情報
)

fun Forecast.toEntity(): ForecastEntity {
    return ForecastEntity(
        cod = cod,
        message = message,
        cnt = cnt,
        list = list,
        city = city
    )
}

@Serializable
data class ForecastItem(
    val dt: Long,                // データ計算時間（Unix、UTC）
    @Embedded val main: MainInfo,          // メインの気象指標
    val weather: List<Weather>,  // 気象状況のリスト
    @Embedded val clouds: Clouds,          // 雲量
    @Embedded val wind: Wind,              // 風の情報
    val visibility: Int = 0,     // 視認性
    val pop: Double,             // 降水確率（0～1）
    @Embedded val rain: Rain? = null,      // 雨が降らない場合はnullが返る
    @Embedded val snow: Snow? = null,      // 雪が降らない場合はnullが返る
    @Embedded val sys: Sys,                // システム情報（昼夜など）
    @SerialName("dt_txt")
    val dtTxt: String            // データ計算時間（ISO形式）
)

@Serializable
data class MainInfo(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    val pressure: Int,         // 気圧（海面）
    @SerialName("sea_level")
    val seaLevel: Int,         // 海面気圧
    @SerialName("grnd_level")
    val grndLevel: Int,        // 地上気圧
    val humidity: Int,         // 湿度
    @SerialName("temp_kf")
    val tempKf: Double         // 内部パラメータ
)

@Serializable
data class Weather(
    val id: Int,             // 気象条件ID
    val main: String,        // グループ（Rain, Snow, Extreme等）
    val description: String, // 詳細な説明
    val icon: String         // アイコンID
)

@Serializable
data class Clouds(val all: Int)// 雲量（%）

@Serializable
data class Wind(
    val speed: Double,         // 風速
    val deg: Int,              // 風向（度）
    val gust: Double           // 突風
)

@Serializable
data class Sys(val pod: String) // 昼夜（n: night, d: day）

@Serializable
data class Rain(
    @SerialName("3h")
    val h3Rain: Double? = null // 過去3時間の降水量 (mm)
)

@Serializable
data class Snow(
    @SerialName("3h")
    val h3Snow: Double? = null // 過去3時間の降雪量 (mm)
)

@Serializable
data class City(
    val id: Int,
    val name: String,
    @Embedded val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,         // UTCからの時差（秒）
    val sunrise: Long,         // 日の出時刻（Unix）
    val sunset: Long           // 日の入り時刻（Unix）
)

@Serializable
data class Coord(
    val lat: Double,           // 緯度
    val lon: Double            // 経度
)

@Serializable
enum class Theme {
    SYSTEM,
    LIGHT,
    DARK,
}

@Serializable
enum class SearchCity(val cityName: String, val q: String) {
    Hokkaido("北海道", "Hokkaido"),
    Tokyo("東京都", "Tokyo"),
    Hyogo("兵庫県", "Hyogo"),
    Oita("大分市", "Oita"),
    CurrentLocation("", "")
}

data class LocationData(val latitude: Double, val longitude: Double)
