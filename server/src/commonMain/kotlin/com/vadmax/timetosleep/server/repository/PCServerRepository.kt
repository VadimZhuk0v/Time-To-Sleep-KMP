package com.vadmax.timetosleep.server.repository

import co.touchlab.kermit.Logger
import com.vadimax.timetosleep.remote.rcp.service.PCService
import com.vadimax.timetosleep.remote.rcp.service.PCServiceImpl
import com.vadmax.timetosleep.domain.usecases.remote.GetSelectedTime
import com.vadmax.timetosleep.domain.usecases.remote.GetTimerEnabled
import com.vadmax.timetosleep.domain.usecases.remote.SetPhoneConnected
import com.vadmax.timetosleep.domain.usecases.remote.SetSelectedTime
import com.vadmax.timetosleep.domain.usecases.remote.SetTimerEnable
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import kotlinx.rpc.serialization.json
import kotlinx.rpc.transport.ktor.server.RPC
import kotlinx.rpc.transport.ktor.server.rpc

internal interface PCServerRepository {

    suspend fun startServer()

}

internal class PCServerRepositoryImpl(
    private val setSelectedTime: SetSelectedTime,
    private val getSelectedTime: GetSelectedTime,
    private val getTimerEnabled: GetTimerEnabled,
    private val setEnabled: SetTimerEnable,
    private val setPhoneConnected: SetPhoneConnected,
) : PCServerRepository {


    override suspend fun startServer() {
        embeddedServer(Netty, port = 9006) {
            install(RPC)
            routing {
                rpc {
                    rpcConfig {
                        serialization {
                            json()
                        }
                    }
                    registerService<PCService> {
                        PCServiceImpl(
                            coroutineContext = it,
                            setSelectedTime = setSelectedTime,
                            getSelectedTime = getSelectedTime,
                            getTimerEnabled = getTimerEnabled,
                            setEnabled = setEnabled,
                            setPhoneConnected = setPhoneConnected,
                        )
                    }
                }
            }
            Logger.i("ℹ️ Start server")
        }.start(wait = true)
    }
}