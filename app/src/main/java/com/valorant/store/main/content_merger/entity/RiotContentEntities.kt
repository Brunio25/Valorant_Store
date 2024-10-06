package com.valorant.store.main.content_merger.entity

import com.valorant.store.api.riot.store.entity.ItemEntity
import com.valorant.store.api.val_api.content.entity.CurrencyEntity
import java.util.UUID

data class RiotContentEntity( // TODO: Add accessories?
    val bundle: BundleContentEntity,
    val skinsPanel: SingleItemOffersContentEntity,
    val nightMarket: NightMarketContentEntity?
)

data class BundleContentEntity(
    val id: UUID,
    val currency: CurrencyEntity?,
    val durationRemainingInSeconds: Long,
    val totalBasePrice: Int?,
    val totalDiscountedPrice: Int?,
    val items: List<BundleItemContentEntity>
)

data class BundleItemContentEntity(
    val currency: CurrencyEntity?,
    val basePrice: Int,
    val discountedPrice: Int,
    val item: ItemEntity
)

data class SingleItemOffersContentEntity(
    val durationRemainingInSeconds: Long,
    val items: List<SingleItemOfferContentEntity>
)

data class SingleItemOfferContentEntity(
    val prices: PriceContentEntity?,
    val item: ItemEntity
)

data class NightMarketContentEntity(
    val durationRemainingInSeconds: Long,
    val items: List<NightMarketOfferContentEntity>
)

data class NightMarketOfferContentEntity(
    val basePrices: PriceContentEntity?,
    val discountedPrice: PriceContentEntity?,
    val items: List<ItemEntity>
)

data class PriceContentEntity(
    val currency: CurrencyEntity,
    val price: Int
)
