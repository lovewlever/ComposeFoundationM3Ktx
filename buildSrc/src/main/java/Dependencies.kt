import org.gradle.api.JavaVersion
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object ProductFlavors {
    fun versionNameSuffixTime(): String = SimpleDateFormat("MMddHHmm", Locale.CANADA).format(Date())

    fun packageTime(): Long = System.currentTimeMillis()
}

object BuildVersion {
    const val CompileSdk = 34
    const val MinSdk = 24
    const val TargetSdk = 34
    const val VersionCode = 1
    const val VersionName = "1.0.0"

    val SourceTargetCompatibility = JavaVersion.VERSION_17
    const val JvmTarget = "17"
    const val KotlinCompilerExtensionVersion = "1.5.1"
    const val KotlinCompilerVersion = "1.9.0"
}

object Libs {

    private const val appcompatVersion = "1.6.1"
    private const val lifecycleVersion = "2.6.2"
    private const val pagingVersion = "3.2.1"
    private const val dataStoreVersion = "1.0.0"
    private const val roomVersion = "2.6.1"
    private const val cameraxVersion = "1.3.0"
    private const val accompanistVersion = "0.32.0"
    const val hiltPluginVersion = "2.48"
    private const val hiltComposeVersion = "1.1.0"


    const val AndroidXAppcompat = "androidx.appcompat:appcompat:$appcompatVersion"
    const val AndroidXAppcompatResources = "androidx.appcompat:appcompat-resources:$appcompatVersion"
    const val AndroidXCoreKtx = "androidx.core:core-ktx:1.12.0"
    const val AndroidXBrowser = "'androidx.browser:browser:1.7.0"

    //    def composeBom = platform('androidx.compose:compose-bom:2023.01.00')
    //    implementation composeBom
    //    androidTestImplementation composeBom
    const val ComposeBom = "androidx.compose:compose-bom:2023.01.00"
    // Compose
    const val ComposeMD3 = "androidx.compose.material3:material3"
    const val ComposeMDIconsCore = "androidx.compose.material:material-icons-core"
    const val ComposeMDIconsExtended = "androidx.compose.material:material-icons-extended"
    const val ComposeMDWinSizeUtil = "androidx.compose.material3:material3-window-size-class"
    const val ComposeRuntimeLiveData = "androidx.compose.runtime:runtime-livedata"
    const val ComposeUi = "androidx.compose.ui:ui"
    const val ComposeUiGraphics = "androidx.compose.ui:ui-graphics"
    const val ComposeActivity = "androidx.activity:activity-compose:1.8.1"


    // Compose-Preview
    const val ComposePreview = "androidx.compose.ui:ui-tooling-preview"
    const val DebugComposePreview = "androidx.compose.ui:ui-tooling"
    // Compose-UI Test
    const val AndroidTestComposeUI = "androidx.compose.ui:ui-test-junit4"
    const val DebugComposeTest = "androidx.compose.ui:ui-test-manifest"

    // Paging3
    const val Paging3RuntimeKtx = "androidx.paging:paging-runtime-ktx:${pagingVersion}"
    const val Paging3Compose = "androidx.paging:paging-compose:${pagingVersion}"

    // DataStore
    const val DataStoreCPreferencesCore = "androidx.datastore:datastore-preferences-core:${dataStoreVersion}"
    const val DataStoreCPreferences = "androidx.datastore:datastore-preferences:${dataStoreVersion}"

    // Room
    const val RoomRuntime = "androidx.room:room-runtime:$roomVersion"
    const val RoomKtx = "androidx.room:room-ktx:$roomVersion"

    // Navigation
    const val NavigationCompose = "androidx.navigation:navigation-compose:2.5.3"

    // lifecycle-runtime-ktx
    const val LifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
    const val LifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    const val LifecycleViewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycleVersion"
    const val LifecycleViewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycleVersion"
    const val LifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    const val LifecycleService = "androidx.lifecycle:lifecycle-service:$lifecycleVersion"
    const val LifecycleCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion"
    const val LifecycleProcess = "androidx.lifecycle:lifecycle-process:$lifecycleVersion"
    const val LifecycleReactiveStreamsKtx = "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycleVersion"

    // Timber
    const val Timber = "com.jakewharton.timber:timber:5.0.1"

