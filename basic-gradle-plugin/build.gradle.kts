plugins {
    id("java-gradle-plugin")
    id ("java")
    id ("org.jetbrains.kotlin.jvm")
    id ("kotlin")
    id("maven-publish")
}

dependencies {
    implementation("com.android.tools.build:gradle:7.4.2")
    implementation("com.android.tools.build:gradle-api:7.4.2")
    implementation("org.ow2.asm:asm:9.7")
    implementation("org.ow2.asm:asm-util:9.7")
    implementation("org.ow2.asm:asm-commons:9.7")
}

gradlePlugin {
    plugins {
        create("basicGradlePlugin") {
            group = "com.gq.basic"
            id = "basic-gradle-plugin"
            implementationClass = "com.gq.basic.gradle.plugin.RetrofitInitPlugin"
            version = "0.0.1"
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri("../basic_plugin_repo") //本地maven地址
        }
    }
}