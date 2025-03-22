plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("app.cash.paparazzi")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.task.cybersapiant"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.task.cybersapiant"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.activity:activity-compose:1.9.0")

    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.31.2-alpha")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material:material:1.7.8")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.8")
    debugImplementation("androidx.compose.ui:ui-tooling")
    androidTestImplementation("androidx.compose.ui:ui-test-manifest:1.7.8")
    // Navigation
    implementation("androidx.navigation:navigation-compose:2.8.0-beta04")
    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.49")
    kapt("com.google.dagger:hilt-compiler:2.49")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Composere Orderable
    implementation("org.burnoutcrew.composereorderable:reorderable:0.9.6")

    // Data store
    implementation("androidx.datastore:datastore-preferences:1.1.3")

    // Jetpack Compose Testing
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.8")
    androidTestImplementation("androidx.compose.ui:ui-test-manifest:1.7.8")
    // UI Testing libraries
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.3")
    testImplementation ("io.mockk:mockk:1.12.3")
    androidTestImplementation ("io.mockk:mockk-android:1.12.3")

    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.7.8")
    androidTestImplementation ("androidx.test.ext:junit:1.2.1")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.6.1")
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.7.8")


    // Paparazzi for screenshot testing
    androidTestImplementation("app.cash.paparazzi:paparazzi:1.3.1")

    // Robolectric for Android tests
    testImplementation("org.robolectric:robolectric:4.10.3")

    // Compose UI testing
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.8")
}
