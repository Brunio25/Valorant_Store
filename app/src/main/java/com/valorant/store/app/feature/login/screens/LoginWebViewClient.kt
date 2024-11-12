package com.valorant.store.app.feature.login.screens

import android.graphics.Bitmap
import android.net.http.SslError
import android.util.Log
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.valorant.store.app.feature.login.viewmodel.LoginScreenViewModel

const val JAVASCRIPT_CSS_INJECTOR = """
                const styleElement = document.createElement("style");
                styleElement.innerHTML = "#root > div > :not(:first-child) { z-index: 1; }";
                document.head.appendChild(styleElement);
            """

internal class LoginWebViewClient(
    private val onRedirectViewInterceptor: (String) -> Boolean,
    private val viewModel: LoginScreenViewModel
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest?): Boolean =
        onRedirectViewInterceptor(request?.url.toString())

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        Log.d("WebView", "Page started loading: $url")
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        Log.d("WebView", "Page finished loading: $url")

        view.evaluateJavascript(JAVASCRIPT_CSS_INJECTOR, null)
        viewModel.pageLoaded()
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
