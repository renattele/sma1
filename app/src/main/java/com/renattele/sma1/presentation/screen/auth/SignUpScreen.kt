package com.renattele.sma1.presentation.screen.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.renattele.sma1.R
import com.renattele.sma1.presentation.components.ErrorSnackbarHost
import com.renattele.sma1.presentation.theme.AppTheme

@Composable
fun SignUpScreen(state: SignUpScreenState, modifier: Modifier = Modifier) {
    Scaffold(modifier.fillMaxSize(), snackbarHost = {
        ErrorSnackbarHost(state.error)
    }) { padding ->
        Column(
            modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Text(stringResource(R.string.sign_up), style = MaterialTheme.typography.headlineLarge)
            OutlinedTextField(state.username, onValueChange = {
                state.eventSink(SignUpScreenEvent.EditUsername(it))
            }, label = {
                Text(stringResource(R.string.username))
            })
            OutlinedTextField(state.password, onValueChange = {
                state.eventSink(SignUpScreenEvent.EditPassword(it))
            }, visualTransformation = PasswordVisualTransformation(), label = {
                Text(stringResource(R.string.password))
            })
            OutlinedTextField(state.passwordRepeat,
                onValueChange = {
                    state.eventSink(SignUpScreenEvent.EditPasswordRepeat(it))
                },
                visualTransformation = PasswordVisualTransformation(),
                label = {
                    Text(stringResource(R.string.repeat_password))
                },
                isError = state.passwordRepeatError != null,
                supportingText = if (state.passwordRepeatError != null) {
                    {
                        Text(state.passwordRepeatError)
                    }
                } else null)
            Button({
                state.eventSink(SignUpScreenEvent.SignUp)
            }) {
                Text(stringResource(R.string.sign_up))
            }
            Text(
                stringResource(R.string.or_sign_in_instead),
                Modifier.clickable {
                    state.eventSink(SignUpScreenEvent.GoToSignIn)
                },
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    AppTheme {
        SignUpScreen(
            SignUpScreenState(
                username = "aaaa",
                password = "bbbb",
                passwordRepeat = "bbbb",
                passwordRepeatError = "AAA",
                error = "BBBB"
            )
        )
    }
}