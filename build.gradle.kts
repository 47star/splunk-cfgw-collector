import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

buildscript {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    dependencies {
        classpath("com.donghoonyoo.boilerplate:gradle-kotlin-boilerplate:latest.release")
    }
}

plugins {
    kotlin("multiplatform") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
}

group = "com.donghoonyoo"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}


kotlin {
    mingwX64()

    linuxArm64()
    linuxX64()

    macosArm64()
    macosX64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlinx("coroutines-core", "1.7.3"))
                implementation(kotlinx("serialization-json", "1.6.0"))

                implementation(kotlinx("datetime", "0.4.1"))

                implementation(ktor("client-core", "2.3.4"))
                implementation(ktor("client-content-negotiation", "2.3.4"))
                implementation(ktor("serialization-kotlinx-json", "2.3.4"))
            }
        }

        val mingwX64Main by getting {
            dependencies {
                implementation(ktor("client-winhttp", "2.3.4"))
            }
        }

        val linuxArm64Main by getting {
            dependencies {
                implementation(ktor("client-cio", "2.3.4"))
            }
        }

        val linuxX64Main by getting {
            dependencies {
                implementation(ktor("client-cio", "2.3.4"))
            }
        }

        val macosArm64Main by getting {
            dependencies {
                implementation(ktor("client-darwin", "2.3.4"))
            }
        }

        val macosX64Main by getting {
            dependencies {
                implementation(ktor("client-darwin", "2.3.4"))
            }
        }
    }

    this.targets.filterIsInstance<KotlinNativeTarget>().forEach {
        it.binaries {
            executable {
                entryPoint = "${group}.splunk.cloudflare.gateway.analytics.http.main"
            }
        }
    }

    jvmToolchain(11)
}
