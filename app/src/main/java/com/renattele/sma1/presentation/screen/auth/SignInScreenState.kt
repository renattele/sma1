package com.renattele.sma1.presentation.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.renattele.sma1.R
import com.renattele.sma1.domain.user.AuthenticateRequest
import com.renattele.sma1.domain.user.AuthenticateResponse
import com.renattele.sma1.domain.user.UserRepository
import com.renattele.sma1.presentation.util.asText
import com.renattele.sma1.presentation.util.rememberErrorState
import kotlinx.coroutines.launch


data class SignInScreenState(
    val username: String,
    val password: String,
    val error: String?,
    val eventSink: (SignInScreenEvent) -> Unit = {},
)

sealed interface SignInScreenEvent {
    data class EditUsername(val username: String) : SignInScreenEvent
    data class EditPassword(val password: String) : SignInScreenEvent
    data object SignIn : SignInScreenEvent
    data object GoToSignUp : SignInScreenEvent
}

@Composable
fun rememberSignInScreenState(
    userRepository: UserRepository,
    onGoToSignUp: () -> Unit,
    onGoToMain: () -> Unit
): SignInScreenState {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val error = rememberErrorState<Int>()
    val coroutineScope = rememberCoroutineScope()
    return SignInScreenState(
        username = username,
        password = password,
        error = error.asText()
    ) { event ->
        when (event) {
            is SignInScreenEvent.EditPassword -> password = event.password
            is SignInScreenEvent.EditUsername -> username = event.username
            SignInScreenEvent.GoToSignUp -> onGoToSignUp()
            SignInScreenEvent.SignIn ->
                coroutineScope.launch {
                    val response = userRepository.authenticate(
                        AuthenticateRequest(
                            username = username,
                            password = password
                        )
                    )
                    when (response) {
                        AuthenticateResponse.Authenticated -> onGoToMain()
                        AuthenticateResponse.WrongCredentials -> error.value = R.string.wrong_credentials
                    }
                }

        }
    }
}