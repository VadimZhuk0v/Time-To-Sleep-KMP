package com.vadmax.timetosleep.ui.main

import KottieAnimation
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.ui.main.ui.BackdropContent
import com.vadmax.timetosleep.ui.main.ui.BackdropToolbar
import com.vadmax.timetosleep.ui.main.ui.MainScreenState
import com.vadmax.timetosleep.ui.widgets.backdrop.Backdrop
import com.vadmax.timetosleep.ui.widgets.backdrop.rememberBackdropState
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClock
import com.vadmax.timetosleep.ui.widgets.numberclock.rememberNumberClockState
import com.vadmax.timetosleep.utils.extentions.clickableNoRipple
import com.vadmax.timetosleep.utils.flow.SingleEventEffect
import kotlinx.coroutines.flow.Flow
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import timetosleepkmp.composeapp.generated.resources.Res
import utils.KottieConstants

@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    val enabled by viewModel.enabled.collectAsStateWithLifecycle()
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val connectQRData by viewModel.connectQRData.collectAsStateWithLifecycle()
    val phoneConnected by viewModel.phoneConnected.collectAsStateWithLifecycle()

    MainContent(
        screenState = screenState,
        enabled = enabled,
        connectQRData = connectQRData,
        phoneConnected = phoneConnected,
        setTime = viewModel::setTime,
        switchEnable = viewModel::switchEnable,
        selectTime = viewModel.selectTime,
    )
}

@Composable
private fun MainContent(
    screenState: MainScreenState,
    enabled: Boolean,
    phoneConnected: Boolean,
    connectQRData: String,
    selectTime: Flow<TimeUIModel>,
    switchEnable: () -> Unit,
    setTime: (TimeUIModel) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        AnimatedContent(
            targetState = screenState,
            label = "Screen state",
        ) {
            when (it) {
                is MainScreenState.Idle -> IdleScreenState()
                is MainScreenState.Time -> TimerScreenState(
                    screenState = it,
                    selectTime = selectTime,
                    enabled = enabled,
                    switchEnable = switchEnable,
                    setTime = setTime,
                    connectQRData = connectQRData,
                    phoneConnected = phoneConnected,
                )
            }
        }
    }
}

@Composable
private fun IdleScreenState(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {

    }
}

@Composable
private fun TimerScreenState(
    screenState: MainScreenState.Time,
    connectQRData: String,
    phoneConnected: Boolean,
    selectTime: Flow<TimeUIModel>,
    enabled: Boolean,
    switchEnable: () -> Unit,
    setTime: (TimeUIModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val clockState = rememberNumberClockState(screenState.initialTime)
    val backdropState = rememberBackdropState()

    Backdrop(
        state = backdropState,
        toolbar = {
            BackdropToolbar(
                connected = phoneConnected,
                switchBackdropSate = { backdropState.switch() },
            )
        },
        backdropContent = {
            BackdropContent(
                connectQRData = connectQRData,
            )
        },
    ) {
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NumberClock(
                isVibrationEnable = false,
                numberClockState = clockState,
                onChangeByUser = setTime,
            )
            Moon(
                enabled = enabled,
                switchEnable = switchEnable,
            )
        }
    }
    val coroutineScope = rememberCoroutineScope()
    SingleEventEffect(selectTime) {
        Logger.d("Receive scroll to time")
        clockState.animateToTime(coroutineScope, it)
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
private fun Moon(
    enabled: Boolean,
    switchEnable: () -> Unit,
    modifier: Modifier = Modifier
) {
    var animation by remember { mutableStateOf("") }
    var isPLaying by remember { mutableStateOf(enabled) }

    LaunchedEffect(Unit) {
        animation = Res.readBytes("files/lt_moon.json").decodeToString()
    }

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation),
    )

    val animationState by animateKottieCompositionAsState(
        composition = composition,
        isPlaying = isPLaying,
        iterations = KottieConstants.IterateForever,
    )
    KottieAnimation(
        composition = composition,
        progress = { animationState.progress },
        modifier = modifier.size(300.dp)
            .clickableNoRipple { switchEnable() }
    )

    LaunchedEffect(enabled, animationState.progress) {
        when {
            enabled -> isPLaying = true
            animationState.progress < 0.05F -> isPLaying = false
        }
    }
}
