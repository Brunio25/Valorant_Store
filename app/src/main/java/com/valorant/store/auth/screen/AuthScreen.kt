package com.valorant.store.auth.screen

import android.graphics.Bitmap
import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.valorant.store.auth.activities.AuthViewModel

@Composable
fun AuthScreen(url: String, onRedirectInterceptor: (String) -> Boolean) {
    val viewModel: AuthViewModel = viewModel()

    Box(modifier = Modifier.fillMaxSize()) {
        AuthWebView(
            url = url, onRedirectInterceptor = onRedirectInterceptor, viewModel = viewModel
        )
        CircularProgressIndicator(modifier = Modifier.alpha(if (viewModel.isPageLoading.value) 1f else 0f))
    }
}

@Composable
fun AuthWebView(url: String, onRedirectInterceptor: (String) -> Boolean, viewModel: AuthViewModel) {
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

                webViewClient = AuthWebViewClient(onRedirectInterceptor, viewModel)

                loadUrl(url)
            }
        })
}

class AuthWebViewClient(
    private val onRedirectInterceptor: (String) -> Boolean, private val viewModel: AuthViewModel
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView, request: WebResourceRequest?
    ): Boolean {
        return onRedirectInterceptor(request?.url.toString())
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.d("WebView", "Page started loading: $url")
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        Log.d("WebView", "Page finished loading: $url")

        view.evaluateJavascript(
            """
                const styleElement = document.createElement("style");
                styleElement.innerHTML = "#root > div > :not(:first-child) { z-index: 1; }"
                const headElement = document.head.appendChild(styleElement)
            """.trimIndent(), null
        )

        viewModel.pageFinished()
    }

    override fun onReceivedError(
        view: WebView?, request: WebResourceRequest?, error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        Log.e("WebView", "Error loading page: ${error?.description}")
    }

    override fun onReceivedHttpError(
        view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        Log.e("WebView", "HTTP error loading page: ${errorResponse?.statusCode}")
    }

    override fun onReceivedSslError(
        view: WebView?, handler: SslErrorHandler?, error: SslError?
    ) {
        super.onReceivedSslError(view, handler, error)
        Log.e("WebView", "SSL error loading page: ${error?.primaryError}")
    }
}
