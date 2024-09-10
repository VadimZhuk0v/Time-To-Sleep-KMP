package com.vadmax.timetosleep.domain.di

import com.vadmax.timetosleep.domain.repositories.TimerDataRepository
import com.vadmax.timetosleep.domain.repositories.TimerDataRepositoryImpl
import org.koin.dsl.module

internal val repositoriesModule = module {
    single<TimerDataRepository> { TimerDataRepositoryImpl() }
}