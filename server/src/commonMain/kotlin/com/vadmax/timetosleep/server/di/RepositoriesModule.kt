package com.vadmax.timetosleep.server.di

import com.vadmax.timetosleep.server.repository.PCServerRepository
import com.vadmax.timetosleep.server.repository.PCServerRepositoryImpl
import com.vadmax.timetosleep.server.usecases.StartServer
import com.vadmax.timetosleep.server.usecases.StartServerImpl
import org.koin.dsl.module

internal val repositoriesModule = module {
    factory<PCServerRepository> {
        PCServerRepositoryImpl(
            get(),
            get(),
            get(),
            get(),
            get(),
        )
    }
    factory<StartServer> { StartServerImpl(get()) }
}