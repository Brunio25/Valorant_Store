package com.valorant.store.domain.mapper.storefront

import com.valorant.store.app.model.storefront.BundleItemUI
import com.valorant.store.app.model.storefront.BundleUi
import com.valorant.store.app.model.storefront.NightMarketOfferUI
import com.valorant.store.app.model.storefront.NightMarketUI
import com.valorant.store.app.model.storefront.SingleItemOfferUI
import com.valorant.store.app.model.storefront.SingleItemOffersUI
import com.valorant.store.app.model.storefront.SkinItemUI
import com.valorant.store.app.model.storefront.StorefrontUI
import com.valorant.store.data.config.serde.ItemType
import com.valorant.store.domain.mapper.valInfo.toUi
import com.valorant.store.domain.model.currencies.Currency
import com.valorant.store.domain.model.storefront.Bundle
import com.valorant.store.domain.model.storefront.BundleItem
import com.valorant.store.domain.model.storefront.Item
import com.valorant.store.domain.model.storefront.NightMarket
import com.valorant.store.domain.model.storefront.NightMarketOffer
import com.valorant.store.domain.model.storefront.SingleItemOffer
import com.valorant.store.domain.model.storefront.SingleItemOffers
import com.valorant.store.domain.model.storefront.Storefront
import com.valorant.store.domain.model.valInfo.ValInfoDataStream
import java.util.UUID

fun Storefront.toUi(valInfo: ValInfoDataStream) = filter().run {
    StorefrontUI(
        bundles = bundles.map { it.toUi(valInfo) },
        skinsPanel = skinsPanel.toUi(valInfo),
        nightMarket = nightMarket?.toUi(valInfo)
    )
}

private fun Storefront.filter(): Storefront = copy(
    bundles = bundles
        .map { bundle ->
            bundle.copy(
                items = bundle.items.filter { it.item.itemType == ItemType.SKIN_LEVEL_CONTENT }
            )
        }.filter { it.items.isNotEmpty() },
    nightMarket = nightMarket?.run {
        copy(
            items = items
                .filter { it.items.first().itemType == ItemType.SKIN_LEVEL_CONTENT }
        )
    }
)

private fun Bundle.toUi(valInfo: ValInfoDataStream): BundleUi =
    BundleUi(
        currency = valInfo.currencies?.get(currencyId),
        durationRemainingInSeconds = durationRemainingInSeconds,
        totalBasePrice = totalBasePrice?.toUi(valInfo),
        totalDiscountedPrice = totalDiscountedPrice?.toUi(valInfo),
        items = items.mapNotNull { it.toUi(valInfo) }
    )

private fun BundleItem.toUi(valInfo: ValInfoDataStream): BundleItemUI? = item.toUi(valInfo)?.let {
    BundleItemUI(
        currency = valInfo.currencies?.get(currencyId),
        basePrices = basePrice,
        discountedPrices = discountedPrice,
        item = it
    )
}

private fun SingleItemOffers.toUi(valInfo: ValInfoDataStream): SingleItemOffersUI =
    SingleItemOffersUI(
        durationRemainingInSeconds = durationRemainingInSeconds,
        items = items.mapNotNull { it.toUi(valInfo) }
    )

private fun SingleItemOffer.toUi(valInfo: ValInfoDataStream): SingleItemOfferUI? =
    item.toUi(valInfo)?.let {
        SingleItemOfferUI(
            prices = prices.toUi(valInfo),
            item = it
        )
    }

private fun NightMarket.toUi(valInfo: ValInfoDataStream): NightMarketUI = NightMarketUI(
    durationRemainingInSeconds = durationRemainingInSeconds,
    items = items.map { it.toUi(valInfo) }
)

private fun NightMarketOffer.toUi(valInfo: ValInfoDataStream): NightMarketOfferUI =
    NightMarketOfferUI(
        basePrices = basePrices.toUi(valInfo),
        discountedPrices = discountedPrices.toUi(valInfo),
        items = items.mapNotNull { it.toUi(valInfo) }
    )

private fun Map<UUID, Int>.toUi(valInfo: ValInfoDataStream): Map<Currency, Int> =
    entries.mapNotNull { entry ->
        valInfo.currencies?.get(entry.key)?.let {
            it to entry.value
        }
    }.toMap()


private fun Item.toUi(valInfo: ValInfoDataStream): SkinItemUI? = valInfo.skins?.get(itemId)
    ?.let {
        SkinItemUI(
            quantity = quantity,
            skin = it.toUi()
        )
    }
