package com.valorant.store.auth

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
