package com.valorant.store.api.config

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.valorant.store.api.val_api.skins.entity.ContentTierEntity
import com.valorant.store.api.val_api.skins.entity.ContentTierMapEntity
import com.valorant.store.api.val_api.skins.entity.CurrencyEntity
import com.valorant.store.api.val_api.skins.entity.CurrencyMapEntity
import com.valorant.store.api.val_api.skins.entity.SkinEntity
import com.valorant.store.api.val_api.skins.entity.SkinMapEntity
import java.lang.reflect.Type
import java.util.UUID

class SkinMapEntityCustomDeserializer : JsonDeserializer<SkinMapEntity> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): SkinMapEntity = json.deserializeUUIDKeyMap<SkinEntity>(context)
}

class CurrencyMapEntityCustomDeserializer : JsonDeserializer<CurrencyMapEntity> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): CurrencyMapEntity = json.deserializeUUIDKeyMap<CurrencyEntity>(context)
}

class ContentTierMapEntityCustomDeserializer : JsonDeserializer<ContentTierMapEntity> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ContentTierMapEntity = json.deserializeUUIDKeyMap<ContentTierEntity>(context)
}

private inline fun <reified V> JsonElement.deserializeUUIDKeyMap(context: JsonDeserializationContext): Map<UUID, V> =
    asJsonObject.entrySet().associate {
        (UUID.fromString(it.key) to context.deserialize<V>(it.value, V::class.java))
    }
