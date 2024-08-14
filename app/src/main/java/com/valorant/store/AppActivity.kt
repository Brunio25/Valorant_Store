package com.valorant.store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.valorant.store.global.ViewModelProvider
import com.valorant.store.navigation.AppNavigation
import com.valorant.store.ui.theme.ValorantStoreTheme

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewModelProvider.initialize(application)

        setContent {
            ValorantStoreTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavigation()
                }
            }
        }
    }
}

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