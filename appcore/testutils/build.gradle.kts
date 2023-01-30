plugins {
	id("com.android.library")
	id("org.jetbrains.kotlin.android")

	id("dagger.hilt.android.plugin")
	id("kotlin-kapt")
}

android {
	namespace = BuildConfig.testutils
	compileSdk = BuildConfig.compileSdkVersion

	defaultConfig {
		minSdk = BuildConfig.minSdkVersion
		targetSdk = BuildConfig.targetSdkVersion

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

	implementation(AndroidXSupportDependencies.appCompat)

	//hilt
	implementation(Libraries.hilt)
	kapt(Libraries.hiltAnnotationProcessor)
	implementation(Libraries.hiltNavigation)

	//coroutine
	implementation(Libraries.coroutineAndroid)

	//hilt For local unit tests
	implementation(Libraries.hiltUnitTest)
	kapt(Libraries.hiltUnitTestAnnotationProcessor)

	//Hilt For instrumentation tests
	implementation(Libraries.hiltInstrumentation)
	kapt(Libraries.hiltInsAnnotationProcessor)


	implementation(TestingDependencies.androidEspressoCore)

	implementation(TestingDependencies.junit)
	implementation(TestingDependencies.androidExtJunit)
	implementation(TestingDependencies.androidTestRunner)
	implementation(TestingDependencies.assertj)
	implementation(TestingDependencies.mockitoKotlin)
	implementation(TestingDependencies.coroutineTest)
}