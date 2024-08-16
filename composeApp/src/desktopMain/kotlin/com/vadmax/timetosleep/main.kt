package com.vadmax.timetosleep

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Time To Sleep KMP",
    ) {
        App()
    }
}