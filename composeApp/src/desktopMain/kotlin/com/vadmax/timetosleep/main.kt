package com.vadmax.timetosleep

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.vadmax.timetosleep.di.viewModelModule
import org.koin.core.context.GlobalContext.startKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Time To Sleep KMP",
        resizable = false,
        state = WindowState(
            size = DpSize(500.dp, 500.dp)
        )
    ) {
        App()
    }
}

private fun initKoin() {
    startKoin {
        modules(viewModelModule)
    }
}