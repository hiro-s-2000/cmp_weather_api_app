package solo.trader.weather.app.project

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import okio.FileSystem
import okio.SYSTEM
import org.koin.compose.koinInject
import solo.trader.weather.app.project.common.Theme
import solo.trader.weather.app.project.navigation.WeatherAppNavHost
import solo.trader.weather.app.project.ui.theme.WeatherAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(onThemeChange: ((isDarkTheme: Boolean) -> Unit)? = null, permissionRequest: (() -> Unit)? = null) {
    val service = koinInject<WeatherService>()

    val currentTheme by service.getTheme().collectAsStateWithLifecycle(initialValue = Theme.SYSTEM)
    val isDarkTheme = when (currentTheme) {
        Theme.SYSTEM -> isSystemInDarkTheme()
        Theme.LIGHT -> false
        Theme.DARK -> true
    }

    CoilCacheInit()

    if (onThemeChange != null) LaunchedEffect(isDarkTheme) {
        onThemeChange(isDarkTheme)
    }

    if (permissionRequest != null) LaunchedEffect(permissionRequest) {
        permissionRequest.invoke()
    }

    WeatherAppTheme(darkTheme = isDarkTheme) {
        WeatherAppNavHost()
    }
}

@Composable
private fun CoilCacheInit() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            // メモリキャッシュの設定
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25)
                    .build()
            }
            // ディスクキャッシュの設定（これがオフライン表示に必須）
            .diskCache {
                DiskCache.Builder()
                    .directory(getCachePath(context) / "image_cache")
                    .fileSystem(FileSystem.SYSTEM) // Okioのファイルシステムを使用
                    .maxSizeBytes(512L * 1024 * 1024) // 512MB
                    .build()
            }
            // キャッシュポリシーの強制（任意）
            .diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build()
    }
}
