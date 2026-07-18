import org.gradle.api.artifacts.VersionCatalogsExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")
    id("com.android.kotlin.multiplatform.library")
}

group = "com.wakaztahir"
version = findProperty("version") as String
val composeVersion = extensions.getByType<VersionCatalogsExtension>()
    .named("libs")
    .findVersion("composeMultiplatform")
    .map { it.requiredVersion }
    .orElse(providers.gradleProperty("compose.version").orElse("1.11.1").get())

kotlin {
    android {
        namespace = "com.wakaztahir.codeeditor"
        buildToolsVersion = "37.0.0"
        compileSdk = 37
        minSdk = 23
    }
    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    sourceSets {
        val commonMain = getByName("commonMain") {
            dependencies {
                api("org.jetbrains.compose.runtime:runtime:$composeVersion")
                api("org.jetbrains.compose.foundation:foundation:$composeVersion")
            }
        }
        val commonTest = getByName("commonTest") {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain = getByName("androidMain") {
            dependencies {

            }
        }
//        val androidTest by getting {
//            dependencies {
//                implementation("junit:junit:4.13.2")
//            }
//        }
        val desktopMain = getByName("desktopMain") {
            dependencies {
                api("org.jetbrains.compose.ui:ui-tooling-preview:$composeVersion")
            }
        }
        val desktopTest = getByName("desktopTest")
    }
}
