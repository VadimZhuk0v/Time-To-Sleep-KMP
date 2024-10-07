package com.vadimax.timetosleep.remote.rcp.service

import co.touchlab.kermit.Logger
import com.vadmax.timetosleep.domain.usecases.local.TurnOff
import com.vadmax.timetosleep.domain.usecases.remote.GetSelectedTime
import com.vadmax.timetosleep.domain.usecases.remote.GetTimerEnabled
import com.vadmax.timetosleep.domain.usecases.remote.SetPhoneConnected
import com.vadmax.timetosleep.domain.usecases.remote.SetSelectedTime
import com.vadmax.timetosleep.domain.usecases.remote.SetTimerEnable
import com.vadmax.timetosleep.server.rpc.data.TimeRemoteModel
import com.vadmax.timetosleep.server.utils.toDomainModel
import com.vadmax.timetosleep.server.utils.toRemoteModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.rpc.RPC
import kotlin.coroutines.CoroutineContext

interface PCService : RPC {

    suspend fun pingStatus(): Flow<Unit>

    suspend fun receiveTime(): Flow<TimeRemoteModel>

    suspend fun shareTime(time: Flow<TimeRemoteModel>)

    suspend fun sendEnable(value: Boolean)

    suspend fun listenEnable(): Flow<Boolean>

    suspend fun turnOff()

}

class PCServiceImpl(
    override val coroutineContext: CoroutineContext,
    private val setSelectedTime: SetSelectedTime,
    private val getSelectedTime: GetSelectedTime,
    private val getTimerEnabled: GetTimerEnabled,
    private val setEnabled: SetTimerEnable,
    private val turnOff: TurnOff,
    private val setPhoneConnected: SetPhoneConnected,
) : PCService {

    override suspend fun pingStatus(): Flow<Unit> {
        return flow {
            while (true) {
                emit(Unit)
                delay(2000)
            }
        }.onStart {
            setPhoneConnected(true)
        }.onCompletion {
            setPhoneConnected(false)
        }
    }

    @OptIn(FlowPreview::class)
    override suspend fun receiveTime(): Flow<TimeRemoteModel> {
        return getSelectedTime().debounce(500)
            .onEach { Logger.d("Send time to phone $it") }
            .map { it.toRemoteModel() }
    }

    override suspend fun shareTime(time: Flow<TimeRemoteModel>) {
        time.collectLatest {
            Logger.d("Receive time from phone $it")
            setSelectedTime(it.toDomainModel())
        }
    }

    override suspend fun listenEnable(): Flow<Boolean> {
        return getTimerEnabled()
    }

    override suspend fun sendEnable(value: Boolean) {
        setEnabled(value)
    }

    override suspend fun turnOff() {
        turnOff.invoke()
    }

}