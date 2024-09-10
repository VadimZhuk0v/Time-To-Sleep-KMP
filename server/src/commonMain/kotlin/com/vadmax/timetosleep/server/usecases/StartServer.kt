package com.vadmax.timetosleep.server.usecases

import com.vadmax.timetosleep.server.repository.PCServerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

fun interface StartServer {

    suspend operator fun invoke()

}

internal class StartServerImpl(
    private val pcServerRepository: PCServerRepository,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : StartServer {
    override suspend fun invoke() = withContext(coroutineContext) {
        pcServerRepository.startServer()
    }
}