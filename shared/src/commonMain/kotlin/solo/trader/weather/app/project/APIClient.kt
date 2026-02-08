@file:OptIn(ExperimentalTime::class)

package solo.trader.weather.app.project

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.core.Closeable
import solo.trader.weather.app.project.Defines.APY_KEY
import solo.trader.weather.app.project.common.Forecast
import kotlin.time.ExperimentalTime

class APIClient(private val apiUrl: String) : Closeable {

    var userId: String? = null

    private val client = HttpClient {
        expectSuccess = true
        install(ContentNegotiation) { json() }
        install(Logging) { level = LogLevel.HEADERS }
        install(HttpTimeout) { requestTimeoutMillis = 5000 }
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 3)
            exponentialDelay()
        }
        install(DefaultRequest) { url.takeFrom(apiUrl) }
    }

    override fun close() = client.close()

    suspend fun getWeatherByLocation(lat: String, lon: String): Result<Forecast?> = runCatching {
        client.get { apiUrl("forecast?lat=$lat&lon=$lon&appid=$APY_KEY") }.body()
    }

    suspend fun getWeatherByPlace(cityName: String): Result<Forecast?> = runCatching {
        client.get { apiUrl("forecast?q=$cityName&appid=$APY_KEY&units=metric&lang=ja") }.body()
    }

    private fun HttpRequestBuilder.apiUrl(path: String) {
        if (userId != null) header(HttpHeaders.Authorization, "Bearer $userId")
        header(HttpHeaders.CacheControl, "no-cache")
        url { encodedPath = path }
    }
}
