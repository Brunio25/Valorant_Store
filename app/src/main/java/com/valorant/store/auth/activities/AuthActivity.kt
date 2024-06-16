package com.valorant.store.auth.activities

import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.valorant.store.R
import com.valorant.store.auth.screen.AuthScreen

class AuthViewModel : ViewModel() {
    private var _isPageLoading = mutableStateOf(true)
    val isPageLoading: State<Boolean> = _isPageLoading

    fun pageFinished() {
        _isPageLoading.value = false
    }
}

class AuthActivity : ComponentActivity() {
    private var https = "https"
    private lateinit var domain: String
    private lateinit var clientId: String
    private lateinit var redirectUri: String
    private lateinit var tokenIdentifier: String

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        domain = getString(R.string.auth_riot_domain)
        clientId = getString(R.string.auth_riot_client_id)
        redirectUri = getString(R.string.auth_riot_redirect_uri)
        tokenIdentifier = getString(R.string.auth_token_identifier)

        val url = buildUrl()
        Log.w("URL: ", "--------- generated url: $url")

        setContent {
            AuthScreen(
                url = buildUrl(),
                onRedirectInterceptor = { url ->
                    if (url.startsWith(redirectUri)) {
                        Uri.parse(url).getQueryParameter(tokenIdentifier)
                        Log.i("TOKENS: ", "---------onRedirectInterceptor: $url")
                        true
                    } else {
                        false
                    }
                }
            )
        }
    }

    private fun buildUrl() = Uri.Builder()
        .scheme(https)
        .authority(domain)
        .appendPath("authorize")
        .appendQueryParameter("redirect_uri", redirectUri)
        .appendQueryParameter("client_id", clientId)
        .appendQueryParameter("response_type", "token id_token")
        .appendQueryParameter("scope", "account openid")
        .appendQueryParameter("nonce", /*Random.nextInt(Int.MAX_VALUE).toString()*/"1")
        .build().toString()
}
