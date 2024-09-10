package com.vadmax.timetosleep.repositories

import co.touchlab.kermit.Logger
import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.domain.usecases.remote.GetSelectedTime
import com.vadmax.timetosleep.domain.usecases.remote.GetTimerEnabled
import com.vadmax.timetosleep.domain.usecases.remote.SetTimerEnable
import com.vadmax.timetosleep.domain.usecases.remote.SetSelectedTime
import com.vadmax.timetosleep.utils.convertion.toDomainModel
import com.vadmax.timetosleep.utils.convertion.toUIModel
import com.vadmax.timetosleep.utils.flow.EventFlow
import com.vadmax.timetosleep.utils.flow.MutableEventFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

interface TimerControlRepository {

    val enabled: StateFlow<Boolean>

    val selectedTime: Flow<TimeUIModel>

    val scrollToTime: EventFlow<TimeUIModel>

    suspend fun setTimeByUser(time: TimeUIModel)

    suspend fun switchEnable()

}

internal class TimerControlRepositoryImpl(
    private val coroutineScope: CoroutineScope,
    private val setTimerEnable: SetTimerEnable,
    private val getTimerEnabled: GetTimerEnabled,
    private val getSelectedTime: GetSelectedTime,
    private val setSelectedTime: SetSelectedTime,
) : TimerControlRepository {

    override val selectedTime = getSelectedTime().map { it.toUIModel() }

    override val scrollToTime = MutableEventFlow<TimeUIModel>()

    override val enabled = getTimerEnabled()

    init {
        collectSelectedTime()
    }

    override suspend fun setTimeByUser(time: TimeUIModel) {
        Logger.d("Set selected time by user: $time")
        setSelectedTime(time.toDomainModel())
    }

    override suspend fun switchEnable() {
        setTimerEnable(enabled.value.not())
    }

    private fun collectSelectedTime() {
        coroutineScope.launch {
            selectedTime.collectLatest {
                Logger.d("Scroll to time")
                scrollToTime.emit(it)
            }
        }
    }

}