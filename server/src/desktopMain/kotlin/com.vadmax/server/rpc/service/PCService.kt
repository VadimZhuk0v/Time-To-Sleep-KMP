package com.vadimax.timetosleep.remote.rcp.service

import co.touchlab.kermit.Logger
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.rpc.RPC
import kotlin.coroutines.CoroutineContext

interface PCService : RPC {

    suspend fun pingStatus(): Flow<Unit>

}

class PCServiceImpl(
    override val coroutineContext: CoroutineContext
) : PCService {

    override suspend fun pingStatus(): Flow<Unit> {
        return flow {
            while (true) {
                Logger.d("Send ping")
                emit(Unit)
                delay(3000)
            }
        }
    }

}