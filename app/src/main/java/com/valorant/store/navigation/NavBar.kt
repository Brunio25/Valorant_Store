package com.valorant.store.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar() {
    BottomAppBar(
        actions = {
            IconButton(onClick = {  }) {
                Icon(Icons.Filled.Person, contentDescription = "Profile")
            }
            IconButton(onClick = {  }) {
                Icon(Icons.Filled.Person, contentDescription = "Profile")
            }
            IconButton(onClick = {  }) {
                Icon(Icons.Filled.Person, contentDescription = "Profile")
            }
            IconButton(onClick = {  }) {
                Icon(Icons.Filled.Person, contentDescription = "Profile")
            }
        }
    )
}
