package com.valorant.store.api.riot.store.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.util.UUID

data class StorefrontDTO(
    @SerializedName("FeaturedBundle")
    val featuredBundle: FeaturedBundleDTO,
    @SerializedName("SkinsPanelLayout")
    val skinsPanelLayout: SkinsPanelLayoutDTO,
    @SerializedName("UpgradeCurrencyStore")
    val upgradeCurrencyStore: UpgradeCurrencyStoreDTO,
    @SerializedName("AccessoryStore")
    val accessoryStore: AccessoryStoreDTO,
    @SerializedName("PluginStores")
    val pluginStores: List<PluginStoreDTO>
)

data class FeaturedBundleDTO(
    @SerializedName("Bundle")
    val bundle: BundleDTO,
    @SerializedName("Bundles")
    val bundles: List<BundleDTO>,
    @SerializedName("BundleRemainingDurationInSeconds")
    val bundleRemainingDurationInSeconds: Long
)

data class SkinsPanelLayoutDTO(
    @SerializedName("SingleItemOffers")
    val singleItemOffers: List<UUID>,
    @SerializedName("SingleItemStoreOffers")
    val singleItemStoreOffers: List<OfferDTO>,
    @SerializedName("SingleItemOffersRemainingDurationInSeconds")
    val singleItemOffersRemainingDurationInSeconds: Long
)

data class UpgradeCurrencyStoreDTO(
    @SerializedName("UpgradeCurrencyOffers")
    val upgradeCurrencyOffers: List<UpgradeCurrencyOfferDTO>
)

data class AccessoryStoreDTO(
    @SerializedName("AccessoryStoreOffers")
    val accessoryStoreOffers: List<AccessoryStoreOfferDTO>,
    @SerializedName("AccessoryStoreRemainingDurationInSeconds")
    val accessoryStoreRemainingDurationInSeconds: Long,
    @SerializedName("StorefrontID")
    val storefrontID: UUID
)

data class PluginStoreDTO(
    @SerializedName("PluginID")
    val pluginID: UUID,
    @SerializedName("PluginOffers")
    val pluginOffers: PluginOffersDTO
)

data class BundleDTO(
    @SerializedName("ID")
    val id: UUID,
    @SerializedName("DataAssetID")
    val dataAssetID: UUID,
    @SerializedName("CurrencyID")
    val currencyID: UUID,
    @SerializedName("Items")
    val items: List<ItemDTO>,
    @SerializedName("ItemOffers")
    val itemOffers: List<ItemOfferDTO>?,
    @SerializedName("TotalBaseCost")
    val totalBaseCost: Map<UUID, Int>?,
    @SerializedName("TotalDiscountedCost")
    val totalDiscountedCost: Map<UUID, Int>?,
    @SerializedName("TotalDiscountPercent")
    val totalDiscountPercent: Double,
    @SerializedName("DurationRemainingInSeconds")
    val durationRemainingInSeconds: Long,
    @SerializedName("WholesaleOnly")
    val wholesaleOnly: Boolean
)

data class ItemDTO(
    @SerializedName("Item")
    val item: ItemInfoDTO,
    @SerializedName("BasePrice")
    val basePrice: Int,
    @SerializedName("CurrencyID")
    val currencyID: UUID,
    @SerializedName("DiscountPercent")
    val discountPercent: Double,
    @SerializedName("DiscountedPrice")
    val discountedPrice: Int,
    @SerializedName("IsPromoItem")
    val isPromoItem: Boolean
)

data class ItemInfoDTO(
    @SerializedName("ItemTypeID")
    val itemTypeID: UUID,
    @SerializedName("ItemID")
    val itemID: UUID,
    @SerializedName("Amount")
    val amount: Int
)

data class ItemOfferDTO(
    @SerializedName("BundleItemOfferID")
    val bundleItemOfferID: UUID,
    @SerializedName("Offer")
    val offer: OfferDTO,
    @SerializedName("DiscountPercent")
    val discountPercent: Double,
    @SerializedName("DiscountedCost")
    val discountedCost: Map<UUID, Int>
)

data class RewardDTO(
    @SerializedName("ItemTypeID")
    val itemTypeID: UUID,
    @SerializedName("ItemID")
    val itemID: UUID,
    @SerializedName("Quantity")
    val quantity: Int
)

data class OfferDTO(
    @SerializedName("OfferID")
    val offerID: UUID,
    @SerializedName("IsDirectPurchase")
    val isDirectPurchase: Boolean,
    @SerializedName("StartDate")
    val startDate: LocalDateTime,
    @SerializedName("Cost")
    val cost: Map<UUID, Int>,
    @SerializedName("Rewards")
    val rewards: List<RewardDTO>
)

data class UpgradeCurrencyOfferDTO(
    @SerializedName("OfferID")
    val offerID: UUID,
    @SerializedName("StorefrontItemID")
    val storefrontItemID: UUID,
    @SerializedName("Offer")
    val offer: OfferDTO,
    @SerializedName("DiscountedPercent")
    val discountedPercent: Double
)

data class AccessoryStoreOfferDTO(
    @SerializedName("Offer")
    val offer: OfferDTO,
    @SerializedName("ContractID")
    val contractID: UUID
)

data class PluginOffersDTO(
    @SerializedName("StoreOffers")
    val storeOffers: List<StoreOfferDTO>,
    @SerializedName("RemainingDurationInSeconds")
    val remainingDurationInSeconds: Long
)

data class StoreOfferDTO(
    @SerializedName("PurchaseInformation")
    val purchaseInformation: PurchaseInformationDTO,
    @SerializedName("SubOffers")
    val subOffers: List<SubOfferDTO>
)

data class PurchaseInformationDTO(
    @SerializedName("DataAssetID")
    val dataAssetID: UUID,
    @SerializedName("OfferID")
    val offerID: UUID,
    @SerializedName("StartDate")
    val startDate: LocalDateTime,
    @SerializedName("PrimaryCurrencyID")
    val primaryCurrencyID: UUID,
    @SerializedName("Cost")
    val cost: Map<UUID, Int>,
    @SerializedName("DiscountedCost")
    val discountedCost: Map<UUID, Int>,
    @SerializedName("DiscountedPercentage")
    val discountedPercentage: Double,
    @SerializedName("Rewards")
    val rewards: List<Any>, // TODO
    @SerializedName("AdditionalContext")
    val additionalContext: List<Any>, // TODO
    @SerializedName("WholesaleOnly")
    val wholesaleOnly: Boolean
)

data class SubOfferDTO(
    @SerializedName("PurchaseInformation")
    val purchaseInformation: PurchaseInformationDTO
)
