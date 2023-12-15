plugins {
    kotlin("kapt")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id ("kotlin-parcelize")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.gq.basicm3"
    compileSdk = BuildVersion.CompileSdk

    defaultConfig {
        minSdk = BuildVersion.MinSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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

    /*flavorDimensions 'v'
productFlavors {
    vRelease {
        dimension 'v'
        versionNameSuffix ".${versionNameSuffixTime()}_release"
        buildConfigField "long", "BuildTimestamp", "-1"
        buildConfigField "boolean", "IsRelease", "true" // 是否是发布版
        buildConfigField "String", "HostName", "\"http://crm.app12345.cn/\""
        buildConfigField "String", "AppName", "\"\""
        manifestPlaceholders = [appName: ""]
    }

    vPreview {
        dimension 'v'
        versionNameSuffix ".${versionNameSuffixTime()}_preview"
        buildConfigField "long", "BuildTimestamp", "${releaseTime()}"
        buildConfigField "boolean", "IsRelease", "false" // 是否是发布版
        buildConfigField "String", "HostName", "\"http://crm.app12345.cn/\""
        buildConfigField "String", "AppName", "\"\""
        manifestPlaceholders = [appName: ""]
    }

    vDeveloper {
        dimension 'v'
        versionNameSuffix ".${versionNameSuffixTime()}_developer"
        buildConfigField "long", "BuildTimestamp", "${releaseTime()}"
        buildConfigField "boolean", "IsRelease", "false" // 是否是发布版
        buildConfigField "String", "HostName", "\"http://192.168.3.113:8081/\""
        buildConfigField "String", "AppName", "\"\""
        manifestPlaceholders = [appName: ""]
    }
}*/

    /* applicationVariants.all { variant ->
         variant.outputs.all { output -> //  ${variant.productFlavors[0].name}
             def fileName = "appname_${variant.versionCode}_${variant.versionName}.apk"
             def outFile = output.outputFile
             if (outFile != null && outFile.name.endsWith('.apk')) {
                 outputFileName = fileName
             }
         }
     }*/

    // build.gradle.kts
    /*applicationVariants.all {
        val variant = this
        variant.outputs
                .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                .forEach { output ->
                    this.productFlavors.forEach {
                        println("ProductFlavors-Name: ${it.name}")
                    }
                    println("Variant.baseName: ${variant.flavorName}")
                    val flavor = this.productFlavors.find { flavor -> flavor.name == variant.flavorName }
                    println("Find Flavor: ${flavor}")
                    val outputFileName = "${flavor?.manifestPlaceholders?.get("skinId")}.skin"
                    println("OutputFileName: $outputFileName")
                    output.outputFileName = outputFileName
                }
    }*/
}

kapt {
    correctErrorTypes = true
}

/*static long releaseTime() {
    return new Date().time
}

def static versionNameSuffixTime() {
    return new Date().format("MMddHHmm", TimeZone.getTimeZone("UTC"))
}*/

dependencies {

/*    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")*/

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
    implementation(Libs.ComposeRuntimeLiveData)
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
    implementation(Libs.LifecycleViewModelKtx)
    implementation(Libs.LifecycleViewModelSavedState)
    implementation(Libs.LifecycleViewModelCompose)
    implementation(Libs.LifecycleLiveDataKtx)
    implementation(Libs.LifecycleService)
    implementation(Libs.LifecycleCommonJava8)
    implementation(Libs.LifecycleProcess)
    implementation(Libs.LifecycleReactiveStreamsKtx)

    implementation(Libs.Retrofit2)
    implementation(Libs.Retrofit2ConverterGson)
    implementation(Libs.Retrofit2Okhttp3)

    implementation(Libs.Gson)

    implementation(Libs.CoilCompose)
    implementation(Libs.AccompanistSystemUIController)
    implementation(Libs.AccompanistSwipeRefresh)
    implementation(Libs.AccompanistPager)
    implementation(Libs.AccompanistPagerIndicators)
    implementation(Libs.AccompanistPagerIndicators)
    implementation(Libs.AccompanistPermissions)
    implementation(Libs.AccompanistPlaceholder)
    implementation(Libs.AccompanistWebView)

    implementation(Libs.DocumentFile)
    implementation(Libs.CommonsCodec)
    implementation(Libs.ProcessPhoenix)
    implementation(Libs.HiltAndroid)
    implementation(Libs.HiltNavigationCompose)
    implementation(Libs.Timber)

    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${BuildVersion.KotlinCompilerVersion}")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${BuildVersion.KotlinCompilerVersion}")
    implementation ("org.jetbrains.kotlin:kotlin-reflect:${BuildVersion.KotlinCompilerVersion}")

    kapt(Libs.KaptHiltCompiler)
    kapt(Libs.KaptDaggerHiltCompiler)
}