import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.exclude

/**
 * Created By Rafiqul Hasan
 */
object Module{
    val app =":app"
    val core = ":appcore:core"
    val testUtils = ":appcore:testutils"
    val styles = ":appcore:styles"
    val preference = ":appcore:preference"
    val weatherinfo = ":features:weatherinfo"
    val citySearch = ":features:citysearch"
    val setting = ":features:settings"
}
/**
 * To define plugins
 */
object BuildPlugins {
    val androidBuildTools by lazy { "com.android.tools.build:gradle:${Versions.gradlePlugin}" }
    val kotlinPlugins by lazy { "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}" }
    val hiltPlugin by lazy { "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}" }
    val navigationSafeArg by lazy { "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}" }
}

/**
 * To define dependencies
 */
object KotlinDependencies {
    val kotlinStd by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}" }
    val coreKtx by lazy { "androidx.core:core-ktx:${Versions.coreKtx}" }
}

object AndroidXSupportDependencies {
    val appCompat by lazy { "androidx.appcompat:appcompat:${Versions.appCompat}" }
    val constraintLayout by lazy { "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}" }
    val fragmentKtx by lazy { "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}" }
    val navigationFragmentKtx by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}" }
    val navigationUIKtx by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navigation}" }
    val lifecycleRuntimeKTX by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKTX}" }
    val swipeRefresh by lazy{"androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"}
    val pagingRuntime by lazy { "androidx.paging:paging-runtime-ktx:${Versions.paging}" }
    val pagingRxJavaSupport by lazy { "androidx.paging:paging-rxjava3:${Versions.paging}" }
    val datastorePreference by lazy { "androidx.datastore:datastore-preferences:${Versions.datastorePreference}" }

}

object MaterialDesignDependencies {
    val materialDesign by lazy { "com.google.android.material:material:${Versions.material}" }
}

object TestingDependencies {
    val junit by lazy { "junit:junit:${Versions.jUnit}" }
    val androidExtJunit by lazy { "androidx.test.ext:junit:${Versions.extJunit}" }
    val androidTestRunner by lazy { "androidx.test:runner:${Versions.androidxTestRunner}" }
    val androidTestRule by lazy { "androidx.test:rules:${Versions.androidXTestRule}" }
    val mockWebServer by lazy { "com.squareup.okhttp3:mockwebserver:${Versions.mockWebServer}" }
    val mockitoKotlin by lazy { "org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlin}" }
    val mockitoInline by lazy { "org.mockito:mockito-inline:${Versions.mockitoInline}" }
    val mockitoAndroid by lazy { "org.mockito:mockito-android:${Versions.mockito}" }
    val assertj by lazy { "org.assertj:assertj-core:${Versions.assertj}" }
    val androidArchCoreTesting by lazy { "androidx.arch.core:core-testing:${Versions.androidArchCore}" }
    val fragmentTesting by lazy { "androidx.fragment:fragment-testing:${Versions.fragmentKtx}" }
    val navigationTesting by lazy { "androidx.navigation:navigation-testing:${Versions.navigationTesting}" }

    val androidEspressoCore by lazy { "androidx.test.espresso:espresso-core:${Versions.espresso}" }
    val espressoContrib by lazy { "androidx.test.espresso:espresso-contrib:${Versions.espresso}" }
    val espressoIntent by lazy { "androidx.test.espresso:espresso-intents:${Versions.espresso}" }
    val espressoConcurrent by lazy { "androidx.test.espresso.idling:idling-concurrent:${Versions.espresso}" }
    val espressoIdling by lazy { "androidx.test.espresso:espresso-idling-resource:${Versions.espresso}" }
    val coroutineTest by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutine}" }
    val hamcrest by lazy{ "org.hamcrest:hamcrest:${Versions.hamcrestVersion}"}
}

object Libraries {
    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltAnnotationProcessor by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }
    val hiltNavigation by lazy { "androidx.hilt:hilt-navigation-fragment:${Versions.hiltNavigation}" }

    //For local unit tests
    val hiltUnitTest by lazy { "com.google.dagger:hilt-android-testing:${Versions.hilt}" }
    val hiltUnitTestAnnotationProcessor by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }

    //For instrumentation tests
    val hiltInstrumentation by lazy { "com.google.dagger:hilt-android-testing:${Versions.hilt}" }
    val hiltInsAnnotationProcessor by lazy { "com.google.dagger:hilt-compiler:${Versions.hilt}" }

    val coroutine by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutine}" }
    val coroutineAndroid by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}" }

    val location by lazy { "com.google.android.gms:play-services-location:${Versions.location}" }


    //logging interceptor
    val loggingInterceptor by lazy { "com.github.ihsanbal:LoggingInterceptor:${Versions.loggingInterceptor}" }

    //network interceptor
    val stetho by lazy { "com.facebook.stetho:stetho:${Versions.stetho}" }
    val stethoOkhttp by lazy { "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}" }
    val stethoJSRhino by lazy { "com.facebook.stetho:stetho-js-rhino:${Versions.stetho}" }

    //moshi
    val moshi by lazy { "com.squareup.moshi:moshi:${Versions.moshi}" }
    val moshiKotlinCodeGen by lazy { "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}" }

    val gson by lazy { "com.google.code.gson:gson:${Versions.gson}" }

    //retrofit
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitGsonConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
    val retrofitMoshiConverter by lazy { "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}" }
    val retrofitRxAdapter by lazy { "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit}" }

    val sdp by lazy { "com.intuit.sdp:sdp-android:${Versions.sdpssp}" }
    val ssp by lazy { "com.intuit.ssp:ssp-android:${Versions.sdpssp}" }

    val lottie by lazy { "com.airbnb.android:lottie:${Versions.lottie}" }

    val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }
    val glideKapt by lazy { "com.github.bumptech.glide:compiler:${Versions.glide}" }
}

