package com.renattele.sma1.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.renattele.sma1.R
import com.renattele.sma1.presentation.components.Spinner
import kotlinx.coroutines.Dispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    state: MainScreenState = rememberMainScreenState(),
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            confirmValueChange = { value ->
                value != SheetValue.PartiallyExpanded
            },
            skipHiddenState = false
        )
    )
    LaunchedEffect(state.permissionMessageVisible) {
        if (state.permissionMessageVisible) {
            scaffoldState.bottomSheetState.expand()
        } else {
            scaffoldState.bottomSheetState.hide()
        }
    }

    BottomSheetScaffold({
        Text(
            stringResource(R.string.please_give_notification_permission),
            Modifier
                .padding(40.dp)
                .align(Alignment.CenterHorizontally)
        )
        Button(onClick = {
            state.eventSink(MainScreenEvent.GoToSettings)
        }, Modifier.align(Alignment.CenterHorizontally)) {
            Text(stringResource(R.string.go_to_settings))
        }
    }, modifier, scaffoldState = scaffoldState) {
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(state.coroutinesCount, onValueChange = { newValue ->
                state.eventSink(MainScreenEvent.ChangeCoroutinesCount(newValue.filter { it.isDigit() }))
            }, Modifier.fillMaxWidth(), isError = state.isCoroutineCountInvalid, supportingText = {
                if (state.isCoroutineCountInvalid) {
                    Text(stringResource(R.string.invalid_value))
                }
            })
            Text(stringResource(R.string.coroutine_type))
            RadioGroup(
                CoroutineType.Parallel to stringResource(R.string.parallel),
                CoroutineType.Sequential to stringResource(R.string.sequential),
                selected = state.coroutineType,
                onSelect = {
                    state.eventSink(MainScreenEvent.ChangeCoroutineType(it))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Text(stringResource(R.string.coroutine_hiding_logic))
            RadioGroup(
                CoroutineHidingLogic.Continue to stringResource(R.string.continue_word),
                CoroutineHidingLogic.Cancel to stringResource(R.string.cancel),
                selected = state.coroutineHidingLogic,
                onSelect = {
                    state.eventSink(MainScreenEvent.ChangeCoroutineHidingLogic(it))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Text(stringResource(R.string.select_pool))
            Spinner(
                selected = state.coroutineDispatcher,
                Dispatchers.Main to stringResource(R.string.main),
                Dispatchers.IO to stringResource(R.string.io),
                Dispatchers.Default to stringResource(R.string.default_word),
                Dispatchers.Unconfined to stringResource(R.string.unconfined),
                onSelect = {
                    state.eventSink(MainScreenEvent.ChangeCoroutineDispatcher(it))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Button({
                state.eventSink(MainScreenEvent.Run)
            }) {
                Text(stringResource(R.string.start))
            }
            Button({
                state.eventSink(MainScreenEvent.Cancel)
            }) {
                Text(stringResource(R.string.cancel))
            }
        }
    }
}

enum class CoroutineType {
    Parallel,
    Sequential
}

enum class CoroutineHidingLogic {
    Cancel,
    Continue
}

@Composable
fun <T> RadioGroup(
    vararg values: Pair<T, String>,
    selected: T,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        values.forEach { value ->
            RadioButtonText(value.second, selected = value.first == selected, onSelect = {
                onSelect(value.first)
            }, Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun RadioButtonText(
    label: String,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .clip(CircleShape)
            .clickable(onClick = onSelect),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected, onClick = onSelect)
        Text(label)
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MaterialTheme {
        MainScreen()
    }
}