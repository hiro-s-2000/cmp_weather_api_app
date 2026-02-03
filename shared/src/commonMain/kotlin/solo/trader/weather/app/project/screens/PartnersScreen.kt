package solo.trader.weather.app.project.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import solo.trader.weather.app.project.PARTNERS
import solo.trader.weather.app.project.PartnerId
import solo.trader.weather.app.project.ScrollToTopHandler
import solo.trader.weather.app.project.generated.resources.Res
import solo.trader.weather.app.project.generated.resources.partners_title
import solo.trader.weather.app.project.ui.components.Divider
import solo.trader.weather.app.project.ui.components.MainHeaderTitleBar
import solo.trader.weather.app.project.ui.components.PartnerCard
import solo.trader.weather.app.project.ui.components.Text
import solo.trader.weather.app.project.ui.components.TopMenuButton
import solo.trader.weather.app.project.ui.generated.resources.UiRes
import solo.trader.weather.app.project.ui.generated.resources.arrow_left_24
import solo.trader.weather.app.project.ui.generated.resources.main_header_back
import solo.trader.weather.app.project.ui.theme.KotlinConfTheme
import solo.trader.weather.app.project.utils.bottomInsetPadding
import solo.trader.weather.app.project.utils.plus
import solo.trader.weather.app.project.utils.topInsetPadding


@Composable
fun PartnersScreen(
    onBack: () -> Unit,
    onPartnerDetail: (partnerId: PartnerId) -> Unit,
) {
    Column(
        Modifier.fillMaxSize()
            .background(color = KotlinConfTheme.colors.mainBackground)
            .padding(topInsetPadding())
    ) {
        MainHeaderTitleBar(
            title = stringResource(Res.string.partners_title),
            startContent = {
                TopMenuButton(
                    icon = UiRes.drawable.arrow_left_24,
                    contentDescription = stringResource(UiRes.string.main_header_back),
                    onClick = onBack,
                )
            }
        )

        Divider(thickness = 1.dp, color = KotlinConfTheme.colors.strokePale)

        val lazyListState = rememberLazyListState()
        ScrollToTopHandler(lazyListState)
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp),
            contentPadding = PaddingValues(vertical = 12.dp) + bottomInsetPadding(),
            state = lazyListState,
        ) {
            for ((level, partners) in PARTNERS) {
                stickyHeader {
                    Text(
                        text = stringResource(level),
                        style = KotlinConfTheme.typography.h1,
                        modifier = Modifier.fillMaxWidth().background(KotlinConfTheme.colors.mainBackground).padding(vertical = 12.dp)
                    )
                }
                items(partners) { partner ->
                    PartnerCard(
                        name = partner.name,
                        logo = partner.icon,
                        onClick = { onPartnerDetail(partner.id) }
                    )
                }
            }
        }
    }
}
