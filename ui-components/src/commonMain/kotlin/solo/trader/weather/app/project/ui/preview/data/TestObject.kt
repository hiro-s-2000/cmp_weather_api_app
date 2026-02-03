package solo.trader.weather.app.project.ui.preview.data

import solo.trader.weather.app.project.common.City
import solo.trader.weather.app.project.common.Clouds
import solo.trader.weather.app.project.common.Coord
import solo.trader.weather.app.project.common.Forecast
import solo.trader.weather.app.project.common.ForecastItem
import solo.trader.weather.app.project.common.MainInfo
import solo.trader.weather.app.project.common.Rain
import solo.trader.weather.app.project.common.Sys
import solo.trader.weather.app.project.common.Weather
import solo.trader.weather.app.project.common.Wind

object TestObject {
    val testForecast = Forecast(
        cod = "200",
        message = 0,
        cnt = 0,
        list = generateFortyForecastItems(),
        city = City(
            id = 0,
            name = "",
            coord = Coord(
                lat = 0.0,
                lon = 0.0
            ),
            country = "",
            population = 0,
            timezone = 0,
            sunrise = 0,
            sunset = 0
        )
    )

    fun generateFortyForecastItems(): List<ForecastItem> = List(40) { index -> testForecastItem(index) }

    fun testForecastItem(index: Int) = ForecastItem(
        dt = 1700000000L + (index * 10800L), // 3時間（10800秒）ずつ加算
        main = MainInfo(
            temp = 20.0 + (index % 5),
            feelsLike = 19.0 + (index % 5),
            tempMin = 15.0,
            tempMax = 25.0,
            pressure = 1013,
            seaLevel = 1013,
            grndLevel = 1000,
            humidity = 50 + (index % 10),
            tempKf = 0.0
        ),
        weather = listOf(
            Weather(
                id = 800,
                main = if (index % 2 == 0) "Clear" else "Clouds",
                description = "sky is clear",
                icon = "01d"
            )
        ),
        clouds = Clouds(all = 10),
        wind = Wind(speed = 2.5, deg = 180, gust = 3.0),
        visibility = 10000,
        pop = 0.1,
        rain = if (index % 10 == 0) Rain(h3Rain = 0.5) else null,
        snow = null,
        sys = Sys(pod = if (index % 8 in 0..3) "d" else "n"),
        dtTxt = "2026-02-07 ${(index * 3) % 24}:00:00"
    )
}