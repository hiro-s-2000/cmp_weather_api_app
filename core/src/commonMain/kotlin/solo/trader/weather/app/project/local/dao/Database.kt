package solo.trader.weather.app.project.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import solo.trader.weather.app.project.local.entity.ForecastEntity

@Dao
interface WeatherDao {
    @Insert
    suspend fun insert(item: ForecastEntity)

    @Update
    suspend fun update(vararg forecast: ForecastEntity)

    @Query("DELETE FROM ForecastEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM ForecastEntity WHERE name = :cityName")
    suspend fun getDataByCityName(cityName: String): ForecastEntity?

    @Query("SELECT * FROM ForecastEntity")
    suspend fun getData(): List<ForecastEntity>?
}
