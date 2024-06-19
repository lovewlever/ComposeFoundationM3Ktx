plugins {
    kotlin("jvm")
}

dependencies {

    implementation("com.google.devtools.ksp:symbol-processing-api:2.0.0-1.0.21")
    implementation("com.squareup:kotlinpoet:1.17.0")
    implementation("com.squareup:kotlinpoet-ksp:1.17.0")
    compileOnly(libs.retrofit2)
    compileOnly(libs.retrofit2.okhttp3)
    compileOnly("javax.inject:javax.inject:1")
}