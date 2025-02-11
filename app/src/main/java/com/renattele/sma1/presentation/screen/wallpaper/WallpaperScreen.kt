package com.renattele.sma1.presentation.screen.wallpaper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.renattele.sma1.R
import com.renattele.sma1.domain.wallpaper.WallpaperEntity
import com.renattele.sma1.presentation.components.ErrorSnackbarHost
import com.renattele.sma1.presentation.screen.wallpaper.components.WallpaperCard
import com.renattele.sma1.presentation.theme.AppTheme
import com.renattele.sma1.presentation.util.plus
import com.renattele.sma1.utils.times
import java.util.UUID

@Composable
fun WallpaperScreen(state: WallpaperScreenState, modifier: Modifier = Modifier) {
    Scaffold(modifier, snackbarHost = {
        ErrorSnackbarHost(state.error)
    }, floatingActionButton = {
        LargeFloatingActionButton(onClick = {
            state.eventSink(WallpaperScreenEvent.GoToAddWallpaper)
        }) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    }) { padding ->
        WallpapersGrid(state, padding)
    }
}

@Composable
private fun WallpapersGrid(
    state: WallpaperScreenState,
    padding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        GridCells.Adaptive(140.dp),
        modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = padding + PaddingValues(20.dp)
    ) {
        item(span = { GridItemSpan(2) }) {
            Column {
                Text(
                    stringResource(R.string.my_wallpapers),
                    style = MaterialTheme.typography.displayLarge
                )
                AssistChip(onClick = {
                    state.eventSink(WallpaperScreenEvent.Logout)
                }, label = {
                    Text(stringResource(R.string.logout))
                }, trailingIcon = {
                    Icon(
                        Icons.AutoMirrored.Filled.Logout,
                        contentDescription = null
                    )
                })
            }
        }
        items(state.wallpapers ?: listOf()) { wallpaper ->
            WallpaperCard(wallpaper, Modifier.animateItem(), onLongClick = {
                state.eventSink(WallpaperScreenEvent.DeleteWallpaper(wallpaper.id))
            })
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private fun LoadingState(modifier: Modifier = Modifier) {
    Box(
        modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularWavyProgressIndicator(
            Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )
    }
}

@Preview
@Composable
private fun WallpaperScreenPreview() {
    AppTheme {
        WallpaperScreen(
            WallpaperScreenState(
                listOf(
                    WallpaperEntity(
                        id = UUID.randomUUID(),
                        author = UUID.randomUUID(),
                        name = "Wallpaper",
                        description = "Description",
                        pictureUri = ""
                    )
                ) * 10,
                error = null
            )
        )
    }
}