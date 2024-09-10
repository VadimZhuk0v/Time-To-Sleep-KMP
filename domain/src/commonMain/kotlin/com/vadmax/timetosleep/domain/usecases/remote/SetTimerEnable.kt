package com.vadmax.timetosleep.domain.usecases.remote

import com.vadmax.timetosleep.domain.repositories.TimerDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

fun interface SetTimerEnable {

    suspend operator fun invoke(value: Boolean)

}

internal class SetTimerEnableImpl(
    private val repository: TimerDataRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) : SetTimerEnable {

    override suspend fun invoke(value: Boolean) = withContext(coroutineContext) {
        repository.enable(value)
    }
}
