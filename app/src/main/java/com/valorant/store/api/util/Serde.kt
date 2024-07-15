package com.valorant.store.api.util

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private val isoDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'")

class LocalDateTimeCustomDeserializer : JsonDeserializer<LocalDateTime> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): LocalDateTime = (json.fromEpochInstant().takeIf { it.isSuccess }
        ?: json.fromCustomIsoDateTime().takeIf { it.isSuccess }
        ?: json.fromIsoDateTime().takeIf { it.isSuccess })
        ?.getOrNull()
        ?: throw DateTimeParseException("Unregistered date format", json.asString, 0)

    private fun JsonElement.fromEpochInstant() = runCatching {
        LocalDateTime.ofInstant(Instant.ofEpochMilli(this.asLong), ZoneId.systemDefault())
    }

    private fun JsonElement.fromIsoDateTime() = runCatching {
        LocalDateTime.parse(this.asString, DateTimeFormatter.ISO_INSTANT)
    }

    private fun JsonElement.fromCustomIsoDateTime() = runCatching {
        LocalDateTime.parse(this.asString, isoDateFormatter)
    }
}
