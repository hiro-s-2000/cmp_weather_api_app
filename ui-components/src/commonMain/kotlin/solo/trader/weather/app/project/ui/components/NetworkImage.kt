package solo.trader.weather.app.project.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.jetbrains.compose.resources.painterResource
import solo.trader.weather.app.project.ui.generated.resources.UiRes
import solo.trader.weather.app.project.ui.generated.resources.placeholder

@Composable
fun NetworkImage(
    photoUrl: String,
    contentDescription: String? = "",
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(photoUrl)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        placeholder = painterResource(UiRes.drawable.placeholder)
    )
}
