package solo.trader.weather.app.project.repository

import solo.trader.weather.app.project.local.dao.WeatherDao
import solo.trader.weather.app.project.local.entity.ForecastEntity

class WeatherLocalRepository(private val weatherDao: WeatherDao) {
    suspend fun getData() = weatherDao.getData()
    suspend fun getDataByCityName(cityName: String) = weatherDao.getDataByCityName(cityName)
    suspend fun insert(item: ForecastEntity) = weatherDao.insert(item)
    suspend fun deleteAll() = weatherDao.deleteAll()

}