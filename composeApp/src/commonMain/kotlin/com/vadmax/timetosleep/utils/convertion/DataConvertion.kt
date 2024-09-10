package com.vadmax.timetosleep.utils.convertion

import com.vadmax.timetosleep.data.TimeUIModel
import com.vadmax.timetosleep.domain.data.TimeDomainModel
import com.vadmax.timetosleep.utils.extentions.hour
import com.vadmax.timetosleep.utils.extentions.minute
import java.util.Calendar

fun TimeDomainModel.toUIModel() = TimeUIModel(hours, minutes)

fun TimeUIModel.toDomainModel() = TimeDomainModel(hours, minutes)

fun Calendar.toTimeUIModel() = TimeUIModel(hour, minute)