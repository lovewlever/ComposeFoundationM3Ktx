plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.ksp)
    id ("kotlin-parcelize")
}

android {
    namespace = "com.gq.foundation"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.gq.foundation"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

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
        jvmTarget = libs.versions.jvmTarget.get()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompilerExtensionVersion.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":basic_m3"))

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
    implementation(Libs.AccompanistPagerIndicators)
    implementation(Libs.AccompanistPermissions)

    implementation(libs.document.file)
    implementation(libs.commons.codec)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    implementation(Libs.ProcessPhoenix)
    implementation(Libs.BlankJUtilCodex)
    implementation(libs.timber)

    implementation(libs.kotlin.stdlib.jdk8)
    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.kotlin.stdlib.reflect)


    ksp(libs.kapt.hilt.dagger.compiler)
    ksp(libs.kapt.hilt.compiler)
    ksp(libs.kapt.lifecycle.compiler)

    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":basic-retrofit-compiler"))
    ksp(project(":basic-retrofit-compiler"))
}