fun DependencyHandlerScope.HiltDependency(){
    "implementation"(Libraries.hilt)
    "kapt"(Libraries.hiltAnnotationProcessor)
}

fun DependencyHandlerScope.GlideDependency(){
    "implementation"(Libraries.glide)
    "kapt"(Libraries.glideKapt)
}

fun DependencyHandlerScope.NetworkDependency(){
    //network
    "implementation"(Libraries.retrofit)
    "implementation"(Libraries.retrofitMoshiConverter)
    "implementation"(Libraries.retrofitRxAdapter)

    //logging interceptor
    "implementation"(Libraries.loggingInterceptor) {
       exclude("org.json", "json")
    }

    "implementation"(Libraries.moshi)
    "kapt"(Libraries.moshiKotlinCodeGen)

    //network monitoring tool
    "implementation" (Libraries.stetho)
    "implementation" (Libraries.stethoOkhttp)
    "implementation" (Libraries.stethoJSRhino)
}

fun DependencyHandlerScope.StethoDependency(){
    //network monitoring tool
    "implementation" (Libraries.stetho)
    "implementation" (Libraries.stethoOkhttp)
    "implementation" (Libraries.stethoJSRhino)
}

fun DependencyHandlerScope.CommonDependency(){
    //kotlin
    "implementation"(KotlinDependencies.kotlinStd)
    "implementation"(KotlinDependencies.coreKtx)

    //Androidx
    "implementation"(AndroidXSupportDependencies.appCompat)
    "implementation"(AndroidXSupportDependencies.constraintLayout)
    "implementation"(AndroidXSupportDependencies.fragmentKtx)
    "implementation"(AndroidXSupportDependencies.lifecycleRuntimeKTX)

    //navigation
    "implementation"(AndroidXSupportDependencies.navigationFragmentKtx)
    "implementation"(AndroidXSupportDependencies.navigationUIKtx)

    //Material Design
    "implementation"(MaterialDesignDependencies.materialDesign)

    //hilt
    "implementation"(Libraries.hilt)
    "kapt"(Libraries.hiltAnnotationProcessor)
    "implementation"(Libraries.hiltNavigation)

    //coroutine
    "implementation"(Libraries.coroutineAndroid)

    //hilt For local unit tests
    "testImplementation"(Libraries.hiltUnitTest)
    "kaptTest"(Libraries.hiltUnitTestAnnotationProcessor)

    //Hilt For instrumentation tests
    "androidTestImplementation"(Libraries.hiltInstrumentation)
    "kaptAndroidTest"(Libraries.hiltInsAnnotationProcessor)
    
    //for sp-dp
    "implementation"(Libraries.sdp)
    "implementation"(Libraries.ssp)

    //testing
    "testImplementation"(TestingDependencies.junit)
    "testImplementation"(TestingDependencies.assertj)
    "testImplementation"(TestingDependencies.androidArchCoreTesting)
    "testImplementation"(TestingDependencies.coroutineTest)
    "testImplementation" (TestingDependencies.mockitoKotlin)
    "testImplementation" (TestingDependencies.mockitoInline)
    "testImplementation"(TestingDependencies.mockWebServer)
    "androidTestImplementation"(TestingDependencies.androidArchCoreTesting)
    "debugImplementation" (TestingDependencies.fragmentTesting)
    "androidTestImplementation"(TestingDependencies.androidExtJunit)
    "androidTestImplementation"(TestingDependencies.androidEspressoCore)
    "androidTestImplementation"(TestingDependencies.mockitoAndroid)
    "androidTestImplementation"(TestingDependencies.androidTestRunner)
    "androidTestImplementation"(TestingDependencies.androidTestRule)
    "androidTestImplementation" (TestingDependencies.navigationTesting)
    "androidTestImplementation" (TestingDependencies.espressoContrib)
    "androidTestImplementation" (TestingDependencies.espressoIntent)
    "androidTestImplementation" (TestingDependencies.espressoConcurrent)
    "implementation" (TestingDependencies.espressoIdling)
}