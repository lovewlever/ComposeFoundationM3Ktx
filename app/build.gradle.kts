plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.gq.foundation"
    compileSdk = BuildVersion.CompileSdk

    defaultConfig {
        applicationId = "com.gq.foundation"
        minSdk = BuildVersion.MinSdk
        targetSdk = BuildVersion.TargetSdk
        versionCode = BuildVersion.VersionCode
        versionName = BuildVersion.VersionName

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
        sourceCompatibility = BuildVersion.SourceTargetCompatibility
        targetCompatibility = BuildVersion.SourceTargetCompatibility
    }
    kotlinOptions {
        jvmTarget = BuildVersion.JvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = BuildVersion.KotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

/*  testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    debugImplementation("androidx.compose.ui:ui-test-manifest")*/

    implementation(project(":basic_m3"))

    implementation(Libs.AndroidXCoreKtx)

    val composeBom = platform(Libs.ComposeBom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(Libs.ComposeMD3)
    implementation(Libs.ComposeMDIconsCore)
    implementation(Libs.ComposeMDIconsExtended)
    implementation(Libs.ComposeMDWinSizeUtil)
    implementation(Libs.ComposeUi)
    implementation(Libs.ComposeUiGraphics)
    implementation(Libs.ComposeActivity)
    implementation(Libs.LifecycleViewModelCompose)
    implementation(Libs.ComposePreview)
    debugImplementation(Libs.DebugComposePreview)
    androidTestImplementation(Libs.AndroidTestComposeUI)
    debugImplementation(Libs.DebugComposeTest)

    implementation(Libs.Paging3RuntimeKtx)
    implementation(Libs.Paging3Compose)

    implementation(Libs.DataStoreCPreferencesCore)
    implementation(Libs.DataStoreCPreferences)

    implementation(Libs.NavigationCompose)

    implementation(Libs.LifecycleRuntimeKtx)

    implementation(Libs.Retrofit2)
    implementation(Libs.Retrofit2ConverterGson)
    implementation(Libs.Retrofit2Okhttp3)

    implementation(Libs.Gson)

    implementation(Libs.CoilCompose)
    implementation(Libs.AccompanistSystemUIController)
    implementation(Libs.AccompanistPager)
    implementation(Libs.AccompanistPagerIndicators)
    implementation(Libs.AccompanistPagerIndicators)
    implementation(Libs.AccompanistPermissions)
    implementation(Libs.AccompanistPlaceholder)
    implementation(Libs.AccompanistWebView)
    
    implementation(Libs.CameraXCore)
    implementation(Libs.CameraX2)

    implementation(Libs.DocumentFile)
    implementation(Libs.CommonsCodec)
    implementation(Libs.ProcessPhoenix)
    implementation(Libs.HiltAndroid)
    implementation(Libs.HiltNavigationCompose)
    implementation(Libs.Timber)



    kapt(Libs.KaptHiltCompiler)
    kapt(Libs.KaptDaggerHiltCompiler)
}