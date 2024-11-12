package com.valorant.store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.valorant.store.app.launcher.screens.MainScreen
import com.valorant.store.app.ui.theme.ValorantStoreTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Box(modifier = Modifier.padding(WindowInsets.systemBars.asPaddingValues())) {
                Box(modifier = Modifier.consumeWindowInsets(WindowInsets.systemBars)) {
                    ValorantStoreTheme {
                        Surface(color = MaterialTheme.colorScheme.background) {
                            MainScreen()
                        }
                    }
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