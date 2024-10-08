package com.valorant.store.api.riot.store.entity

import com.valorant.store.api.config.ItemType
import java.util.UUID

data class StorefrontEntity( // TODO: Add accessories?
    val bundle: BundleEntity,
    val skinsPanel: SingleItemOffersEntity,
    val nightMarket: NightMarketEntity?
)

data class BundleEntity(
    val id: UUID,
    val currencyId: UUID,
    val durationRemainingInSeconds: Long,
    val totalBasePrice: Map<UUID, Int>?,
    val totalDiscountedPrice: Map<UUID, Int>?,
    val items: List<BundleItemEntity>
)

data class BundleItemEntity(
    val currencyId: UUID,
    val basePrice: Int,
    val discountedPrice: Int,
    val item: ItemEntity
)

data class SingleItemOffersEntity(
    val durationRemainingInSeconds: Long,
    val items: List<SingleItemOfferEntity>
)

data class SingleItemOfferEntity(
    val prices: Map<UUID, Int>,
    val item: ItemEntity
)

data class ItemEntity(
    val itemId: UUID,
    val itemType: ItemType,
    val quantity: Int
)

data class NightMarketEntity(
    val durationRemainingInSeconds: Long,
    val items: List<NightMarketOfferEntity>
)

data class NightMarketOfferEntity(
    val basePrices: Map<UUID, Int>,
    val discountedPrice: Map<UUID, Int>,
    val items: List<ItemEntity>
)