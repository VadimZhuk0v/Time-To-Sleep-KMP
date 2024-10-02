package com.vadmax.timetosleep.domain.usecases.remote

import com.vadmax.timetosleep.domain.repositories.TimerDataRepository
import kotlinx.coroutines.flow.StateFlow

fun interface GetPhoneConnected {

    operator fun invoke(): StateFlow<Boolean>

}

internal class GetPhoneConnectedImpl(
    private val repository: TimerDataRepository
) : GetPhoneConnected {

    override fun invoke() = repository.phoneConnected
}