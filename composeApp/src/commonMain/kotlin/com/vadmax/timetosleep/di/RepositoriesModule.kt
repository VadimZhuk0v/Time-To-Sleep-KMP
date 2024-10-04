package com.vadmax.timetosleep.di

import com.vadmax.timetosleep.domain.repositories.TimerControlRepository
import com.vadmax.timetosleep.domain.repositories.TimerControlRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {
    single<TimerControlRepository> {
        TimerControlRepositoryImpl(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
}