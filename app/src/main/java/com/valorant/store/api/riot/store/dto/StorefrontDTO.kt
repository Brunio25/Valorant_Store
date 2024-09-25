package com.valorant.store.api.riot.store.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.valorant.store.api.config.ItemType
import java.time.LocalDateTime
import java.util.UUID

data class StorefrontDTO(
    @JsonProperty("FeaturedBundle")
    val featuredBundle: FeaturedBundleDTO,
    @JsonProperty("SkinsPanelLayout")
    val skinsPanelLayout: SkinsPanelLayoutDTO,
    @JsonProperty("UpgradeCurrencyStore")
    val upgradeCurrencyStore: UpgradeCurrencyStoreDTO,
    @JsonProperty("AccessoryStore")
    val accessoryStore: AccessoryStoreDTO,
    @JsonProperty("PluginStores")
    val pluginStores: List<PluginStoreDTO>?,
    @JsonProperty("BonusStore")
    val bonusStore: BonusStoreDTO?
)

data class FeaturedBundleDTO(
    @JsonProperty("Bundle")
    val bundle: BundleDTO,
    @JsonProperty("Bundles")
    val bundles: List<BundleDTO>,
    @JsonProperty("BundleRemainingDurationInSeconds")
    val bundleRemainingDurationInSeconds: Long
)

data class SkinsPanelLayoutDTO(
    @JsonProperty("SingleItemOffers")
    val singleItemOffers: List<UUID>,
    @JsonProperty("SingleItemStoreOffers")
    val singleItemStoreOffers: List<OfferDTO>,
    @JsonProperty("SingleItemOffersRemainingDurationInSeconds")
    val singleItemOffersRemainingDurationInSeconds: Long
)

data class UpgradeCurrencyStoreDTO(
    @JsonProperty("UpgradeCurrencyOffers")
    val upgradeCurrencyOffers: List<UpgradeCurrencyOfferDTO>
)

data class AccessoryStoreDTO(
    @JsonProperty("AccessoryStoreOffers")
    val accessoryStoreOffers: List<AccessoryStoreOfferDTO>,
    @JsonProperty("AccessoryStoreRemainingDurationInSeconds")
    val accessoryStoreRemainingDurationInSeconds: Long,
    @JsonProperty("StorefrontID")
    val storefrontID: UUID
)

data class PluginStoreDTO(
    @JsonProperty("PluginID")
    val pluginID: UUID,
    @JsonProperty("PluginOffers")
    val pluginOffers: PluginOffersDTO
)

data class BundleDTO(
    @JsonProperty("ID")
    val id: UUID,
    @JsonProperty("DataAssetID")
    val dataAssetID: UUID,
    @JsonProperty("CurrencyID")
    val currencyID: UUID,
    @JsonProperty("Items")
    val items: List<ItemDTO>,
    @JsonProperty("ItemOffers")
    val itemOffers: List<ItemOfferDTO>?,
    @JsonProperty("TotalBaseCost")
    val totalBaseCost: Map<UUID, Int>?,
    @JsonProperty("TotalDiscountedCost")
    val totalDiscountedCost: Map<UUID, Int>?,
    @JsonProperty("TotalDiscountPercent")
    val totalDiscountPercent: Double,
    @JsonProperty("DurationRemainingInSeconds")
    val durationRemainingInSeconds: Long,
    @JsonProperty("WholesaleOnly")
    val wholesaleOnly: Boolean
)

data class ItemDTO(
    @JsonProperty("Item")
    val item: ItemInfoDTO,
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

data class ItemInfoDTO(
    @JsonProperty("ItemTypeID")
    val itemTypeID: ItemType,
    @JsonProperty("ItemID")
    val itemID: UUID,
    @JsonProperty("Amount")
    val amount: Int
)

data class ItemOfferDTO(
    @JsonProperty("BundleItemOfferID")
    val bundleItemOfferID: UUID,
    @JsonProperty("Offer")
    val offer: OfferDTO,
    @JsonProperty("DiscountPercent")
    val discountPercent: Double,
    @JsonProperty("DiscountedCost")
    val discountedCost: Map<UUID, Int>
)

data class RewardDTO(
    @JsonProperty("ItemTypeID")
    val itemTypeID: ItemType,
    @JsonProperty("ItemID")
    val itemID: UUID,
    @JsonProperty("Quantity")
    val quantity: Int
)

data class OfferDTO(
    @JsonProperty("OfferID")
    val offerID: UUID,
    @JsonProperty("IsDirectPurchase")
    val isDirectPurchase: Boolean,
    @JsonProperty("StartDate")
    val startDate: LocalDateTime,
    @JsonProperty("Cost")
    val cost: Map<UUID, Int>,
    @JsonProperty("Rewards")
    val rewards: List<RewardDTO>
)

data class UpgradeCurrencyOfferDTO(
    @JsonProperty("OfferID")
    val offerID: UUID,
    @JsonProperty("StorefrontItemID")
    val storefrontItemID: UUID,
    @JsonProperty("Offer")
    val offer: OfferDTO,
    @JsonProperty("DiscountedPercent")
    val discountedPercent: Double
)

data class AccessoryStoreOfferDTO(
    @JsonProperty("Offer")
    val offer: OfferDTO,
    @JsonProperty("ContractID")
    val contractID: UUID
)

data class PluginOffersDTO(
    @JsonProperty("StoreOffers")
    val storeOffers: List<StoreOfferDTO>,
    @JsonProperty("RemainingDurationInSeconds")
    val remainingDurationInSeconds: Long
)

data class StoreOfferDTO(
    @JsonProperty("PurchaseInformation")
    val purchaseInformation: PurchaseInformationDTO,
    @JsonProperty("SubOffers")
    val subOffers: List<SubOfferDTO>
)

data class PurchaseInformationDTO(
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

data class SubOfferDTO(
    @JsonProperty("PurchaseInformation")
    val purchaseInformation: PurchaseInformationDTO
)

data class BonusStoreDTO(
    @JsonProperty("BonusStoreOffers")
    val bonusStoreOffers: List<BonusStoreOfferDTO>,
    @JsonProperty("BonusStoreRemainingDurationInSeconds")
    val bonusStoreRemainingDurationInSeconds: Long,
    @JsonProperty("BonusStoreSecondsSinceItStarted")
    val bonusStoreSecondsSinceItStarted: Long
)

data class BonusStoreOfferDTO(
    @JsonProperty("BonusOfferID")
    val bonusOfferID: UUID,
    @JsonProperty("Offer")
    val offer: OfferDTO,
    @JsonProperty("DiscountPercent")
    val discountPercent: Int,
    @JsonProperty("DiscountCosts")
    val discountCosts: Map<UUID, Int>,
    @JsonProperty("IsSeen")
    val isSeen: Boolean
)
