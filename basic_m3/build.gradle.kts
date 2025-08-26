plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
    id ("kotlin-parcelize")
}

android {
    namespace = "com.gq.basicm3"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =  libs.versions.kotlinCompilerExtensionVersion.get()
    }

    /*    flavorDimensions += listOf("variants")
    productFlavors {
        create("vRelease") {
            dimension = "variants"
            versionNameSuffix = ".${versionNameSuffixTime()}_release"
            buildConfigField("boolean", "IS_RELEASE", "true")
            buildConfigField("long", "BUILD_TIMESTAMP", "-1")
        }
        create("vRC") {
            dimension = "variants"
            versionNameSuffix = ".${versionNameSuffixTime()}_rc"
            buildConfigField("boolean", "IS_RELEASE", "false")
            buildConfigField("long", "BUILD_TIMESTAMP", "${buildTimestamp()}")
        }
    }


    applicationVariants.all {
        val variant = this
        variant.outputs
                .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
                .forEach { output ->
                    val fileName = "PigEarTagScanner_${variant.versionCode}_${variant.versionName}.apk"
                    output.outputFileName = fileName
                }
    }*/
}

/*
fun buildTimestamp() = System.currentTimeMillis()
fun versionNameSuffixTime(): String = SimpleDateFormat("MMddHHmm", Locale.US).format(Date())
*/

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.appcompatResources)

    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.lifecycle)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.paging3.runtime.ktx)
    implementation(libs.paging3.compose)


    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.bundles.lifecycle)
    implementation(libs.bundles.camearX)

    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.retrofit2.okhttp3)

    implementation(libs.gson)

    implementation(libs.coil.compose)
    implementation(libs.accompanistPagerIndicators)
    implementation(libs.accompanistPermissions)

    implementation(libs.document.file)
    implementation(libs.commons.codec)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.process.phoenix)
    implementation(libs.blank.j.util.codex)
    implementation(libs.timber)

    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.kotlin.stdlib.reflect)


    ksp(libs.kapt.hilt.dagger.compiler)
    ksp(libs.kapt.hilt.compiler)
    ksp(libs.kapt.lifecycle.compiler)
}
