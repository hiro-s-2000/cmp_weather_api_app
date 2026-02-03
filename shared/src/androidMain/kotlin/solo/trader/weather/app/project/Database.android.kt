package solo.trader.weather.app.project

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.java.KoinJavaComponent

actual fun getDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val appContext = KoinJavaComponent.get<Context>(Context::class.java)
    val dbFile = appContext.getDatabasePath("weather.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}