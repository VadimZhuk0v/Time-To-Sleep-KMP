package com.vadmax.timetosleep

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Tray
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberTrayState
import androidx.compose.ui.window.rememberWindowState
import com.vadmax.timetosleep.di.ViewModelModule
import com.vadmax.timetosleep.di.nativeUseCasesModule
import com.vadmax.timetosleep.di.repositoriesModule
import com.vadmax.timetosleep.domain.di.domainModules
import com.vadmax.timetosleep.server.di.remoteModules
import com.vadmax.timetosleep.server.usecases.StartServer
import com.vadmax.timetosleep.ui.app.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import org.koin.ksp.generated.module
import timetosleepkmp.composeapp.generated.resources.Res
import timetosleepkmp.composeapp.generated.resources.ic_launcher_round

fun main() {
    val koin = initKoin()
    val runServer: StartServer = koin.get()
    val coroutineScope = CoroutineScope(Dispatchers.IO)
    coroutineScope.launch {
        runServer()
    }
    application {
        val windowState = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.BottomEnd),
            size = DpSize(500.dp, 500.dp)
        )
        var showApp by remember { mutableStateOf(true) }
        val trayState = rememberTrayState()
        Tray(
            icon = painterResource(Res.drawable.ic_launcher_round),
            state = trayState,
            onAction = {
                showApp = true
            },
            menu = {
                Item(
                    text = "Close",
                    onClick = {
                        coroutineScope.cancel()
                        exitApplication()
                    },
                )
            },
            tooltip = "Time To Sleep",
        )
        if (showApp) {
            Window(
                state = windowState,
                onCloseRequest = { showApp = false },
                title = "Time To Sleep",
                resizable = false,
                icon = painterResource(Res.drawable.ic_launcher_round),
            ) {
                App()
            }
        }
    }
}

private fun initKoin(): Koin {
    return startKoin {
        modules(
            listOf(
                ViewModelModule().module,
                nativeUseCasesModule,
                repositoriesModule,
                module { single { CoroutineScope(Dispatchers.Main) } }
            ) + domainModules + remoteModules,
        )
    }.koin
}