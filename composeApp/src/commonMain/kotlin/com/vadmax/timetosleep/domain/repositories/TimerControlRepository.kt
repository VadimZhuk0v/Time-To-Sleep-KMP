package com.vadmax.timetosleep.domain.repositories

import co.touchlab.kermit.Logger
import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.domain.data.TimeDomainModel
import com.vadmax.timetosleep.domain.usecases.SendNotification
import com.vadmax.timetosleep.domain.usecases.local.TurnOff
import com.vadmax.timetosleep.domain.usecases.remote.GetSelectedTime
import com.vadmax.timetosleep.domain.usecases.remote.GetTimerEnabled
import com.vadmax.timetosleep.domain.usecases.remote.SetSelectedTime
import com.vadmax.timetosleep.domain.usecases.remote.SetTimerEnable
import com.vadmax.timetosleep.utils.convertion.toDomainModel
import com.vadmax.timetosleep.utils.convertion.toUIModel
import com.vadmax.timetosleep.utils.extentions.hour
import com.vadmax.timetosleep.utils.extentions.minute
import com.vadmax.timetosleep.utils.extentions.second
import com.vadmax.timetosleep.utils.flow.EventFlow
import com.vadmax.timetosleep.utils.flow.MutableEventFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Duration
import java.util.Calendar
import java.util.Date
import kotlin.time.Duration.Companion.minutes

interface TimerControlRepository {

    val enabled: StateFlow<Boolean>

    val selectedTime: Flow<TimeUIModel>

    val scrollToTime: EventFlow<TimeUIModel>

    suspend fun setTimeByUser(time: TimeUIModel)

    suspend fun switchEnable()

}

internal class TimerControlRepositoryImpl(
    getTimerEnabled: GetTimerEnabled,
    private val coroutineScope: CoroutineScope,
    private val setTimerEnable: SetTimerEnable,
    private val getSelectedTime: GetSelectedTime,
    private val setSelectedTime: SetSelectedTime,
    private val turnOff: TurnOff,
    private val sendNotification: SendNotification,
) : TimerControlRepository {

    override val selectedTime = getSelectedTime().map { it.toUIModel() }

    override val scrollToTime = MutableEventFlow<TimeUIModel>()

    override val enabled = getTimerEnabled()

    init {
        collectSelectedTime()
        collectEnabled()
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

    @OptIn(FlowPreview::class)
    private fun collectEnabled() {
        coroutineScope.launch {
            combine(enabled, getSelectedTime()) { enabled, selectedTime ->
                enabled to selectedTime
            }.debounce(1000).collectLatest {
                val enabled = it.first
                val time = it.second
                Logger.i("Enabled: $enabled")
                if (enabled) {
                    val date = calculateTurnOffTime(time)
                    scheduleTurnOff(date)
                }
            }
        }
    }

    private fun calculateTurnOffTime(time: TimeDomainModel): Calendar {
        return Calendar.getInstance().apply {
            this.hour = time.hours
            this.minute = time.minutes
            second = 0
            if (this.time < Date()) {
                this.add(Calendar.DAY_OF_YEAR, 1)
            }
        }
    }

    private fun calculateToTurnOffDelay(turnOffDate: Calendar): Long {
        val selectedTime = turnOffDate.timeInMillis
        val currentTimeInMillis = System.currentTimeMillis()
        return selectedTime - currentTimeInMillis
    }

    private suspend fun scheduleTurnOff(date: Calendar) {
        Logger.i("PC will be turned off at $date")
        val now = Calendar.getInstance()
        val delayMillis = calculateToTurnOffDelay(date)
        val minutesDiff = Duration.between(now.toInstant(), date.toInstant()).toMinutes()
        sendNotification(
            "PC will be turned off at ${date.get(Calendar.HOUR_OF_DAY)}:${
                date.get(
                    Calendar.MINUTE
                )
            }"
        )
        if (minutesDiff > 5) {
            val beforeNotificationDelay = delayMillis - 5.minutes.inWholeMilliseconds
            delay(beforeNotificationDelay)
            sendNotification("PC will be turned off in 5 minutes")
            delay(5.minutes)
            turnOff()
        } else {
            delay(delayMillis)
            turnOff()
        }
    }

}