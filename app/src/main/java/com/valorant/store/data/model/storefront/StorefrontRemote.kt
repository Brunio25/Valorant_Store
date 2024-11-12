package com.valorant.store.data.model.storefront

import com.fasterxml.jackson.annotation.JsonProperty
import com.valorant.store.data.config.serde.ItemType
import java.time.LocalDateTime
import java.util.UUID

data class StorefrontRemote(
    @JsonProperty("FeaturedBundle")
    val featuredBundle: FeaturedBundleRemote,
    @JsonProperty("SkinsPanelLayout")
    val skinsPanelLayout: SkinsPanelLayoutRemote,
    @JsonProperty("UpgradeCurrencyStore")
    val upgradeCurrencyStore: UpgradeCurrencyStoreRemote,
    @JsonProperty("AccessoryStore")
    val accessoryStore: AccessoryStoreRemote,
    @JsonProperty("PluginStores")
    val pluginStores: List<PluginStoreRemote>?,
    @JsonProperty("BonusStore")
    val bonusStore: BonusStoreRemote?
)

data class FeaturedBundleRemote(
    @JsonProperty("Bundle")
    val bundle: BundleRemote,
    @JsonProperty("Bundles")
    val bundles: List<BundleRemote>,
    @JsonProperty("BundleRemainingDurationInSeconds")
    val bundleRemainingDurationInSeconds: Long
)

data class SkinsPanelLayoutRemote(
    @JsonProperty("SingleItemOffers")
    val singleItemOffers: List<UUID>,
    @JsonProperty("SingleItemStoreOffers")
    val singleItemStoreOffers: List<OfferRemote>,
    @JsonProperty("SingleItemOffersRemainingDurationInSeconds")
    val singleItemOffersRemainingDurationInSeconds: Long
)

data class UpgradeCurrencyStoreRemote(
    @JsonProperty("UpgradeCurrencyOffers")
    val upgradeCurrencyOffers: List<UpgradeCurrencyOfferRemote>
)

data class AccessoryStoreRemote(
    @JsonProperty("AccessoryStoreOffers")
    val accessoryStoreOffers: List<AccessoryStoreOfferRemote>,
    @JsonProperty("AccessoryStoreRemainingDurationInSeconds")
    val accessoryStoreRemainingDurationInSeconds: Long,
    @JsonProperty("StorefrontID")
    val storefrontID: UUID
)

data class PluginStoreRemote(
    @JsonProperty("PluginID")
    val pluginID: UUID,
    @JsonProperty("PluginOffers")
    val pluginOffers: PluginOffersRemote
)

data class BundleRemote(
    @JsonProperty("ID")
    val id: UUID,
    @JsonProperty("DataAssetID")
    val dataAssetID: UUID,
    @JsonProperty("CurrencyID")
    val currencyID: UUID,
    @JsonProperty("Items")
    val items: List<ItemRemote>,
    @JsonProperty("ItemOffers")
    val itemOffers: List<ItemOfferRemote>?,
    @JsonProperty("TotalBaseCost")
    val totalBaseCost: Map<UUID, Int>?,
    @JsonProperty("TotalDiscountedCost")
    val totalDiscountedCost: Map<UUID, Int>?,
    @JsonProperty("TotalDiscountPercent")
    val totalDiscountPercent: Double,
    @JsonProperty("DurationRemainingInSeconds")
    val durationRemainingInSeconds: Long,
    @JsonProperty("WholesaleOnly")
    val wholesaleOnly: Boolean,
    @JsonProperty("IsGiftable")
    val isGiftable: String
)

data class ItemRemote(
    @JsonProperty("Item")
    val item: ItemInfoRemote,
    @JsonProperty("BasePrice")
    val basePrice: Int,
    @JsonProperty("CurrencyID")
    val currencyID: UUID,
    @JsonProperty("DiscountPercent")
    val discountPercent: Double,
    @JsonProperty("DiscountedPrice")
    val discountedPrice: Int,
    @JsonProperty("IsPromoItem")
    val isPromoItem: Boolean
)

