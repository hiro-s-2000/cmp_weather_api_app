@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import com.mikepenz.aboutlibraries.plugin.DuplicateMode
import com.mikepenz.aboutlibraries.plugin.DuplicateRule
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    alias(libs.plugins.aboutLibraries)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
    androidTarget {
        compilerOptions {
            freeCompilerArgs.addAll(
                "-P",
                "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=solo.trader.weather.app.project.zoomable.internal.AndroidParcelize"
            )
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            api(projects.core)
            api(projects.uiComponents)

            api(libs.compose.runtime)
            api(libs.compose.foundation)
            api(libs.compose.animation)
            api(libs.compose.components.resources)

            api(libs.koin.compose.viewmodel.navigation)

            api(libs.ktor.client.logging)
            api(libs.ktor.serialization.kotlinx.json)
            api(libs.ktor.client.content.negotiation)
            api(libs.ktor.utils)

            implementation(libs.kotlinx.datetime)

            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.material3)
            implementation(libs.androidx.navigation3.ui)
            implementation(libs.androidx.lifecycle.viewmodel.navigation3)
            implementation(libs.ktor.client.core)

            implementation(libs.aboutlibraries.core)

            // Multiplatform Settings
            implementation(libs.settings)
            implementation(libs.settings.serialization)
            implementation(libs.settings.observable)
            implementation(libs.settings.coroutines)

            implementation(libs.doistx.normalize)

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.sqlite.bundled)
            implementation(libs.compass.geolocation)
            implementation(libs.compass.geolocation.mobile)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.settings.test)
            implementation(libs.kotlinx.coroutines.test)
        }

        val nonAndroidMain by creating {
            dependsOn(commonMain.get())
        }
        configure(listOf(iosMain)) {
            get().dependsOn(nonAndroidMain)
        }

        androidMain.dependencies {
            implementation(libs.android.svg)
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.preference)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.darwin)
        }
    }

    jvmToolchain(21)
}

android {
    namespace = "solo.trader.weather.app.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    debugImplementation(libs.compose.ui.tooling)
    ksp(libs.androidx.room.compiler)
}

compose.desktop {
    application {
        mainClass = "solo.trader.weather.app.project.MainKt"
    }
}

compose.resources {
    packageOfResClass = "solo.trader.weather.app.project.generated.resources"
}

aboutLibraries {
    library.duplicationMode = DuplicateMode.MERGE
    library.duplicationRule = DuplicateRule.SIMPLE
    export.outputFile = File("src/commonMain/composeResources/files")
}
