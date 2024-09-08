package com.vadmax.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

fun testCore() = ""

