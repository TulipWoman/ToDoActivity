plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.todoactivity"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.todoactivity"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    //Section 3 - week 3
    implementation("androidx.room:room-runtime:2.8.4")
    annotationProcessor("androidx.room:room-complier:2.8.4")
}