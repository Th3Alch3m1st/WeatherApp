dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}
rootProject.name = "WeatherApp"
include (":app")
include (":appcore:core")
include (":appcore:styles")
include(":appcore:preference")
include(":appcore:testutils")

include(":features:weatherinfo")
include(":features:settings")
include(":features:citysearch")
