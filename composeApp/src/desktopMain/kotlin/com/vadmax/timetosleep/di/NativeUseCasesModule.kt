package com.vadmax.timetosleep.di

import com.vadmax.timetosleep.domain.usecases.SendNotification
import com.vadmax.timetosleep.domain.usecases.SendNotificationImpl
import org.koin.dsl.module

actual val nativeUseCasesModule = module {
    factory<SendNotification> { SendNotificationImpl() }
}