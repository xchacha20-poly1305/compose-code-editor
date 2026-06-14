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
        compileSdk = 37
        minSdk = 23
    }
    jvm("desktop") {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.compose.runtime:runtime:$composeVersion")
                api("org.jetbrains.compose.foundation:foundation:$composeVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {

            }
        }
//        val androidTest by getting {
//            dependencies {
//                implementation("junit:junit:4.13.2")
//            }
//        }
        val desktopMain by getting {
            dependencies {
                api("org.jetbrains.compose.ui:ui-tooling-preview:$composeVersion")
            }
        }
        val desktopTest by getting
    }
}
