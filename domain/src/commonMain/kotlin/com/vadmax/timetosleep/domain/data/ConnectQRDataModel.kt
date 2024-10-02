package com.vadmax.timetosleep.domain.data

import kotlinx.serialization.Serializable

@Serializable
internal class ConnectQRDataModel(
    val ipAddress: String,
    val port: Int,
)