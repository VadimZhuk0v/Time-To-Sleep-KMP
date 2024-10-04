package com.vadmax.timetosleep.domain.usecases.local

import co.touchlab.kermit.Logger
import java.io.BufferedReader
import java.io.InputStreamReader

internal class TurnOffImpl : TurnOff {
    override fun invoke() {
        val processBuilder = ProcessBuilder()
        val command = "shutdown -s -t 0"
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
            process.waitFor()
        } catch (e: Exception) {
            Logger.e("❌ Failed to turn off PC", e)
        }
    }

}