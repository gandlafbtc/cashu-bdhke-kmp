plugins {
    id("maven-publish")
    kotlin("multiplatform") version "1.8.0"
}

group = "com.gandlaf"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        jvmToolchain(8)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("fr.acinq.secp256k1:secp256k1-kmp:0.7.1")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test")) // This brings all the platform dependencies automatically
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("fr.acinq.secp256k1:secp256k1-kmp-jni-jvm:0.7.1")
            }
        }

        val jvmTest by getting

    }
}
