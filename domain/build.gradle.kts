plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    jvm("desktop")
    sourceSets {
        val desktopMain by getting

        val commonMain by getting {
            dependencies {
                implementation(project(":core"))
            }
        }
    }
}