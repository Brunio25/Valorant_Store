package com.valorant.store.app.feature.storefront.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.valorant.store.app.feature.storefront.util.ColorUtil
import com.valorant.store.domain.model.contentTiers.ContentTier
import com.valorant.store.domain.model.currencies.Currency
import com.valorant.store.domain.model.skins.Skin
import com.valorant.store.domain.model.skins.SkinLevel
import java.util.UUID

val mockSkin = Skin(
    uuid = UUID.randomUUID(),
    displayName = "Nome a mostrar",
    themeUuid = UUID.randomUUID(),
    contentTierUuid = null,
    displayIcon = "https://media.valorant-api.com/weaponskinchromas/b5280469-4069-b125-c76a-d28e20791322/displayicon.png",
    wallpaper = null,
    assetPath = "",
    chromas = emptyList(),
    levels = listOf(
        SkinLevel(
            uuid = UUID.randomUUID(),
            displayName = "level",
            displayIcon = "https://media.valorant-api.com/weaponskinchromas/b5280469-4069-b125-c76a-d28e20791322/displayicon.png",
            levelItem = null,
            streamedVideo = null,
            assetPath = ""
        )
    )
)


val mockCurrency = Currency(
    "VALORANT POINTS",
    "VALORANT POINT",
    "https://media.valorant-api.com/currencies/85ad13f7-3d1b-5128-9eb2-7cd8ee0b5741/displayicon.png",
    "https://media.valorant-api.com/currencies/85ad13f7-3d1b-5128-9eb2-7cd8ee0b5741/largeicon.png"
)

val mockContentTier = ContentTier(
    displayName = "Deluxe Edition",
    devName = "Deluxe",
    rank = 1,
    highlightColor = "00958733",
    displayIcon = "https://media.valorant-api.com/contenttiers/0cebb8be-46d7-c12a-d306-e9907bfc5a25/displayicon.png",
)

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SkinDisplayComponent(
    skin: Skin = mockSkin,
    prices: Map<Currency, Int> = mapOf(mockCurrency to 1500),
    contentTier: ContentTier = mockContentTier
) {
    val bgColor = ColorUtil.parseRgba(contentTier.highlightColor)

    Card(
        onClick = {},
        colors = CardDefaults.cardColors().copy(containerColor = bgColor.color),
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .fillMaxHeight(0.2f)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = contentTier.displayIcon,
                contentDescription = contentTier.displayName,
                modifier = Modifier
                    .padding(vertical = 12.dp, horizontal = 15.dp)
                    .fillMaxSize(0.2f)
                    .wrapContentSize(Alignment.TopEnd)
                    .align(Alignment.TopEnd)
            )

            AsyncImage(
                model = skin.levels.first().displayIcon,
                contentDescription = skin.displayName,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.BottomStart)
                    .wrapContentSize(),
            ) {
                Text(
                    text = skin.displayName,
                    color = bgColor.invertedAlpha,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.weight(1f))

                val (currency, price) = prices.entries.first()
                Text(
                    text = price.toString(),
                    color = bgColor.invertedAlpha,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.weight(0.05f))
                AsyncImage(
                    model = currency.displayIcon,
                    contentDescription = currency.displayName,
                    contentScale = ContentScale.FillBounds,
                    colorFilter = ColorFilter.tint(bgColor.invertedAlpha),
                    modifier = Modifier.fillMaxSize(0.15f)
                )
            }
        }
    }
}
