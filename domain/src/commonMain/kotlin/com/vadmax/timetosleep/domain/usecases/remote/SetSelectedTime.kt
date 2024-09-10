package com.vadmax.timetosleep.domain.usecases.remote

import com.vadmax.timetosleep.domain.data.TimeDomainModel
import com.vadmax.timetosleep.domain.repositories.TimerDataRepository

fun interface SetSelectedTime {

    operator fun invoke(time: TimeDomainModel)

}

internal class SetSelectedTimeImpl(
    private val repository: TimerDataRepository,
) : SetSelectedTime {
    override fun invoke(time: TimeDomainModel) {
        repository.setSelectedTime(time)
    }
}