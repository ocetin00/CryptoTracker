import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
   // alias(libs.plugins.google.services)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary) // Changed from androidApplication
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.kotlinCocoapods)
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


   cocoapods {

        version = "1.0"
       summary = "Some description for a Kotlin/Native module"
       homepage = "Link to a Kotlin/Native module homepage"
       podfile = project.file("../CtIOS/Podfile")

       framework {
           // Required properties
           // Framework name configuration. Use this property instead of deprecated 'frameworkName'
           baseName = xcfName

           // Optional properties
           // Specify the framework linking type. It's dynamic by default.
           isStatic = true
           // Dependency export
           // Uncomment and specify another project module if you have one:
           // export(project(":<your other KMP module>"))
           transitiveExport = false // This is default.
       }

    }


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

                //Firebase
             //   implementation(project.dependencies.platform(libs.firebase.bom))
               // implementation("dev.gitlive:firebase-auth:2.1.0")
                //implementation("dev.gitlive:firebase-firestore:2.1.0")

                implementation(libs.androidx.room.runtime)
                implementation(libs.androidx.sqlite.bundled)

                //koin
                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.koin.compose)
                implementation(libs.koin.compose.viewmodel)
                implementation(libs.koin.compose.viewmodel.navigation)

                //ktor
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.negotiation)
                implementation(libs.ktor.xml)
                implementation(libs.ktor.logging)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }

        val iosMain by creating {
            dependsOn(commonMain)
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

compose.experimental {}

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

room {
    schemaDirectory("$projectDir/schemas")
}
