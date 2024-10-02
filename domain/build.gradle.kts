plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.serialization)
}

kotlin {
    jvm("desktop")
    sourceSets {
        val desktopMain by getting

        val commonMain by getting {
            dependencies {
                implementation(project(":core"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
            }
        }
    }
}