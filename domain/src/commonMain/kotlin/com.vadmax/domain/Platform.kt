package com.vadmax.domain

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


