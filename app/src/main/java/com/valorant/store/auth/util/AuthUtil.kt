package com.valorant.store.auth.util

import android.net.Uri
import android.util.Log
import androidx.navigation.NavController
import com.valorant.store.navigation.NavRoutes
import kotlin.random.Random

internal class AuthUtil(
    private val domain: String,
    private val redirectUri: String,
    private val clientId: String,
    private val tokenIdentifier: String
) {
    internal fun buildUrl() = Uri.Builder()
        .scheme("https")
        .authority(domain)
        .appendPath("authorize")
        .appendQueryParameter("redirect_uri", redirectUri)
        .appendQueryParameter("client_id", clientId)
        .appendQueryParameter("response_type", "token id_token")
        .appendQueryParameter("scope", "account openid")
        .appendQueryParameter("nonce", Random.nextInt(Int.MAX_VALUE).toString())
        .build().toString()

    internal fun onRedirectWeViewInterceptor(navController: NavController): (String) -> Boolean = { url ->
        if (url.startsWith(redirectUri)) {
            Log.w("TOKEN: ", "--------- onRedirectInterceptor: ${
                Uri.parse(url).fragment?.split("&")
                    ?.map { it.split("=") }
                    ?.associate { it[0] to it[1] }
                    ?.get(tokenIdentifier)
                    ?: "empty"
            }")
            Log.i("TOKENS: ", "---------onRedirectInterceptor: $url")
            navController.navigate(NavRoutes.Home.route)
            true
        } else {
            false
        }
    }
}
