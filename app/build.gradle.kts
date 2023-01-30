plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    id("com.github.ben-manes.versions") version "0.44.0"
    id("kotlin-kapt")
}

android {
    namespace = BuildConfig.applicationID
    compileSdk = BuildConfig.compileSdkVersion

    defaultConfig {
        applicationId = BuildConfig.applicationID
        minSdk = BuildConfig.minSdkVersion
        targetSdk = BuildConfig.targetSdkVersion
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName

        testInstrumentationRunner = "com.epay.codingchallenge.utils.HiltAppTestRunner"
    }

    buildFeatures {
        dataBinding = true
    }

    signingConfigs {
        create("release") {
            storeFile = properties["RELEASE_STORE_FILE"]?.let { file(it) }
            storePassword = properties["RELEASE_STORE_PASSWORD"].toString()
            keyAlias = properties["RELEASE_KEY_ALIAS"].toString()
            keyPassword = properties["RELEASE_KEY_PASSWORD"].toString()
        }
    }

    buildTypes {

        named("release") {
            isMinifyEnabled = false
            isDebuggable = false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getAt("release")
        }

        named("debug") {
            isMinifyEnabled = false
            isDebuggable = true
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    packagingOptions {
        resources.excludes.add("META-INF/*")
        resources.excludes.add("META-INF/licenses/**")
        resources.excludes.add("**/attach_hotspot_windows.dll")
    }

    testOptions {
        animationsDisabled = true
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {

    implementation(project(Module.styles))
    implementation(project(Module.core))
    implementation(project(Module.weatherinfo))
    implementation(project(Module.preference))

    CommonDependency()
    StethoDependency()

    implementation (Libraries.lottie)
}