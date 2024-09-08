plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            api(libs.koin.core)
            api(libs.kotlinx.coroutines.swing)
            api("co.touchlab:kermit:2.0.4")
        }
    }
}