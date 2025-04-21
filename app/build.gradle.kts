plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.androidx.navigation.safe.args)
    kotlin("kapt")
}

android {
    namespace = "com.example.avito_internship_music_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.avito_internship_music_app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.serialization.json)
    implementation(libs.okhttp.logging)

    implementation(libs.glide)
    kapt(libs.glide.compiler)

    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

    implementation(libs.fragment.ktx)
    implementation(libs.recyclerview)

    implementation(libs.media)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}