package com.valorant.store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.valorant.store.navigation.AppNavigation
import com.valorant.store.navigation.NavRoutes
import com.valorant.store.ui.theme.ValorantStoreTheme

//class MainActivity : ComponentActivity() {
//    private val authViewModel: AuthViewModel by viewModels {
//        AuthViewModelFactory(application)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val authResultLauncher = registerForActivityResult(
//            ActivityResultContracts.StartActivityForResult()
//        ) { result ->
//            if (result.resultCode == RESULT_OK) {
//                authViewModel.handleAuthResult(result.data)
//            } else {
//                authViewModel.setErrorState()
//            }
//        }
//
//        authViewModel.setAuthResultLauncher(authResultLauncher)
//
//        setContent {
//            MyApp(authViewModel)
//        }
//    }
//}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ValorantStoreTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

//@Composable
//fun AppScreen() {
//    ValorantStoreTheme {
//        Surface(color = MaterialTheme.colorScheme.background) {
//            Button()
//        }
//    }
//}

@Composable
fun FirstScreen(navController: NavController) {
    LocalContext.current
    return Button(onClick = {
        navController.navigate(NavRoutes.Auth.route)
    }) {
        Text(text = "Login")
    }
}

//@Composable
//fun MyApp(authViewModel: AuthViewModel) {
//    val authState by authViewModel.authState.collectAsState()
//
//    MaterialTheme {
//        Surface {
//            when (authState) {
//                is AuthState.Authenticated -> AuthenticatedScreen()
//                is AuthState.UnAuthenticated -> UnauthenticatedScreen(onClick = { authViewModel.startAuth() })
//                is AuthState.Error -> ErrorScreen()
//            }
//        }
//    }
//}

@Composable
fun AuthenticatedScreen() = Text(text = "Logged in")

@Composable
fun UnauthenticatedScreen(onClick: () -> Unit) = Button(onClick = onClick, content = { Text(text = "log in") })

@Composable
fun ErrorScreen() = Text(text = "Error")

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, color: Color = Color.White) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = color
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ValorantStoreTheme {
        Greeting("bucko")
    }
}