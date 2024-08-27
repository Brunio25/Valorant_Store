package com.valorant.store.api.config

import android.util.Log
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class LocalDateTimeCustomDeserializer : JsonDeserializer<LocalDateTime>() {
    private val isoDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'")

    private val parsers = listOf(
        fromEpochInstant(),
        fromIsoDateTime(),
        fromCustomIsoDateTime()
    )

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDateTime =
        parsers.map { it(p) }
            .find { it != null }
            ?: let {
                val e = DateTimeParseException("Unregistered date format", p.text, 0)
                Log.e(
                    "DATE_TIME_SERDE",
                    "LocalDateTimeCustomDeserializer deserialize error on ${p.text}",
                    e
                )
                throw e
            }

    private fun fromEpochInstant(): (JsonParser) -> LocalDateTime? = {
        runCatching {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it.longValue), ZoneOffset.UTC)
        }.getOrNull()
    }

    private fun fromIsoDateTime(): (JsonParser) -> LocalDateTime? = {
        runCatching {
            LocalDateTime.ofInstant(Instant.parse(it.text), ZoneOffset.UTC)
        }.getOrNull()
    }

    private fun fromCustomIsoDateTime(): (JsonParser) -> LocalDateTime? = {
        runCatching {
            LocalDateTime.parse(it.text, isoDateFormatter)
        }.getOrNull()
    }
}
