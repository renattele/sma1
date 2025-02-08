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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.renattele.sma1.R
import com.renattele.sma1.presentation.theme.AppTheme

@Composable
fun SignInScreen(state: SignInScreenState, modifier: Modifier = Modifier) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.error) {
        if (state.error != null) {
            snackbarHostState.showSnackbar(state.error)
        }
    }
    Scaffold(modifier.fillMaxSize(), snackbarHost = {
        SnackbarHost(snackbarHostState)
    }) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            Text(stringResource(R.string.sign_in), style = MaterialTheme.typography.headlineLarge)
            OutlinedTextField(state.username, onValueChange = {
                state.eventSink(SignInScreenEvent.EditUsername(it))
            }, label = {
                Text(stringResource(R.string.username))
            })
            OutlinedTextField(state.password, onValueChange = {
                state.eventSink(SignInScreenEvent.EditPassword(it))
            }, visualTransformation = PasswordVisualTransformation(), label = {
                Text(stringResource(R.string.password))
            })
            Button({
                state.eventSink(SignInScreenEvent.SignIn)
            }) {
                Text(stringResource(R.string.sign_in))
            }
            Text(
                stringResource(R.string.or_sign_up_instead),
                Modifier.clickable {
                    state.eventSink(SignInScreenEvent.GoToSignUp)
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
private fun SignInScreenPreview() {
    AppTheme {
        SignInScreen(
            SignInScreenState(
                username = "aaaa",
                password = "bbbbb",
                error = "cccc"
            )
        )
    }
}