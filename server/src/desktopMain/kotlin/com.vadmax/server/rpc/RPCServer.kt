package com.vadmax.server.rpc

import co.touchlab.kermit.Logger
import com.vadimax.timetosleep.remote.rcp.service.PCService
import com.vadimax.timetosleep.remote.rcp.service.PCServiceImpl
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import kotlinx.rpc.serialization.json
import kotlinx.rpc.transport.ktor.server.RPC
import kotlinx.rpc.transport.ktor.server.rpc

fun runServer() {
    embeddedServer(Netty, port = 9006) {
        module()
        Logger.i("Start server")
    }.start(wait = true)
}

private fun Application.module() {
    install(RPC)

    routing {
        rpc {
            rpcConfig {
                serialization {
                    json()
                }
            }

            registerService<PCService> { PCServiceImpl(it) }
        }
    }
}