package com.valorant.store.api.util

import android.net.Uri
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class UriCustomDeserializer : JsonDeserializer<Uri> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Uri = Uri.parse(json.asString)
}