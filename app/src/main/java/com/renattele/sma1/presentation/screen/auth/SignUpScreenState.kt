package com.renattele.sma1.presentation.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.renattele.sma1.R
import com.renattele.sma1.domain.user.AddUserRequest
import com.renattele.sma1.domain.user.AddUserResponse
import com.renattele.sma1.domain.user.AuthenticateRequest
import com.renattele.sma1.domain.user.UserRepository
import com.renattele.sma1.presentation.util.asText
import com.renattele.sma1.presentation.util.rememberErrorState
import kotlinx.coroutines.launch


data class SignUpScreenState(
    val username: String,
    val password: String,
    val passwordRepeat: String,
    val passwordRepeatError: String?,
    val error: String?,
    val eventSink: (SignUpScreenEvent) -> Unit = {},
)

sealed interface SignUpScreenEvent {
    data class EditUsername(val username: String) : SignUpScreenEvent
    data class EditPassword(val password: String) : SignUpScreenEvent
    data class EditPasswordRepeat(val password: String) : SignUpScreenEvent
    data object SignUp : SignUpScreenEvent
    data object GoToSignIn : SignUpScreenEvent
}

@Composable
fun rememberSignUpScreenState(
    userRepository: UserRepository,
    onGoToSignIn: () -> Unit,
    onGoToMainScreen: () -> Unit,
): SignUpScreenState {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    val passwordRepeatError =
        if (password != passwordRepeat && password.isNotEmpty() && passwordRepeat.isNotEmpty())
            stringResource(R.string.passwords_don_t_match)
        else null
    val error = rememberErrorState<Int>()

    val coroutineScope = rememberCoroutineScope()
    return SignUpScreenState(
        username = username,
        password = password,
        passwordRepeat = passwordRepeat,
        passwordRepeatError = passwordRepeatError,
        error = error.asText()
    ) { event ->
        when (event) {
            is SignUpScreenEvent.EditPassword -> password = event.password
            is SignUpScreenEvent.EditPasswordRepeat -> passwordRepeat = event.password
            is SignUpScreenEvent.EditUsername -> username = event.username
            SignUpScreenEvent.GoToSignIn -> onGoToSignIn()
            SignUpScreenEvent.SignUp -> {
                coroutineScope.launch {
                    if (passwordRepeatError != null) return@launch
                    val result = userRepository.registerUser(
                        AddUserRequest(
                            username = username,
                            password = password
                        )
                    )
                    when (result) {
                        AddUserResponse.AlreadyRegistered -> error.value =
                            R.string.already_registered

                        AddUserResponse.FailedToRegister -> error.value =
                            R.string.failed_to_register

                        is AddUserResponse.Success -> {
                            userRepository.authenticate(AuthenticateRequest(
                                username,
                                password
                            ))
                            onGoToMainScreen()
                        }
                    }
                }
            }
        }
    }
}