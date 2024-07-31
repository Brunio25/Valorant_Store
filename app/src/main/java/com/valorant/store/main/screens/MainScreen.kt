package com.valorant.store.main.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.valorant.store.ErrorScreen
import com.valorant.store.api.val_api.skins.entity.SkinBatchEntity
import com.valorant.store.global.UiState
import com.valorant.store.global.ViewModelProvider
import com.valorant.store.main.viewmodel.MainScreenViewModel

@Composable
fun MainScreen() {
    val viewModel: MainScreenViewModel = ViewModelProvider.mainScreenViewModel
    val skinBatch by viewModel.skinBatchLevels.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val storefront = skinBatch) {
            is UiState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is UiState.Success -> MainScreenContent(skinBatch = storefront.data)
            is UiState.Error -> ErrorScreen()
        }
    }
}

@Composable
fun MainScreenContent(skinBatch: SkinBatchEntity) {
    val imageUrls = skinBatch.skins.map { skin -> skin.levels.first().displayIcon }

    Log.i("TOKEN_HOME", imageUrls.toString())
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(imageUrls) {
            AsyncImage(
                model = it,
                contentDescription = "Descriptor",
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .aspectRatio(1f)
            )
        }
    }
}
