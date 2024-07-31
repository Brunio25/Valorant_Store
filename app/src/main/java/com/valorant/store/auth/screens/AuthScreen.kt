package com.valorant.store.auth.screens

import android.net.Uri
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.valorant.store.R
import com.valorant.store.auth.AuthState
import com.valorant.store.auth.util.buildUrl
import com.valorant.store.auth.viewmodel.PageLoadingViewModel
import com.valorant.store.global.ViewModelProvider
import com.valorant.store.navigation.NavRoutes

@Composable
fun AuthScreen(navController: NavController) {
    val viewModel: PageLoadingViewModel = viewModel()

    val riotDomain = stringResource(id = R.string.auth_riot_domain)
    val registeredRedirectUri = stringResource(id = R.string.auth_riot_redirect_uri)
    val clientId = stringResource(id = R.string.auth_riot_client_id)
    val tokenIdentifier = stringResource(id = R.string.auth_token_identifier)

    val onRedirectViewInterceptor = onRedirectViewInterceptorCreator(
        registeredRedirectUri = registeredRedirectUri,
        tokenIdentifier = tokenIdentifier,
        navController = navController,
        authState = ViewModelProvider.authState
    )

    Box(modifier = Modifier.fillMaxSize()) {
        AuthWebView(
            url = buildUrl(riotDomain, registeredRedirectUri, clientId),
            onRedirectViewInterceptor = onRedirectViewInterceptor,
            viewModel = viewModel
        )
        CircularProgressIndicator(modifier = Modifier.alpha(if (viewModel.isPageLoading.value) 1f else 0f))
    }
}

private fun onRedirectViewInterceptorCreator(
    registeredRedirectUri: String,
    tokenIdentifier: String,
    navController: NavController,
    authState: AuthState
): (String) -> Boolean = { url ->
    if (url.startsWith(registeredRedirectUri).not()) {
        false
    } else {
        val token = Uri.parse(url).fragment?.split("&")
            ?.map { it.split("=") }
            ?.associate { it[0] to it[1] }
            ?.get(tokenIdentifier)

        authState.setAuthToken(token)
        navController.navigate(NavRoutes.Home.route)
        true
    }
}

@Composable
private fun AuthWebView(
    url: String,
    onRedirectViewInterceptor: (String) -> Boolean,
    viewModel: PageLoadingViewModel
) {
    AndroidView(modifier = Modifier
        .fillMaxSize()
        .alpha(if (viewModel.isPageLoading.value) 0f else 1f),
        factory = { context ->
            WebView(context).apply {
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    loadWithOverviewMode = true // TODO: Remove?

                    allowFileAccess = false
                    allowContentAccess = false

                    cacheMode = WebSettings.LOAD_DEFAULT
                    mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

//                javaScriptCanOpenWindowsAutomatically = true// TODO: Remove?
//                layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL

//                  TODO: Remove?
                    userAgentString =
                        "Mozilla/5.0 (Linux; Android 13; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Mobile Safari/537.36"
                }

//            clearCache(true)
//            clearHistory()
//            clearFormData()

                WebView.setWebContentsDebuggingEnabled(true) // TODO: Remove?

                webViewClient = AuthWebViewClient(
                    onRedirectViewInterceptor = onRedirectViewInterceptor,
                    viewModel = viewModel
                )

                loadUrl(url)
            }
        })
}
