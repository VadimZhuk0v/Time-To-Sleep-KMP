package com.vadmax.timetosleep.domain.usecases.remote

import com.vadmax.timetosleep.domain.repositories.TimerDataRepository

fun interface SetPhoneConnected {

    operator fun invoke(value: Boolean)

}

internal class SetPhoneConnectedImpl(
    private val repository: TimerDataRepository
) : SetPhoneConnected {

    override fun invoke(value: Boolean) = repository.setPhoneConnected(value)

}