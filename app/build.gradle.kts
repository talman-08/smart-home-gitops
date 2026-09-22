import java.util.Properties

plugins {

    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}
val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")

    if (file.exists()) {
        file.inputStream().use {
            load(it)
        }
    }
}


android {
    namespace = "com.example.lab_1"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.lab_1"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
		
		    buildConfigField(
        "String",
        "GITHUB_TOKEN",
        "\"${localProperties.getProperty("GITHUB_TOKEN", "")}\""
    )
}
    

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
		buildConfig = true
    }
}

dependencies {

    // Existing Compose dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // -----------------------------
    // LAB 1 - NETWORKING
    // -----------------------------

    // Retrofit - communicate with GitHub API
    implementation("com.squareup.retrofit2:retrofit:2.11.0")

    // Gson - convert GitHub JSON into Kotlin objects
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Kotlin Coroutines - background work
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.11.0")

    // Tests
    testImplementation(libs.junit)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4")
}