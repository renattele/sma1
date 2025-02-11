package com.renattele.sma1.presentation.screen.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.renattele.sma1.domain.user.UserRepository
import kotlinx.coroutines.delay

data class SplashScreenState(
    val username: String?,
    val error: String?,
)


@Composable
fun rememberSplashScreenState(
    userRepository: UserRepository,
    onGoToSignIn: () -> Unit,
    onGoToMain: () -> Unit,
): SplashScreenState {
    var username by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        val user = userRepository.getCurrentAuthenticatedUser()
        if (user == null) {
            onGoToSignIn()
        } else {
            delay(300)
            username = user.username
            delay(1000)
            onGoToMain()
        }
    }
    return SplashScreenState(username = username, error = null)
}