package com.valorant.store.api.config

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

enum class ItemType(val value: String) {
    EQUIPPABLE_CONTENT("51c9eb99-3e6b-4658-801f-a5a7fd64bb9d"),
    SKIN_CONTENT("bcef87d6-209b-46c6-8b19-fbe40bd95abc"),
    SKIN_LEVEL_CONTENT("e7c63390-eda7-46e0-bb7a-a6abdacd2433"),
    SKIN_CHROMA_CONTENT("3ad1b2b2-acdb-4524-852f-954a76ddae0a"),
    CHARM_CONTENT("77258665-71d1-4623-bc72-44db9bd5b3b3"),
    CHARM_LEVEL_CONTENT("dd3bf334-87f3-40bd-b043-682a57a8dc3a"),
    ATTACHMENT_CONTENT("6520634c-bd1e-4fc4-81af-cac5dc723105"),
    CHARACTER_CONTENT("01bb38e1-da47-4e6a-9b3d-945fe4655707"),
    SPRAY_CONTENT("d5f120f8-ff8c-4aac-92ea-f2b5acbe9475"),
    SPRAY_LEVEL_CONTENT("290f8769-97c6-492a-a1a8-caacf3d5b325"),
    PLAYER_CARD_CONTENT("3f296c07-64c3-494c-923b-fe692a4fa1bd"),
    MISSION_CONTENT("ac3c307a-368f-4db8-940d-68914b26d89a"),
    PLAYER_TITLE_CONTENT("de7caa6b-adf7-4588-bbd1-143831e786c6"),
    CONTRACT_CONTENT("0381b6a6-e901-4225-a30c-b18afc6d0ad4"),
    PREMIUM_CONTRACT_CONTENT("f85cb6f7-33e5-4dc8-b609-ec7212301948"),
    TOTEM_CONTENT("03a572de-4234-31ed-d344-ababa488f981"),
    PERMANENT_ENTITLEMENT("4e60e748-bce6-4faa-9327-ebbe6089d5fe"),
    LOYALTY_ENTITLEMENT("e632de5f-3ef9-45fe-97f2-10ee16ac1d50"),
    XGP_ENTITLEMENT("7a9330bf-8266-4b13-85e5-8013f63a63ae"),
    CURRENCY_REWARD("ea6fcd2e-8373-4137-b1c0-b458947aa86d"),
    CONTRACT_XP_CURRENCY_ID("8e755510-5d2b-4921-ad75-bed2de113b18"),
    F2P_ENTITLEMENT("ca37f5be-0b15-4917-a0e7-a6e8600489f2");

    companion object {
        fun of(itemType: String) = entries.find { it.value == itemType }
            ?: throw Exception("ItemType Not Found: $itemType")
    }
}

class ItemTypeCustomDeserializer : JsonDeserializer<ItemType> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ItemType = ItemType.of(json.asString)
}