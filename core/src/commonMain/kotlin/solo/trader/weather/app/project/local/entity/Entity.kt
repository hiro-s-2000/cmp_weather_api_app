package solo.trader.weather.app.project.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import solo.trader.weather.app.project.common.City
import solo.trader.weather.app.project.common.Forecast
import solo.trader.weather.app.project.common.ForecastItem

@Entity
data class ForecastEntity(
    @PrimaryKey
    val fId: Int? = null,
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<ForecastItem> = listOf(),
    @Embedded val city: City
)

fun List<ForecastEntity>.toDomainList(): List<Forecast> = this.map { it.toDomain() }

fun ForecastEntity.toDomain(): Forecast {
    return Forecast(
        cod = cod,
        message = message,
        cnt = cnt,
        list = list,
        city = city
    )
}