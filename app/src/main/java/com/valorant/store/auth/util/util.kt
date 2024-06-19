package com.valorant.store.auth.util

import android.net.Uri
import kotlin.random.Random

internal fun buildUrl(domain: String, registeredRedirectUri: String, clientId: String) =
    Uri.Builder()
        .scheme("https")
        .authority(domain)
        .appendPath("authorize")
        .appendQueryParameter("redirect_uri", registeredRedirectUri)
        .appendQueryParameter("client_id", clientId)
        .appendQueryParameter("response_type", "token id_token")
        .appendQueryParameter("scope", "account openid")
        .appendQueryParameter("nonce", Random.nextInt(Int.MAX_VALUE).toString())
        .build().toString()
