package com.vadmax.timetosleep.domain.usecases.remote

import com.vadmax.timetosleep.domain.repositories.TimerDataRepository
import kotlinx.coroutines.flow.StateFlow

fun interface GetTimerEnabled {

    operator fun invoke(): StateFlow<Boolean>

}

internal class GetTimerEnabledImpl(
    private val repository: TimerDataRepository,
) : GetTimerEnabled {

    override fun invoke() = repository.enabled
}
