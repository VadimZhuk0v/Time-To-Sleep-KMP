package com.vadmax.timetosleep.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vadmax.server.rpc.runServer
import com.vadmax.timetosleep.Greeting
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import timetosleepkmp.composeapp.generated.resources.Res
import timetosleepkmp.composeapp.generated.resources.compose_multiplatform
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    var showContent by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    runServer()
                }
            }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
    }
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