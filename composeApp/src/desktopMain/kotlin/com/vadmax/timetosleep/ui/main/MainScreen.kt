package com.vadmax.timetosleep.ui.main

import KottieAnimation
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClock
import com.vadmax.timetosleep.ui.widgets.numberclock.rememberNumberClockState
import kottieComposition.KottieCompositionSpec
import kottieComposition.animateKottieCompositionAsState
import kottieComposition.rememberKottieComposition
import org.jetbrains.compose.resources.ExperimentalResourceApi
import timetosleepkmp.composeapp.generated.resources.Res
import utils.KottieConstants
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.Date

@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            val state = rememberNumberClockState(Date())
            NumberClock(
                isVibrationEnable = false, numberClockState = state,
            )
            Moon()
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Moon(modifier: Modifier = Modifier) {
    var animation by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        animation = Res.readBytes("files/lt_moon.json").decodeToString()
    }

    val composition = rememberKottieComposition(
        spec = KottieCompositionSpec.File(animation),
    )

    val animationState by animateKottieCompositionAsState(
        composition = composition,
        isPlaying = true,
        iterations = KottieConstants.IterateForever,
    )
    KottieAnimation(
        composition = composition,
        progress = { animationState.progress },
        modifier = modifier.size(300.dp)
    )
}

fun runCommand(command: String): String {
    val processBuilder = ProcessBuilder()
    // Split command into arguments
    processBuilder.command(command.split(" "))

    try {
        val process = processBuilder.start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = StringBuilder()
        var line: String?

        while (reader.readLine().also { line = it } != null) {
            output.append(line).append("\n")
        }

        val exitCode = process.waitFor()
        println("Exit code: $exitCode")
        return output.toString()
    } catch (e: Exception) {
        e.printStackTrace()
        return "Error: ${e.message}"
    }
}