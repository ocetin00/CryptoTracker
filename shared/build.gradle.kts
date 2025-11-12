import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication) // Changed from androidApplication
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidx.room)
}

kotlin {


    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    val xcfName = "sharedKit"

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcfName
            isStatic = true
        }
    }


    /**
     *
     *
     * rm -rf Pods Podfile.lock
     * cd iosApp
     * pod deintegrate
     * pod repo update
     * pod install
     *
     */


    sourceSets {

        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)

                //Sandwich for network responses
                implementation(libs.sandwich)
                implementation(libs.sandwich.ktor)

                implementation("dev.gitlive:firebase-auth:2.4.0")
                implementation("dev.gitlive:firebase-firestore:2.4.0")

                implementation(libs.androidx.room.runtime)
                implementation(libs.androidx.sqlite.bundled)


                //koin
                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.koin.compose.viewmodel.navigation)

                implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.1")

                //ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.negotiation)
                implementation(libs.ktor.serialization.json)
                implementation(libs.ktor.xml)
                implementation(libs.ktor.logging)
                implementation(libs.kotlinx.serialization.json)

                //coil
                implementation(libs.coil.kt.compose)
                implementation(libs.coil.network.okhttp)

                implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")

            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.koin.android)
                //Firebase
                //implementation(project.dependencies.platform(libs.firebase.bom))
            }
        }

        val iosMain by creating {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }


        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspIosSimulatorArm64", libs.androidx.room.compiler)
    add("kspIosArm64", libs.androidx.room.compiler)
    // Add any other platform target you use in your project, for example kspDesktop
}

compose.experimental {}
room {
    schemaDirectory("$projectDir/schemas")
}
android {
    namespace = "com.oguzhan.cryptotracker"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}


