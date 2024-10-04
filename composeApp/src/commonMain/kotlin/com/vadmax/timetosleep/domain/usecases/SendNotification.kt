package com.vadmax.timetosleep.domain.usecases

fun interface SendNotification {

    operator fun invoke(message: String)

}