package com.vadmax.timetosleep.domain.repositories

import com.vadmax.timetosleep.domain.data.TimeDomainModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

internal interface TimerDataRepository {

    val phoneConnected: StateFlow<Boolean>

    val selectedTime: StateFlow<TimeDomainModel>

    val enabled: StateFlow<Boolean>

    suspend fun enable(value: Boolean)

    fun setSelectedTime(time: TimeDomainModel)

    fun setPhoneConnected(value: Boolean)
}

internal class TimerDataRepositoryImpl : TimerDataRepository {

    override val enabled = MutableStateFlow(false)

    override val phoneConnected = MutableStateFlow(false)

    override val selectedTime = MutableStateFlow(Calendar.getInstance().run {
        TimeDomainModel(get(Calendar.HOUR_OF_DAY), get(Calendar.MINUTE))
    })

    override suspend fun enable(value: Boolean) {
        enabled.value = value
    }

    override fun setSelectedTime(time: TimeDomainModel) {
        selectedTime.value = time
    }

    override fun setPhoneConnected(value: Boolean) {
        phoneConnected.value = value
    }

}