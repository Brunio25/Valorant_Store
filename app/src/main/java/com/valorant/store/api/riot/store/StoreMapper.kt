package com.valorant.store.api.riot.store

import android.util.Log
import com.valorant.store.api.config.ItemType
import com.valorant.store.api.riot.store.dto.BundleDTO
import com.valorant.store.api.riot.store.dto.ItemDTO
import com.valorant.store.api.riot.store.dto.OfferDTO
import com.valorant.store.api.riot.store.dto.RewardDTO
import com.valorant.store.api.riot.store.dto.SkinsPanelLayoutDTO
import com.valorant.store.api.riot.store.dto.StorefrontDTO
import com.valorant.store.api.riot.store.entity.BundleEntity
import com.valorant.store.api.riot.store.entity.BundleItemEntity
import com.valorant.store.api.riot.store.entity.ItemEntity
import com.valorant.store.api.riot.store.entity.SingleItemOfferEntity
import com.valorant.store.api.riot.store.entity.SingleItemOffersEntity
import com.valorant.store.api.riot.store.entity.StorefrontEntity

object StoreMapper {
    fun toStorefrontEntity(storefrontDTO: StorefrontDTO): StorefrontEntity = StorefrontEntity(
        bundle = with(storefrontDTO.featuredBundle.bundles.first()) {
            Log.w("STOREFRONT_BUNDLE", "Multiple featureBundles $this")
            toBundleEntity(this)
        },
        skinsPanel = toSingleItemOffers(storefrontDTO.skinsPanelLayout)
    )

    private fun toBundleEntity(bundleDTO: BundleDTO): BundleEntity = with(bundleDTO) {
        BundleEntity(
            id = id,
            currencyId = currencyID,
            durationRemainingInSeconds = durationRemainingInSeconds,
            totalBasePrice = totalBaseCost,
            totalDiscountedPrice = totalDiscountedCost,
            items = items.map { toBundleItemEntity(it) }
        )
    }

    private fun toBundleItemEntity(itemDTO: ItemDTO): BundleItemEntity = with(itemDTO) {
        BundleItemEntity(
            currencyId = currencyID,
            basePrice = basePrice,
            discountedPrice = discountedPrice,
            item = ItemEntity(
                itemId = item.itemID,
                itemType = item.itemTypeID,
                quantity = item.amount
            )
        )
    }

    private fun toSingleItemOffers(skinsPanelLayout: SkinsPanelLayoutDTO): SingleItemOffersEntity =
        with(skinsPanelLayout) {
            SingleItemOffersEntity(
                singleItemOffersRemainingDurationInSeconds,
                items = singleItemStoreOffers.map { toSingleItemOfferEntity(it) }
            )
        }

    private fun toSingleItemOfferEntity(offerDTO: OfferDTO): SingleItemOfferEntity =
        with(offerDTO) {
            SingleItemOfferEntity(
                price = this.cost,
                item = this.rewards.map { rewardToItemEntity(it) }
                    .first { it.itemType == ItemType.SKIN_LEVEL_CONTENT }
            )
        }

    private fun rewardToItemEntity(rewardDTO: RewardDTO): ItemEntity = with(rewardDTO) {
        ItemEntity(
            itemId = itemID,
            itemType = itemTypeID,
            quantity = quantity
        )
    }
}
