package com.vadmax.timetosleep.domain.usecases.local

import java.net.InetAddress

fun interface GetLocalIPAddress {

    operator fun invoke(): String

}

internal class GetLocalIPAddressImpl : GetLocalIPAddress {

    override fun invoke(): String {
        val localhost = InetAddress.getLocalHost()
        return localhost.hostAddress
    }
}