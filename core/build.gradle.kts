@file:OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidx.room)
}

room {
    schemaDirectory("$projectDir/schemas")
}

kotlin {
    applyDefaultHierarchyTemplate()

    androidTarget {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-P",
                "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=solo.trader.weather.app.project.zoomable.internal.AndroidParcelize"
            )
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val roomSupportedMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                api(libs.androidx.room.runtime)
                api(libs.androidx.sqlite.bundled)
            }
        }
        androidMain.get().dependsOn(roomSupportedMain)
        iosMain.get().dependsOn(roomSupportedMain)

        commonMain.dependencies {
            api(libs.kotlinx.datetime)
            api(libs.kotlinx.serialization.core)
            api(libs.kotlinx.serialization.json)
            api(libs.kermit)
            api(libs.koin.core)
            api(libs.koin.compose)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
        }

        androidMain.dependencies {
            api(libs.androidx.sqlite.bundled)
            api(libs.androidx.room.sqlite.wrapper)
        }
    }

    jvmToolchain(21)
}

android {
    namespace = "solo.trader.weather.app.project.model"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}