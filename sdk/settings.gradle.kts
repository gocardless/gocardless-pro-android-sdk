pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://aa-sdk.s3-eu-west-1.amazonaws.com/android_repo") }
    }
}

rootProject.name = "MVVMApp"
include(":app")
include(":GoCardlessSDK")
