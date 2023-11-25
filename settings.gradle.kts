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
    }
}

rootProject.name = "MyHealthPal"
include(":app")
//need to try adding library here
//include(":unityLibrary")
//project(":unityLibrary").projectDir = new File("..\\UnityProject\\androidBuild\\unityLibrary")