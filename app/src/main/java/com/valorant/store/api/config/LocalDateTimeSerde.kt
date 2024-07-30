package com.valorant.store.api.config

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class LocalDateTimeCustomDeserializer : JsonDeserializer<LocalDateTime> {
    private val isoDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'")

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
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it.asLong), ZoneOffset.UTC)
        }.getOrNull()
    }

    private fun fromIsoDateTime(): (JsonElement) -> LocalDateTime? = {
        runCatching {
            LocalDateTime.ofInstant(Instant.parse(it.asString), ZoneOffset.UTC)
        }.getOrNull()
    }

    private fun fromCustomIsoDateTime(): (JsonElement) -> LocalDateTime? = {
        runCatching {
            LocalDateTime.parse(it.asString, isoDateFormatter)
        }.getOrNull()
    }
}