data class ItemInfoRemote(
    @JsonProperty("ItemTypeID")
    val itemTypeID: ItemType,
    @JsonProperty("ItemID")
    val itemID: UUID,
    @JsonProperty("Amount")
    val amount: Int
)

data class ItemOfferRemote(
    @JsonProperty("BundleItemOfferID")
    val bundleItemOfferID: UUID,
    @JsonProperty("Offer")
    val offer: OfferRemote,
    @JsonProperty("DiscountPercent")
    val discountPercent: Double,
    @JsonProperty("DiscountedCost")
    val discountedCost: Map<UUID, Int>
)

data class RewardRemote(
    @JsonProperty("ItemTypeID")
    val itemTypeID: ItemType,
    @JsonProperty("ItemID")
    val itemID: UUID,
    @JsonProperty("Quantity")
    val quantity: Int
)

data class OfferRemote(
    @JsonProperty("OfferID")
    val offerID: UUID,
    @JsonProperty("IsDirectPurchase")
    val isDirectPurchase: Boolean,
    @JsonProperty("StartDate")
    val startDate: LocalDateTime,
    @JsonProperty("Cost")
    val cost: Map<UUID, Int>,
    @JsonProperty("Rewards")
    val rewards: List<RewardRemote>
)

data class UpgradeCurrencyOfferRemote(
    @JsonProperty("OfferID")
    val offerID: UUID,
    @JsonProperty("StorefrontItemID")
    val storefrontItemID: UUID,
    @JsonProperty("Offer")
    val offer: OfferRemote,
    @JsonProperty("DiscountedPercent")
    val discountedPercent: Double
)

data class AccessoryStoreOfferRemote(
    @JsonProperty("Offer")
    val offer: OfferRemote,
    @JsonProperty("ContractID")
    val contractID: UUID
)

data class PluginOffersRemote(
    @JsonProperty("StoreOffers")
    val storeOffers: List<StoreOfferRemote>,
    @JsonProperty("RemainingDurationInSeconds")
    val remainingDurationInSeconds: Long
)

data class StoreOfferRemote(
    @JsonProperty("PurchaseInformation")
    val purchaseInformation: PurchaseInformationRemote,
    @JsonProperty("SubOffers")
    val subOffers: List<SubOfferRemote>
)

data class PurchaseInformationRemote(
    @JsonProperty("DataAssetID")
    val dataAssetID: UUID,
    @JsonProperty("OfferID")
    val offerID: UUID,
    @JsonProperty("StartDate")
    val startDate: LocalDateTime,
    @JsonProperty("PrimaryCurrencyID")
    val primaryCurrencyID: UUID,
    @JsonProperty("Cost")
    val cost: Map<UUID, Int>,
    @JsonProperty("DiscountedCost")
    val discountedCost: Map<UUID, Int>,
    @JsonProperty("DiscountedPercentage")
    val discountedPercentage: Double,
    @JsonProperty("Rewards")
    val rewards: List<Any>, // TODO
    @JsonProperty("AdditionalContext")
    val additionalContext: List<Any>, // TODO
    @JsonProperty("WholesaleOnly")
    val wholesaleOnly: Boolean
)

data class SubOfferRemote(
    @JsonProperty("PurchaseInformation")
    val purchaseInformation: PurchaseInformationRemote
)

data class BonusStoreRemote(
    @JsonProperty("BonusStoreOffers")
    val bonusStoreOffers: List<BonusStoreOfferRemote>,
    @JsonProperty("BonusStoreRemainingDurationInSeconds")
    val bonusStoreRemainingDurationInSeconds: Long,
    @JsonProperty("BonusStoreSecondsSinceItStarted")
    val bonusStoreSecondsSinceItStarted: Long
)

data class BonusStoreOfferRemote(
    @JsonProperty("BonusOfferID")
    val bonusOfferID: UUID,
    @JsonProperty("Offer")
    val offer: OfferRemote,
    @JsonProperty("DiscountPercent")
    val discountPercent: Int,
    @JsonProperty("DiscountCosts")
    val discountCosts: Map<UUID, Int>,
    @JsonProperty("IsSeen")
    val isSeen: Boolean
)
