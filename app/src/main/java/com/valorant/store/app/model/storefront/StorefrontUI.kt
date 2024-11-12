package com.valorant.store.app.model.storefront

import com.valorant.store.app.model.valInfo.SkinUi
import com.valorant.store.domain.model.currencies.Currency

data class StorefrontUI( // TODO: Add accessories?
    val bundles: List<BundleUi>,
    val skinsPanel: SingleItemOffersUI,
    val nightMarket: NightMarketUI?
)

data class BundleUi(
    val currency: Currency?,
    val durationRemainingInSeconds: Long,
    val totalBasePrice: Map<Currency, Int>?,
    val totalDiscountedPrice: Map<Currency, Int>?,
    val items: List<BundleItemUI>
)

data class BundleItemUI(
    val currency: Currency?,
    val basePrices: Int,
    val discountedPrices: Int,
    val item: SkinItemUI
)

data class SingleItemOffersUI(
    val durationRemainingInSeconds: Long,
    val items: List<SingleItemOfferUI>
)

data class SingleItemOfferUI(
    val prices: Map<Currency, Int>,
    val item: SkinItemUI
)

data class NightMarketUI(
    val durationRemainingInSeconds: Long,
    val items: List<NightMarketOfferUI>
)

data class NightMarketOfferUI(
    val basePrices: Map<Currency, Int>,
    val discountedPrices: Map<Currency, Int>,
    val items: List<SkinItemUI>
)

data class SkinItemUI(
    val quantity: Int,
    val skin: SkinUi
)
