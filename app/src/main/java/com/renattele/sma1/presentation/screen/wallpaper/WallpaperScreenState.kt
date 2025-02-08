package com.renattele.sma1.presentation.screen.wallpaper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.renattele.sma1.R
import com.renattele.sma1.domain.user.UserRepository
import com.renattele.sma1.domain.wallpaper.DeleteWallpaperResponse
import com.renattele.sma1.domain.wallpaper.WallpaperEntity
import com.renattele.sma1.domain.wallpaper.WallpaperRepository
import com.renattele.sma1.presentation.util.asText
import com.renattele.sma1.presentation.util.rememberErrorState
import kotlinx.coroutines.launch
import java.util.UUID

data class WallpaperScreenState(
    val wallpapers: List<WallpaperEntity>,
    val error: String?,
    val eventSink: (WallpaperScreenEvent) -> Unit = {},
)

sealed interface WallpaperScreenEvent {
    data object GoToAddWallpaper: WallpaperScreenEvent
    data object Logout: WallpaperScreenEvent
    data class DeleteWallpaper(val wallpaperId: UUID): WallpaperScreenEvent
}

@Composable
fun rememberWallpaperScreenState(
    userRepository: UserRepository,
    wallpaperRepository: WallpaperRepository,
    onGoToAddWallpaper: () -> Unit,
    onGoToLogin: () -> Unit
): WallpaperScreenState {
    val wallpapers = remember { mutableStateListOf<WallpaperEntity>() }
    LaunchedEffect(Unit) {
        wallpapers.addAll(wallpaperRepository.getWallpapers())
    }
    val coroutineScope = rememberCoroutineScope()
    val error = rememberErrorState<Int>()
    return WallpaperScreenState(
        wallpapers,
        error = error.asText()
    ) { event ->
        when (event) {
            WallpaperScreenEvent.GoToAddWallpaper -> onGoToAddWallpaper()
            WallpaperScreenEvent.Logout -> {
                coroutineScope.launch {
                    userRepository.logout()
                    onGoToLogin()
                }
            }

            is WallpaperScreenEvent.DeleteWallpaper -> {
                coroutineScope.launch {
                    val response = wallpaperRepository.deleteWallpaperById(event.wallpaperId)
                    when (response) {
                        DeleteWallpaperResponse.IdNotFound -> {
                            error.value = R.string.failed_to_delete
                        }
                        DeleteWallpaperResponse.Success -> {
                            wallpapers.removeIf { it.id == event.wallpaperId }
                        }
                    }
                }
            }
        }
    }
}