    // Retrofit2/Okhttp3
    const val Retrofit2 = "com.squareup.retrofit2:retrofit:2.9.0"
    const val Retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val Retrofit2Okhttp3 = "com.squareup.okhttp3:okhttp:4.10.0"

    // Gson
    const val Gson = "com.google.code.gson:gson:2.10.1"

    // Coil
    const val CoilCompose = "io.coil-kt:coil-compose:2.0.0-rc02"
    const val CoilVideo =  "io.coil-kt:coil-video:2.0.0-rc02"

    // CameraX
    // The following line is optional, as the core library is included indirectly by camera-camera2
    const val CameraXCore = "androidx.camera:camera-core:${cameraxVersion}"
    const val CameraX2 = "androidx.camera:camera-camera2:${cameraxVersion}"
    // If you want to additionally use the CameraX Lifecycle library
    const val CameraXLifecycle = "androidx.camera:camera-lifecycle:${cameraxVersion}"
    // If you want to additionally use the CameraX VideoCapture library
    const val CameraXVideo = "androidx.camera:camera-video:${cameraxVersion}"
    // If you want to additionally use the CameraX View class
    const val CameraXView = "androidx.camera:camera-view:${cameraxVersion}"
    // If you want to additionally use the CameraX Extensions library
    const val CameraXExtensions = "androidx.camera:camera-extensions:${cameraxVersion}"

    // Google accompanist
    @Deprecated("如果您使用 SystemUIController 在 Activity 中进行边到边切换并更改系统栏颜色和系统栏图标颜色，请使用 androidx.activity 1.8.0-alpha03 及更高版本中提供的新 Activity.enableEdgeToEdge 方法")
    const val AccompanistSystemUIController = "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion"
    @Deprecated("Deprecated & Removed androidx.compose.foundation 中提供官方插图支持")
    const val AccompanistInsets = "com.google.accompanist:accompanist-insets:$accompanistVersion"
    @Deprecated("在 Compose Material 1.3.0 中，伴奏者 SwipeRefresh 已被 PullRefresh 取代。实现是类似的，但它不是可组合函数，而是可以应用于可组合函数的修饰符")
    const val AccompanistSwipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$accompanistVersion"
    const val AccompanistPager = "com.google.accompanist:accompanist-pager:$accompanistVersion"
    const val AccompanistPagerIndicators = "com.google.accompanist:accompanist-pager-indicators:$accompanistVersion"
    @Deprecated("将导入包替换为指向 Androidx.Compose")
    const val AccompanistFlowLayout = "com.google.accompanist:accompanist-flowlayout:$accompanistVersion"
    @Deprecated("此库已弃用，androidx.navigation.compose 中提供了官方 navigation-compose 支持")
    const val AccompanistNavigationAnim = "com.google.accompanist:accompanist-navigation-animation:$accompanistVersion"
    const val AccompanistPermissions = "com.google.accompanist:accompanist-permissions:$accompanistVersion"
    const val AccompanistPlaceholder = "com.google.accompanist:accompanist-placeholder:$accompanistVersion"
    const val AccompanistWebView = "com.google.accompanist:accompanist-webview:$accompanistVersion"

    // Bugly
    const val BuglyCrashReport = "com.tencent.bugly:crashreport:latest.release"
    const val BuglyNativeCrashReport = "com.tencent.bugly:nativecrashreport:latest.release"

    // Others
    const val ProcessPhoenix = "com.jakewharton:process-phoenix:2.1.2"
    const val DocumentFile = "androidx.documentfile:documentfile:1.0.1"
    const val CommonsCodec = "commons-codec:commons-codec:1.15"
    const val StartUpRuntime = "androidx.startup:startup-runtime:1.1.1"
    const val HiltAndroid = "com.google.dagger:hilt-android:$hiltPluginVersion"
    const val HiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:$hiltComposeVersion"
    // https://github.com/Blankj/AndroidUtilCode/tree/master?tab=readme-ov-file
    const val BlankJUtilCodex = "com.blankj:utilcodex:1.31.1"

    // - - - -
    // Kapt
    const val KaptRoomCompiler = "androidx.room:room-compiler:$roomVersion"
    const val KaptDaggerHiltCompiler = "com.google.dagger:hilt-compiler:$hiltPluginVersion"
    const val KaptHiltCompiler = "androidx.hilt:hilt-compiler:$hiltComposeVersion"
}