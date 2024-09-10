plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.serialization)
    alias(libs.plugins.rpc.platform)
    alias(libs.plugins.google.ksp)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting
        commonMain.dependencies {
            implementation(project(":core"))
            implementation(project(":domain"))
            implementation(libs.ktor.server)

            //RPC
            implementation(libs.kotlinx.rpc.ktor)
            implementation(libs.kotlinx.rpc.server)
            implementation(libs.kotlinx.rpc.json)
            implementation(libs.kotlinx.rpc.client)
            implementation(libs.kotlinx.rpc.client.ktor)
            implementation(libs.logback.classic)
        }
    }
}