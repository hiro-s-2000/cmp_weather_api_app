package solo.trader.weather.app.project.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import solo.trader.weather.app.project.generated.resources.Res
import solo.trader.weather.app.project.generated.resources.bluesky
import solo.trader.weather.app.project.generated.resources.info_link_about_app
import solo.trader.weather.app.project.generated.resources.info_link_about_conf
import solo.trader.weather.app.project.generated.resources.info_link_code_of_conduct
import solo.trader.weather.app.project.generated.resources.info_link_description_bluesky
import solo.trader.weather.app.project.generated.resources.info_link_description_slack
import solo.trader.weather.app.project.generated.resources.info_link_description_twitter
import solo.trader.weather.app.project.generated.resources.info_link_partners
import solo.trader.weather.app.project.generated.resources.info_link_settings
import solo.trader.weather.app.project.generated.resources.info_title
import solo.trader.weather.app.project.generated.resources.kotlinconf_by_jetbrains
import solo.trader.weather.app.project.generated.resources.slack
import solo.trader.weather.app.project.generated.resources.twitter
import solo.trader.weather.app.project.ui.components.Divider
import solo.trader.weather.app.project.ui.components.MainHeaderTitleBar
import solo.trader.weather.app.project.ui.components.PageMenuItem
import solo.trader.weather.app.project.ui.theme.KotlinConfTheme


@Composable
fun InfoScreen(
    onAboutConf: () -> Unit,
    onAboutApp: () -> Unit,
    onOurPartners: () -> Unit,
    onCodeOfConduct: () -> Unit,
    onTwitter: () -> Unit,
    onSlack: () -> Unit,
    onBluesky: () -> Unit,
    onSettings: () -> Unit,
) {
    Column(Modifier.fillMaxSize().background(color = KotlinConfTheme.colors.mainBackground)) {
        MainHeaderTitleBar(stringResource(Res.string.info_title))
        Divider(1.dp, KotlinConfTheme.colors.strokePale)

        Column(
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                imageVector = vectorResource(Res.drawable.kotlinconf_by_jetbrains),
                contentDescription = null,
                modifier = Modifier
                    .padding(24.dp)
                    .widthIn(max = 360.dp)
            )
            PageMenuItem(stringResource(Res.string.info_link_about_conf), onClick = onAboutConf)
            PageMenuItem(stringResource(Res.string.info_link_about_app), onClick = onAboutApp)
            PageMenuItem(stringResource(Res.string.info_link_settings), onClick = onSettings)
            PageMenuItem(stringResource(Res.string.info_link_partners), onClick = onOurPartners)
            PageMenuItem(stringResource(Res.string.info_link_code_of_conduct), onClick = onCodeOfConduct)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SocialSquare(
                    image = vectorResource(Res.drawable.twitter),
                    description = stringResource(Res.string.info_link_description_twitter),
                    modifier = Modifier.weight(1f),
                    onClick = onTwitter,
                )
                SocialSquare(
                    image = vectorResource(Res.drawable.slack),
                    description = stringResource(Res.string.info_link_description_slack),
                    modifier = Modifier.weight(1f),
                    onClick = onSlack,
                )
                SocialSquare(
                    image = vectorResource(Res.drawable.bluesky),
                    description = stringResource(Res.string.info_link_description_bluesky),
                    modifier = Modifier.weight(1f),
                    onClick = onBluesky,
                )
            }
        }
    }
}

@Composable
private fun SocialSquare(
    image: ImageVector,
    description: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Image(
        imageVector = image,
        contentDescription = description,
        modifier = modifier
            .clip(KotlinConfTheme.shapes.roundedCornerMd)
            .clickable(onClick = onClick)
            .background(KotlinConfTheme.colors.tileBackground)
            .padding(vertical = 32.dp)
            .size(64.dp),
        colorFilter = ColorFilter.tint(KotlinConfTheme.colors.primaryText),
    )
}
