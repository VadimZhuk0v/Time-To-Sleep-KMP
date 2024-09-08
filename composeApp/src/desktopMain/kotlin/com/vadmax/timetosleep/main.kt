package com.vadmax.timetosleep

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vadmax.timetosleep.di.viewModelModule
import org.koin.core.context.GlobalContext.startKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Time To Sleep KMP",
    ) {
        App()
    }
}

private fun initKoin() {
    startKoin {
        modules(viewModelModule)
    }
}