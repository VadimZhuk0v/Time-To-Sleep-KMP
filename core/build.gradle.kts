plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.google.ksp)
}

kotlin {
    jvm("desktop")

    dependencies {
        add("kspCommonMainMetadata", libs.koin.ksp.compiler)
        add("kspDesktop", libs.koin.ksp.compiler)
    }

    sourceSets {
        val desktopMain by getting
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.swing)
            // DI
            api(project.dependencies.platform(libs.koin.bom))
            api(libs.koin.core)
            api(libs.koin.annotation)

            // Logger
            api("co.touchlab:kermit:2.0.4")
        }
    }
}