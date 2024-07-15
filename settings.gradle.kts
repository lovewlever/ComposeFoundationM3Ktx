pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("basic_plugin_repo") //本地maven地址
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("basic_plugin_repo") //本地maven地址
        }
    }
}

rootProject.name = "ComposeFoundationM3Ktx"
include(":app")
include(":basic_m3")
include(":basic-retrofit-compiler")
include(":basic-gradle-plugin")
