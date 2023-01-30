plugins {
	id("com.android.library")
	id("kotlin-android")
	id("org.jetbrains.kotlin.android")
	id("dagger.hilt.android.plugin")
	id("kotlin-kapt")
}

android {
	namespace = BuildConfig.core
	compileSdk = BuildConfig.compileSdkVersion

	defaultConfig {
		minSdk = BuildConfig.minSdkVersion
		targetSdk = BuildConfig.targetSdkVersion

		consumerProguardFiles("consumer-rules.pro")
	}

	buildFeatures {
		dataBinding = true
	}

	buildTypes {
		getByName("debug"){
			buildConfigField("String", "AUTH_TOKEN", "\"78a07164952e030a671b9350f648cd70\"")
			buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")
			buildConfigField("String", "ICON_URL", "\"https://openweathermap.org/img/wn/\"")
		}
		getByName("release") {
			buildConfigField("String", "AUTH_TOKEN", "\"78a07164952e030a671b9350f648cd70\"")
			buildConfigField("String", "BASE_URL", "\"https://api.openweathermap.org/data/2.5/\"")
			buildConfigField("String", "ICON_URL", "\"https://openweathermap.org/img/wn/\"")
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

	implementation(KotlinDependencies.coreKtx)
	implementation(AndroidXSupportDependencies.appCompat)
	implementation(MaterialDesignDependencies.materialDesign)

	HiltDependency()
	NetworkDependency()

	GlideDependency()

	implementation(Libraries.sdp)
	implementation(Libraries.ssp)
}