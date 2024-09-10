package com.vadmax.timetosleep.domain.usecases.remote

import com.vadmax.timetosleep.domain.data.TimeDomainModel
import com.vadmax.timetosleep.domain.repositories.TimerDataRepository
import kotlinx.coroutines.flow.Flow

fun interface GetSelectedTime {

    operator fun invoke(): Flow<TimeDomainModel>

}

internal class GetSelectedTimeImpl(
    private val repository: TimerDataRepository,
) : GetSelectedTime {
    override fun invoke(): Flow<TimeDomainModel> {
        return repository.selectedTime
    }
}