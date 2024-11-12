package com.valorant.store.data.extensions

import android.util.Base64
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

fun <T> ObjectMapper.writeValueAsBase64(data: T): String = writeValueAsBytes(data)
    .let { Base64.encode(it, Base64.NO_WRAP) }
    .toString(Charsets.UTF_8)

inline fun <reified T> ObjectMapper.readBase64Value(base64: String): T =
    readValue(Base64.decode(base64, Base64.NO_WRAP), object : TypeReference<T>() {})
