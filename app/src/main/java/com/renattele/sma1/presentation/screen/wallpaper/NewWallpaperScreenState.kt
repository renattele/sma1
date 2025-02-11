package com.renattele.sma1.presentation.screen.wallpaper

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.renattele.sma1.R
import com.renattele.sma1.domain.wallpaper.AddWallpaperRequest
import com.renattele.sma1.domain.wallpaper.AddWallpaperResponse
import com.renattele.sma1.domain.wallpaper.WallpaperRepository
import com.renattele.sma1.presentation.util.asText
import com.renattele.sma1.presentation.util.rememberErrorState
import kotlinx.coroutines.launch

data class NewWallpaperScreenState(
    val name: String,
    val description: String,
    val pictureUri: String?,
    val error: String?,
    val eventSink: (NewWallpaperScreenEvent) -> Unit = {},
)

sealed interface NewWallpaperScreenEvent {
    data class EditName(val name: String) : NewWallpaperScreenEvent
    data class EditDescription(val description: String) : NewWallpaperScreenEvent
    data object EditPreview : NewWallpaperScreenEvent
    data object Create : NewWallpaperScreenEvent
}

@Composable
fun rememberNewWallpaperScreenState(
    wallpaperRepository: WallpaperRepository,
    onGoToBack: () -> Unit,
): NewWallpaperScreenState {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var pictureUri by remember { mutableStateOf<String?>(null) }
    val error = rememberErrorState<Int>()

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val pickMediaLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                pictureUri = uri.toString()
            }
        }
    return NewWallpaperScreenState(
        name = name,
        description = description,
        pictureUri = pictureUri,
        error = error.asText()
    ) { event ->
        when (event) {
            NewWallpaperScreenEvent.Create -> {
                coroutineScope.launch {
                    if (name.isEmpty()) {
                        error.value = R.string.empty_name
                        return@launch
                    }
                    if (pictureUri.isNullOrEmpty()) {
                        error.value = R.string.empty_picture
                        return@launch
                    }
                    val response = wallpaperRepository.addWallpaper(
                        AddWallpaperRequest(
                            name = name,
                            description = description,
                            pictureUri = pictureUri!!
                        )
                    )
                    when (response) {
                        AddWallpaperResponse.Failure -> {
                            error.value =
                                R.string.failed_to_create_wallpaper
                        }

                        AddWallpaperResponse.Success -> onGoToBack()
                    }
                }
            }

            is NewWallpaperScreenEvent.EditDescription -> description = event.description
            is NewWallpaperScreenEvent.EditName -> name = event.name
            NewWallpaperScreenEvent.EditPreview -> {
                pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }
}