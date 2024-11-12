package com.valorant.store.domain.model.storefront

import com.valorant.store.data.config.serde.ItemType
import java.util.UUID

data class Storefront( // TODO: Add accessories?
    val bundles: List<Bundle>,
    val skinsPanel: SingleItemOffers,
    val nightMarket: NightMarket?
)

data class Bundle(
    val id: UUID,
    val currencyId: UUID,
    val durationRemainingInSeconds: Long,
    val totalBasePrice: Map<UUID, Int>?,
    val totalDiscountedPrice: Map<UUID, Int>?,
    val items: List<BundleItem>
)

data class BundleItem(
    val currencyId: UUID,
    val basePrice: Int,
    val discountedPrice: Int,
    val item: Item
)

data class SingleItemOffers(
    val durationRemainingInSeconds: Long,
    val items: List<SingleItemOffer>
)

data class SingleItemOffer(
    val prices: Map<UUID, Int>,
    val item: Item
)

data class NightMarket(
    val durationRemainingInSeconds: Long,
    val items: List<NightMarketOffer>
)

data class NightMarketOffer(
    val basePrices: Map<UUID, Int>,
    val discountedPrices: Map<UUID, Int>,
    val items: List<Item>
)

data class Item(
    val itemId: UUID,
    val itemType: ItemType,
    val quantity: Int
)
