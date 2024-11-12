package com.valorant.store.data.mappers.storefront

import com.valorant.store.data.config.serde.ItemType
import com.valorant.store.data.model.storefront.BonusStoreOfferRemote
import com.valorant.store.data.model.storefront.BonusStoreRemote
import com.valorant.store.data.model.storefront.BundleRemote
import com.valorant.store.data.model.storefront.ItemInfoRemote
import com.valorant.store.data.model.storefront.ItemRemote
import com.valorant.store.data.model.storefront.OfferRemote
import com.valorant.store.data.model.storefront.RewardRemote
import com.valorant.store.data.model.storefront.SkinsPanelLayoutRemote
import com.valorant.store.data.model.storefront.StorefrontRemote
import com.valorant.store.domain.model.storefront.Bundle
import com.valorant.store.domain.model.storefront.BundleItem
import com.valorant.store.domain.model.storefront.Item
import com.valorant.store.domain.model.storefront.NightMarket
import com.valorant.store.domain.model.storefront.NightMarketOffer
import com.valorant.store.domain.model.storefront.SingleItemOffer
import com.valorant.store.domain.model.storefront.SingleItemOffers
import com.valorant.store.domain.model.storefront.Storefront

fun StorefrontRemote.toDomain(): Storefront = Storefront(
    bundles = featuredBundle.bundles.map { it.toDomain() },
    skinsPanel = skinsPanelLayout.toDomain(),
    nightMarket = bonusStore?.toDomain()
)

private fun BundleRemote.toDomain(): Bundle = Bundle(
    id = id,
    currencyId = currencyID,
    durationRemainingInSeconds = durationRemainingInSeconds,
    totalBasePrice = totalBaseCost,
    totalDiscountedPrice = totalDiscountedCost,
    items = items.map { it.toDomain() }
)

private fun ItemRemote.toDomain(): BundleItem = BundleItem(
    currencyId = currencyID,
    basePrice = basePrice,
    discountedPrice = discountedPrice,
    item = item.toDomain()
)

private fun ItemInfoRemote.toDomain(): Item = Item(
    itemId = itemID,
    itemType = itemTypeID,
    quantity = amount
)

private fun SkinsPanelLayoutRemote.toDomain(): SingleItemOffers = SingleItemOffers(
    singleItemOffersRemainingDurationInSeconds,
    items = singleItemStoreOffers.map { it.toDomain() }
)

private fun OfferRemote.toDomain(): SingleItemOffer = SingleItemOffer(
    prices = cost,
    item = rewards.map { it.toDomain() }
        .first { it.itemType == ItemType.SKIN_LEVEL_CONTENT }
)

private fun BonusStoreRemote.toDomain(): NightMarket = NightMarket(
    durationRemainingInSeconds = bonusStoreRemainingDurationInSeconds,
    items = bonusStoreOffers.map { it.toDomain() }
)

private fun BonusStoreOfferRemote.toDomain(): NightMarketOffer = NightMarketOffer(
    basePrices = offer.cost,
    discountedPrices = discountCosts,
    items = offer.rewards.map { it.toDomain() }
)

private fun RewardRemote.toDomain(): Item = Item(
    itemId = itemID,
    itemType = itemTypeID,
    quantity = quantity
)
