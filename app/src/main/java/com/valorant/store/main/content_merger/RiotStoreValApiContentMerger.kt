package com.valorant.store.main.content_merger

import com.valorant.store.api.riot.store.entity.BundleEntity
import com.valorant.store.api.riot.store.entity.BundleItemEntity
import com.valorant.store.api.riot.store.entity.NightMarketEntity
import com.valorant.store.api.riot.store.entity.NightMarketOfferEntity
import com.valorant.store.api.riot.store.entity.SingleItemOfferEntity
import com.valorant.store.api.riot.store.entity.SingleItemOffersEntity
import com.valorant.store.api.riot.store.entity.StorefrontEntity
import com.valorant.store.api.val_api.content.ValInfoRepository
import com.valorant.store.main.content_merger.entity.BundleContentEntity
import com.valorant.store.main.content_merger.entity.BundleItemContentEntity
import com.valorant.store.main.content_merger.entity.NightMarketContentEntity
import com.valorant.store.main.content_merger.entity.NightMarketOfferContentEntity
import com.valorant.store.main.content_merger.entity.PriceContentEntity
import com.valorant.store.main.content_merger.entity.RiotContentEntity
import com.valorant.store.main.content_merger.entity.SingleItemOfferContentEntity
import com.valorant.store.main.content_merger.entity.SingleItemOffersContentEntity
import java.util.UUID

class RiotStoreValApiContentMerger(private val contentRepository: ValInfoRepository) {
    fun merge(storefront: StorefrontEntity) =
        RiotContentEntity(
            bundle = toBundleContentEntity(storefront.bundle),
            skinsPanel = toSingleItemOffersContentEntity(storefront.skinsPanel),
            nightMarket = storefront.nightMarket?.let { toNightMarketContentEntity(storefront.nightMarket) }
        )

    private fun toBundleContentEntity(bundle: BundleEntity): BundleContentEntity = with(bundle) {
        BundleContentEntity(
            id = id,
            currency = contentRepository.getCurrencyById(currencyId),
            durationRemainingInSeconds = durationRemainingInSeconds,
            totalBasePrice = totalBasePrice?.values?.firstOrNull(), // TODO: Add logic to pick currencies
            totalDiscountedPrice = totalDiscountedPrice?.values?.firstOrNull(), // TODO: Add logic to pick currencies
            items = items.map { toBundleItemContentEntity(it) }
        )
    }

    private fun toBundleItemContentEntity(bundleItemEntity: BundleItemEntity): BundleItemContentEntity =
        with(bundleItemEntity) {
            BundleItemContentEntity(
                currency = contentRepository.getCurrencyById(currencyId),
                basePrice = basePrice,
                discountedPrice = discountedPrice,
                item = item.copy()
            )
        }

    private fun toSingleItemOffersContentEntity(offersEntity: SingleItemOffersEntity): SingleItemOffersContentEntity =
        with(offersEntity) {
            SingleItemOffersContentEntity(
                durationRemainingInSeconds = durationRemainingInSeconds,
                items = items.map { toSingleItemOfferContentEntity(it) }
            )
        }

    private fun toSingleItemOfferContentEntity(offerEntity: SingleItemOfferEntity): SingleItemOfferContentEntity =
        with(offerEntity) {
            SingleItemOfferContentEntity(
                prices = toPriceContentEntity(prices),
                item = item.copy()
            )
        }

    private fun toNightMarketContentEntity(nightMarket: NightMarketEntity): NightMarketContentEntity =
        with(nightMarket) {
            NightMarketContentEntity(
                durationRemainingInSeconds = durationRemainingInSeconds,
                items = items.map { toNightMarketOfferContentEntity(it) }
            )
        }

    private fun toNightMarketOfferContentEntity(nightMarketOffer: NightMarketOfferEntity) =
        with(nightMarketOffer) {
            NightMarketOfferContentEntity(
                basePrices = toPriceContentEntity(basePrices),
                discountedPrice = toPriceContentEntity(discountedPrice),
                items = items.map { it.copy() }
            )
        }

    private fun toPriceContentEntity(prices: Map<UUID, Int>): PriceContentEntity? =
        contentRepository.getCurrencyById(prices.keys.first())?.let {
            PriceContentEntity(
                currency = it,
                price = prices.values.first()
            )
        }
}
