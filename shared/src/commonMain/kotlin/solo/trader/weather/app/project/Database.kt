package solo.trader.weather.app.project

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import solo.trader.weather.app.project.local.converter.ForecastItemConverter
import solo.trader.weather.app.project.local.converter.WeatherConverter
import solo.trader.weather.app.project.local.dao.WeatherDao
import solo.trader.weather.app.project.local.entity.ForecastEntity

expect fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>

@Database(entities = [ForecastEntity::class], version = 1)
@TypeConverters(ForecastItemConverter::class, WeatherConverter::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDao(): WeatherDao
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}