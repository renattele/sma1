package com.renattele.sma1.presentation.screen.wallpaper

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.renattele.sma1.R
import com.renattele.sma1.presentation.components.ErrorSnackbarHost
import com.renattele.sma1.presentation.theme.AppTheme

@Composable
fun NewWallpaperScreen(state: NewWallpaperScreenState, modifier: Modifier = Modifier) {
    Scaffold(modifier, snackbarHost = {
        ErrorSnackbarHost(state.error)
    }, floatingActionButton = {
        LargeFloatingActionButton({
            state.eventSink(NewWallpaperScreenEvent.Create)
        }) {
            Icon(Icons.Default.Done, contentDescription = null)
        }
    }) { padding ->
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                stringResource(R.string.add_wallpaper),
                Modifier.padding(bottom = 20.dp),
                style = MaterialTheme.typography.headlineLarge
            )
            Box {
                AsyncImage(
                    state.pictureUri ?: R.drawable.ic_launcher_background,
                    contentDescription = null,
                    Modifier
                        .size(200.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Icon(
                    Icons.Default.Edit,
                    contentDescription = null,
                    Modifier.align(Alignment.BottomEnd)
                        .clip(CircleShape)
                        .clickable {
                            state.eventSink(NewWallpaperScreenEvent.EditPreview)
                        }
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(16.dp)
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            OutlinedTextField(state.name, onValueChange = {
                state.eventSink(NewWallpaperScreenEvent.EditName(it))
            }, label = { Text(stringResource(R.string.name)) })
            OutlinedTextField(state.description, onValueChange = {
                state.eventSink(NewWallpaperScreenEvent.EditDescription(it))
            }, label = { Text(stringResource(R.string.description)) })
        }
    }
}

@Preview
@Composable
private fun NewWallpaperScreenPreview() {
    AppTheme {
        NewWallpaperScreen(
            NewWallpaperScreenState(
                "Name",
                "Description",
                null,
                error = null
            )
        )
    }
}