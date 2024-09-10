package com.vadmax.timetosleep.domain.repositories

import co.touchlab.kermit.Logger
import com.vadmax.timetosleep.domain.data.TimeDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

internal interface TimerDataRepository {
    val selectedTime: StateFlow<TimeDomainModel>

    val enabled: StateFlow<Boolean>

    suspend fun enable(value: Boolean)

    fun setSelectedTime(time: TimeDomainModel)
}

internal class TimerDataRepositoryImpl : TimerDataRepository {

    override val enabled = MutableStateFlow(false)

    override val selectedTime = MutableStateFlow(Calendar.getInstance().run {
        TimeDomainModel(get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE))
    })

    override suspend fun enable(value: Boolean) {
        enabled.value = value
    }

    override fun setSelectedTime(time: TimeDomainModel) {
        selectedTime.value = time
    }

}