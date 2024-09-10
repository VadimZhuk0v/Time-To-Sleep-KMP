package com.vadmax.timetosleep.ui.widgets.numberclock

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.ui.widgets.numberclock.NumberClockState.Companion.Saver
import com.vadmax.timetosleep.utils.extentions.hour
import com.vadmax.timetosleep.utils.extentions.minute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@Composable
fun rememberNumberClockState(
    initialHour: Int,
    initialMinute: Int,
): NumberClockState = rememberSaveable(saver = Saver) {
    NumberClockState(
        LazyListState(
            firstVisibleItemIndex = initialHour,
        ),
        LazyListState(
            firstVisibleItemIndex = initialMinute,
        ),
    )
}

@Composable
fun rememberNumberClockState(time: TimeUIModel): NumberClockState =
    rememberNumberClockState(initialHour = time.hours, initialMinute = time.minutes)

@Composable
fun rememberNumberClockState(calendar: Calendar): NumberClockState =
    rememberNumberClockState(initialHour = calendar.hour, initialMinute = calendar.minute)

@Composable
fun rememberNumberClockState(time: Date): NumberClockState =
    rememberNumberClockState(Calendar.getInstance().apply { this.time = time })

@Stable
class NumberClockState(val hourState: LazyListState, val minuteState: LazyListState) {

    val time by derivedStateOf {
        TimeUIModel(hourState.firstVisibleItemIndex, minuteState.firstVisibleItemIndex)
    }

    var scrolledProgrammatically = false
        private set

    fun animateToTime(scope: CoroutineScope, time: TimeUIModel) {
        scrolledProgrammatically = true
        val minJob = scope.launch {
            minuteState.animateScrollToItem(time.minutes)
        }
        val hourJob = scope.launch {
            hourState.animateScrollToItem(time.hours)
        }
        scope.launch {
            minJob.join()
            hourJob.join()
            scrolledProgrammatically = false
        }
    }

    companion object {
        val Saver: Saver<NumberClockState, *> = listSaver(
            save = {
                listOf(
                    it.hourState.firstVisibleItemIndex,
                    it.minuteState.firstVisibleItemIndex,
                )
            },
            restore = {
                NumberClockState(
                    LazyListState(it[0]),
                    LazyListState(it[1]),
                )
            },
        )
    }
}
