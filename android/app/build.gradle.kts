plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.tranzo.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.tranzo.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 2
        versionName = "1.0.1"

        buildConfigField("String", "BASE_URL", "\"https://tranzomoney-production.up.railway.app\"")
        buildConfigField("String", "WEBAUTHN_RP_ID", "\"tranzo.app\"")
        buildConfigField("String", "WEBAUTHN_ORIGIN", "\"https://tranzo.app\"")
        buildConfigField("String", "GOOGLE_CLIENT_ID", "\"753093250645-mjghuinh2qrjfi5mrlqtjq3kei4ecqbn.apps.googleusercontent.com\"")
    }

    signingConfigs {
        create("debug") {
            storeFile = file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("String", "BASE_URL", "\"https://tranzomoney-production.up.railway.app\"")
            buildConfigField("String", "WEBAUTHN_RP_ID", "\"tranzo.app\"")
            buildConfigField("String", "WEBAUTHN_ORIGIN", "\"https://tranzo.app\"")
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
        buildConfig = true
    }
}

dependencies {
    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.lifecycle.runtime)

    // Navigation
    implementation(libs.navigation.compose)

    // Lifecycle
    implementation(libs.lifecycle.viewmodel)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation)

    // Retrofit & OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Core
    implementation(libs.core.ktx)
    implementation(libs.core.splashscreen)

    // DataStore
    implementation(libs.datastore)

    // Biometric
    implementation(libs.biometric)

    // Images
    implementation(libs.coil)

    // QR Code
    implementation(libs.zxing)

    // Lottie
    implementation(libs.lottie)

    // Google Auth & Credential Management
    implementation(libs.google.auth)
    implementation(libs.credential.manager)
    implementation("com.google.android.libraries.identity.googleid:googleid:1.0.0")

    // WebAuthn / Passkey
    implementation("com.google.android.gms:play-services-fido:20.1.0")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Jetpack Security
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // JSON
    implementation("com.google.code.gson:gson:2.10.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    // Crypto & Web3
    implementation("org.web3j:core:4.9.7")
    implementation("io.jsonwebtoken:jjwt-api:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.3")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.3")

    // ZeroDev SDK

    // Accompanist
    implementation("com.google.accompanist:accompanist-permissions:0.36.0")
}
