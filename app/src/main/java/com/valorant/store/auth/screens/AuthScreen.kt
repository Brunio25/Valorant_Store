package com.valorant.store.auth.screens

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
import com.valorant.store.auth.util.AuthUtil
import com.valorant.store.auth.view_models.AuthViewModel

@Composable
fun AuthScreen(navController: NavController) {
    val viewModel: AuthViewModel = viewModel()

    val riotDomain = stringResource(id = R.string.auth_riot_domain)
    val redirectUri = stringResource(id = R.string.auth_riot_redirect_uri)
    val clientId = stringResource(id = R.string.auth_riot_client_id)
    val tokenIdentifier = stringResource(id = R.string.auth_token_identifier)
    val authUtil = AuthUtil(riotDomain, redirectUri, clientId, tokenIdentifier)

    Box(modifier = Modifier.fillMaxSize()) {
        AuthWebView(
            url = authUtil.buildUrl(),
            onRedirectInterceptor = authUtil.onRedirectWeViewInterceptor(navController),
            viewModel = viewModel
        )
        CircularProgressIndicator(modifier = Modifier.alpha(if (viewModel.isPageLoading.value) 1f else 0f))
    }
}

const val JAVASCRIPT_CSS_INJECTOR = """
                const styleElement = document.createElement("style");
                styleElement.innerHTML = "#root > div > :not(:first-child) { z-index: 1; }";
                document.head.appendChild(styleElement);
            """


@Composable
private fun AuthWebView(
    url: String,
    onRedirectInterceptor: (String) -> Boolean,
    viewModel: AuthViewModel
) {
    val cssInjector = stringResource(id = R.string.auth_javascript_css_injector)
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

                // TODO: Remove?
                WebView.setWebContentsDebuggingEnabled(true)

                webViewClient = AuthWebViewClient(
                    onRedirectInterceptor = onRedirectInterceptor,
                    viewModel = viewModel,
                    cssInjector = JAVASCRIPT_CSS_INJECTOR //TODO workaround for now
                )

                loadUrl(url)
            }
        })
}
