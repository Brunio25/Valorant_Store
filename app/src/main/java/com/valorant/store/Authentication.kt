package com.valorant.store

import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Message
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import kotlin.random.Random

sealed class AuthState {
    data object UnAuthenticated : AuthState()
    data object Authenticated : AuthState()
    data object Error : AuthState()
}

class AuthWebViewActivity : ComponentActivity() {
    companion object {
        const val HTTPS = "https"
        const val DOMAIN = "auth.riotgames.com"
        const val CLIENT_ID = "play-valorant-web-prod"
        const val REDIRECT_URI = "https://playvalorant.com/opt_in"
    }

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        val url = buildUrl()
        Log.w("URL: ", "--------- generated url: $url")

        setContent {
            AuthWebViewScreen(
                url = buildUrl(),
                onRedirectInterceptor = { url ->
                    if (url.startsWith(REDIRECT_URI)) {
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
        .scheme(HTTPS)
        .authority(DOMAIN)
        .appendPath("authorize")
        .appendQueryParameter("redirect_uri", REDIRECT_URI)
        .appendQueryParameter("client_id", CLIENT_ID)
        .appendQueryParameter("response_type", "token id_token")
        .appendQueryParameter("scope", "account openid")
        .appendQueryParameter("nonce", /*Random.nextInt(Int.MAX_VALUE).toString()*/"1")
        .build().toString()
//    private fun buildUrl() = "https://accounts.hcaptcha.com/demo"
}

@Composable
fun AuthWebViewScreen(url: String, onRedirectInterceptor: (String) -> Boolean) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.useWideViewPort = true // TODO: Remove?
            settings.loadWithOverviewMode = true // TODO: Remove?

            settings.setSupportMultipleWindows(true) // TODO: Remove?
            settings.allowFileAccess = false
            settings.allowContentAccess = false

            // TODO: Remove?
            settings.userAgentString =
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"

            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

            // TODO: Remove?
            WebView.setWebContentsDebuggingEnabled(true)

            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return false
                }

                override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    Log.d("WebView", "Page started loading: $url")
                    view.evaluateJavascript(
                        """
                            (function() {
                                var elements = document.getElementsByTagName('button');
                                for (var i = 0; i < elements.length; i++) {
                                    if (elements[i].innerText.includes('Accept')) {
                                        elements[i].click();
                                        break;
                                    }
                                }
                            })();
                        """,
                        null
                    )
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    Log.d("WebView", "Page finished loading: $url")
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Log.e("WebView", "Error loading page: ${error?.description}")
                }

                override fun onReceivedHttpError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    errorResponse: WebResourceResponse?
                ) {
                    super.onReceivedHttpError(view, request, errorResponse)
                    Log.e("WebView", "HTTP error loading page: ${errorResponse?.statusCode}")
                }

                override fun onReceivedSslError(
                    view: WebView?,
                    handler: SslErrorHandler?,
                    error: SslError?
                ) {
                    handler?.proceed() // Ignore SSL errors
                    Log.e("WebView", "SSL error loading page: ${error?.primaryError}")
                }
            }

            webChromeClient = object : WebChromeClient() {
                override fun onCreateWindow(
                    view: WebView,
                    isDialog: Boolean,
                    isUserGesture: Boolean,
                    resultMsg: Message
                ): Boolean {
                    val newWebView = WebView(view.context)
                    newWebView.settings.javaScriptEnabled = true
                    newWebView.settings.domStorageEnabled = true
                    newWebView.webViewClient = WebViewClient()
                    (view.parent as ViewGroup).addView(newWebView)

                    val transport = resultMsg.obj as WebView.WebViewTransport
                    transport.webView = newWebView
                    resultMsg.sendToTarget()

                    return true
                }
            }

            loadUrl(url)
        }
    })
}

//class AuthViewModel(application: Application) : AndroidViewModel(application) {
//    private val _authState = MutableStateFlow<AuthState>(AuthState.UnAuthenticated)
//    val authState: StateFlow<AuthState> = _authState
//
//    private lateinit var authService: AuthorizationService
//    private lateinit var authRequest: AuthorizationRequest
//    private lateinit var serviceConfig: AuthorizationServiceConfiguration
//    private lateinit var authResultLauncher: ActivityResultLauncher<Intent>
//
//    init {
//        val context: Context = getApplication<Application>().applicationContext
//        serviceConfig = AuthorizationServiceConfiguration(
//            Uri.parse("https://auth.riotgames.com/authorize"),
//            Uri.parse("https://auth.riotgames.com/token")
//        )
//
//        authRequest = AuthorizationRequest.Builder(
//            serviceConfig,
//            "play-valorant-web-prod",
//            ResponseTypeValues.CODE,
//            Uri.parse("https://playvalorant.com/opt_in")
//        ).build()
//
//        authService = AuthorizationService(context)
//    }
//
//    fun setAuthResultLauncher(launcher: ActivityResultLauncher<Intent>) {
//        authResultLauncher = launcher
//    }
//
//    fun startAuth() {
//        val authIntent = authService.getAuthorizationRequestIntent(authRequest)
//        authResultLauncher.launch(authIntent)
//    }
//
//    fun setErrorState() {
//        viewModelScope.launch {
//            _authState.emit(AuthState.Error)
//        }
//    }
//
//    fun handleAuthResult(data: Intent?) {
//        val resp = AuthorizationResponse.fromIntent(data!!)
//        val ex = AuthorizationException.fromIntent(data)
//
//        if (resp != null) {
//            exchangeAuthCode(resp.authorizationCode!!)
//        } else {
//            setErrorState()
//        }
//    }
//
//    private fun exchangeAuthCode(authCode: String) {
//        val tokenRequest = TokenRequest.Builder(
//            serviceConfig,
//            "play-valorant-web-prod"
//        ).setGrantType(GrantTypeValues.AUTHORIZATION_CODE)
//            .setRedirectUri(Uri.parse("https://auth.riotgames.com/authorize"))
//            .setAuthorizationCode(authCode)
//            .build()
//
//        viewModelScope.launch {
//            authService.performTokenRequest(tokenRequest) { response, ex ->
//                viewModelScope.launch {
//                    if (response != null) {
//                        // Handle the token response
//                        _authState.emit(AuthState.Authenticated)
//                    } else {
//                        _authState.emit(AuthState.Error)
//                    }
//                }
//            }
//        }
//    }
//}
//
//class AuthViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return AuthViewModel(application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
