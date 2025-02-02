package com.renattele.sma1.presentation.screen

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LifecycleResumeEffect
import com.renattele.sma1.R
import com.renattele.sma1.utils.showToast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

data class MainScreenState(
    val coroutinesCount: String,
    val coroutineType: CoroutineType,
    val coroutineHidingLogic: CoroutineHidingLogic,
    val coroutineDispatcher: CoroutineDispatcher,
    val permissionMessageVisible: Boolean,
    val isCoroutineCountInvalid: Boolean,
    val eventSink: (MainScreenEvent) -> Unit,
)

sealed interface MainScreenEvent {
    data class ChangeCoroutinesCount(val count: String) : MainScreenEvent
    data class ChangeCoroutineType(val type: CoroutineType) : MainScreenEvent
    data class ChangeCoroutineHidingLogic(val logic: CoroutineHidingLogic) : MainScreenEvent
    data class ChangeCoroutineDispatcher(val dispatcher: CoroutineDispatcher) : MainScreenEvent
    data object Run : MainScreenEvent
    data object GoToSettings : MainScreenEvent
    data object Cancel : MainScreenEvent
}

@Composable
fun rememberMainScreenState(): MainScreenState {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var coroutinesCount by remember { mutableStateOf("1") }
    var coroutineType by remember { mutableStateOf(CoroutineType.Parallel) }
    var coroutineHidingLogic by remember { mutableStateOf(CoroutineHidingLogic.Continue) }
    var dispatcher: CoroutineDispatcher by remember { mutableStateOf(Dispatchers.Main) }

    var permissionMessageVisible by remember { mutableStateOf(false) }
    var deniedCount by remember { mutableIntStateOf(0) }
    var permissionGranted by remember { mutableStateOf(false) }
    val isCoroutineCountInvalid =
        coroutinesCount.isEmpty() || coroutinesCount.toInt() <= 0
    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            permissionGranted = granted
            if (!granted) deniedCount++
            if (deniedCount > 2) permissionMessageVisible = true
        }

    var job by remember { mutableStateOf<Job?>(null) }
    var completedCount by remember { mutableIntStateOf(0) }

    LifecycleResumeEffect(Unit) {
        onPauseOrDispose {
            if (coroutineHidingLogic == CoroutineHidingLogic.Cancel) {
                job?.cancel()
            }
        }
    }

    return MainScreenState(
        coroutinesCount = coroutinesCount,
        coroutineType = coroutineType,
        coroutineHidingLogic = coroutineHidingLogic,
        coroutineDispatcher = dispatcher,
        permissionMessageVisible = permissionMessageVisible,
        isCoroutineCountInvalid = isCoroutineCountInvalid
    ) { event ->
        when (event) {
            is MainScreenEvent.ChangeCoroutineDispatcher -> dispatcher = event.dispatcher
            is MainScreenEvent.ChangeCoroutineHidingLogic -> coroutineHidingLogic = event.logic
            is MainScreenEvent.ChangeCoroutineType -> coroutineType = event.type
            is MainScreenEvent.ChangeCoroutinesCount -> coroutinesCount = event.count

            MainScreenEvent.GoToSettings -> {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", context.packageName, null)
                context.startActivity(intent)
            }

            MainScreenEvent.Run -> {
                if (isCoroutineCountInvalid) return@MainScreenState
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
                if (job?.isActive == true) {
                    context.showToast(context.getString(R.string.process_is_already_running))
                } else job =
                    coroutineScope.launch(SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
                        context.showToast(
                            context.getString(
                                R.string.exception,
                                throwable.message
                            )
                        )
                    }) {
                        workload(coroutinesCount.toInt(), coroutineType, dispatcher) {
                            completedCount = it
                        }
                    }
            }

            MainScreenEvent.Cancel -> {
                if (job?.isActive == false) {
                    context.showToast(context.getString(R.string.process_is_not_running))
                } else {
                    job?.cancel()
                    context.showToast(
                        context.getString(
                            R.string.cancelled_coroutines,
                            coroutinesCount.toInt() - completedCount
                        )
                    )
                }
            }
        }
    }
}

private suspend fun workload(
    count: Int,
    coroutineType: CoroutineType,
    dispatcher: CoroutineDispatcher,
    completedCount: (Int) -> Unit,
) = coroutineScope {
    var completed = 0
    launch(dispatcher) {
        if (coroutineType == CoroutineType.Parallel) {
            repeat(count) {
                launch {
                    delay(Random.nextLong(1000))
                    completedCount(++completed)
                }
            }
        } else {
            launch {
                repeat(count) {
                    delay(Random.nextLong(20))
                    completedCount(++completed)
                }
            }
        }
    }
}