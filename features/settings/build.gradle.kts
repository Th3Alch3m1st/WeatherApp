plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")
	id("kotlin-android")
	id("kotlin-parcelize")
	id("androidx.navigation.safeargs.kotlin")
	id("dagger.hilt.android.plugin")
	id("kotlin-kapt")
}

android {
	namespace =  BuildConfig.setting
	compileSdk = BuildConfig.compileSdkVersion

	defaultConfig {
		minSdk = BuildConfig.minSdkVersion
		targetSdk = BuildConfig.targetSdkVersion

		testInstrumentationRunner = BuildConfig.testRunnerPackage
		consumerProguardFiles("consumer-rules.pro")
	}

	buildFeatures {
		dataBinding = true
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
}

dependencies {
	implementation(project(Module.styles))
	implementation(project(Module.core))
	implementation(project(Module.preference))

	implementation(AndroidXSupportDependencies.datastorePreference)
	CommonDependency()

	testImplementation(project(Module.testUtils))
	androidTestImplementation(project(Module.testUtils))
}