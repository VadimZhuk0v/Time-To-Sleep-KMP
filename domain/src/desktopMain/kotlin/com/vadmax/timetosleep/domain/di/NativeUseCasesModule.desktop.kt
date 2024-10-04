package com.vadmax.timetosleep.domain.di

import com.vadmax.timetosleep.domain.usecases.local.TurnOff
import com.vadmax.timetosleep.domain.usecases.local.TurnOffImpl
import org.koin.dsl.module

internal actual val nativeUseCasesModule = module {
    factory<TurnOff> { TurnOffImpl() }
}