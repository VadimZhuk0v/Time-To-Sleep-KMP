package com.vadmax.timetosleep.server.rpc.data

import kotlinx.serialization.Serializable

@Serializable
data class TimeRemoteModel(
    val hours: Int,
    val minutes: Int,
)