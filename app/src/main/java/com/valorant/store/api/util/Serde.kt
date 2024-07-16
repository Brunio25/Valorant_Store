package com.valorant.store.api.util

import android.util.Log
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

// 0001-01-01T00:00:00Z
// 2011-12-03T10:15:30Z
class LocalDateTimeCustomDeserializer : JsonDeserializer<LocalDateTime> {
    private val parsers = listOf(
        fromEpochInstant(),
        fromIsoDateTime(),
        fromCustomIsoDateTime()
    )

    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): LocalDateTime = parsers.map { it(json) }
        .find { it != null }
        ?: let {
            val e = DateTimeParseException("Unregistered date format", json.asString, 0)
            Log.e(
                "DATE_TIME_SERDE",
                "LocalDateTimeCustomDeserializer deserialize error on ${json.asString}",
                e
            )
            throw e
        }

    private fun fromEpochInstant(): (JsonElement) -> LocalDateTime? = {
        runCatching {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it.asLong), ZoneId.systemDefault())
        }.getOrNull()
    }

    private fun fromIsoDateTime(): (JsonElement) -> LocalDateTime? = {
        runCatching {
            LocalDateTime.parse(it.asString, DateTimeFormatter.ISO_INSTANT)
        }.getOrNull()
    }

    private fun fromCustomIsoDateTime(): (JsonElement) -> LocalDateTime? = {
        runCatching {
            LocalDateTime.parse(it.asString, isoDateFormatter)
        }.getOrNull()
    }
}
