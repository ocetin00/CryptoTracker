plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.compose.compiler)
    id("jacoco")
    kotlin("plugin.serialization") version "2.0.20"
}

android {
    namespace = "com.oguzhan.cryptotracker"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.oguzhan.cryptotracker"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    packaging {
        resources {
            excludes.add("META-INF/AL2.0")
            excludes.add("META-INF/LGPL2.1")
            excludes.add("META-INF/LICENSE.md")
            excludes.add("META-INF/LICENSE-notice.md")
            excludes.add("META-INF/NOTICE")
            excludes.add("META-INF/LICENSE")
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }
}
tasks.register("testClasses")

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.materialWindow)
    implementation(libs.google.android.material)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.androidx.hilt.common)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.hilt.compiler)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    testImplementation(libs.junit)
    testImplementation(libs.truth)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //SplashScreen
    implementation(libs.androidx.core.splashscreen)

    //Constraint Layout
    implementation(libs.androidx.constraintlayout.compose)

    //Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    //Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    //Retrofit
    implementation(libs.square.retrofit2)
    implementation(libs.okhttp3)
    implementation(libs.gson)
    implementation(libs.converter.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.okhttp3.mockwebserver)


    //DataStore
    implementation(libs.datastore)

    //Hilt
    implementation(libs.hilt.android)
    kspAndroidTest(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)
    ksp(libs.hilt.compiler)



    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.hilt.android.testing)


    //Coil
    implementation(libs.coil.kt.compose)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.ui.auth)
    implementation(libs.firebase.firestore)

    //Mockk
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.core.testing)
    testImplementation(libs.turbine)

    //Robolectric
    testImplementation(libs.robolectric)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.accompanist.placeholder.material3)

    //Sandwich for network responses
    implementation(libs.sandwich)
    implementation(libs.sandwich.retrofit)

    //chucker
    debugImplementation(libs.chucker)

}