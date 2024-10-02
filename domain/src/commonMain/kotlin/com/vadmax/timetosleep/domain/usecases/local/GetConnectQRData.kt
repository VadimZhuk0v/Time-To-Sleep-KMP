package com.vadmax.timetosleep.domain.usecases.local

import com.vadmax.timetosleep.domain.data.ConnectQRDataModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun interface GetConnectQRData {

    operator fun invoke(): String

}

internal class GetConnectQRDataImpl(
    private val getLocalIPAddress: GetLocalIPAddress,
) : GetConnectQRData {

    override fun invoke(): String {
        val ipAddress = getLocalIPAddress()
        val port = 9006
        val data = ConnectQRDataModel(
            ipAddress = ipAddress,
            port = port,
        )
        return Json.encodeToString(data)
    }
}