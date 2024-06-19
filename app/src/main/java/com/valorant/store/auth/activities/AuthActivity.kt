package com.valorant.store.auth.activities
//
//import android.net.Uri
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import com.valorant.store.R
//import com.valorant.store.auth.screens.AuthScreen
//
//class AuthActivity : ComponentActivity() {
//    companion object {
//        const val JAVASCRIPT_CSS_INJECTOR = """
//                const styleElement = document.createElement("style");
//                styleElement.innerHTML = "#root > div > :not(:first-child) { z-index: 1; }"
//                const headElement = document.head.appendChild(styleElement)
//            """
//    }
//
//    private var https = "https"
//    private lateinit var domain: String
//    private lateinit var clientId: String
//    private lateinit var redirectUri: String
//    private lateinit var tokenIdentifier: String
//
//    override fun onCreate(savedInstanceState: android.os.Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        domain = getString(R.string.auth_riot_domain)
//        clientId = getString(R.string.auth_riot_client_id)
//        redirectUri = getString(R.string.auth_riot_redirect_uri)
//        tokenIdentifier = getString(R.string.auth_token_identifier)
//
//        setContent {
//            AuthScreen(
//                onRedirectInterceptor = { url ->
//                    if (url.startsWith(redirectUri)) {
//                        Log.w("TOKEN: ", "--------- onRedirectInterceptor: ${
//                            Uri.parse(url).fragment?.split("&")
//                                ?.map { it.split("=") }
//                                ?.associate { it[0] to it[1] }
//                                ?.get(tokenIdentifier)
//                                ?: "empty"
//                        }")
//                        Log.i("TOKENS: ", "---------onRedirectInterceptor: $url")
//                        true
//                    } else {
//                        false
//                    }
//                }
//            )
//        }
//    }
//}
