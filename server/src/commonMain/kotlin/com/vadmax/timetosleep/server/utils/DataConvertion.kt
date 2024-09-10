package com.vadmax.timetosleep.server.utils

import com.vadmax.timetosleep.domain.data.TimeDomainModel
import com.vadmax.timetosleep.server.rpc.data.TimeRemoteModel

internal fun TimeRemoteModel.toDomainModel() = TimeDomainModel(hours, minutes)

internal fun TimeDomainModel.toRemoteModel() = TimeRemoteModel(hours, minutes)

