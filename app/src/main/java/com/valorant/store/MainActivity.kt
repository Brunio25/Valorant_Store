package com.valorant.store

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.valorant.store.navigation.BottomNavigationBar
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ValorantStoreTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Button()
                }
            }
        }
    }
}

@Composable
fun Button() {
    val context = LocalContext.current
    return Button(onClick = {
        val intent = Intent(context, AuthWebViewActivity::class.java)
        context.startActivity(intent)
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