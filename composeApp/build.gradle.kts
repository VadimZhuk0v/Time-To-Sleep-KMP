import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
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
            implementation(project(":core"))
            implementation(project(":domain"))
            implementation(project(":server"))
            implementation(compose.materialIconsExtended)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.qr.kit)

            // DI
            implementation(libs.koin.compose)
            implementation(libs.koin.composeVM)

            // Navigation
            implementation(libs.navigation)

            //Kottie
            implementation(libs.kottie)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }

    composeCompiler {
        enableStrongSkippingMode = true
    }
}

ksp {
    arg("KOIN_USE_COMPOSE_VIEWMODEL", "true")
}

compose.desktop {
    application {
        buildTypes.release.proguard {
            version.set("7.5.0")
            obfuscate.set(false)
            optimize.set(false)
            joinOutputJars.set(true)
        }
        mainClass = "com.vadmax.timetosleep.MainKt"
        nativeDistributions {
            outputBaseDir.set(File("${projectDir.parentFile.path}/distribute"))
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.vadmax.timetosleep"
            packageVersion = "1.0.0"
            vendor = "VadMax"
            windows {
                menuGroup = "VadMax"
                dirChooser = true
                installationPath = "C:\\Program Files\\Time To Sleep\\"
                iconFile.set(File("${projectDir.parentFile.path}/distribute/app_icon.ico"))
            }
        }
    }
}
