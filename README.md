# Coding Challenge

## About the application
On launch Application will ask for location permission. Location permission is needed to retrieve the current location and later based location lat,lon weather info is collected. If location permission is not given, the app will show an error screen, title and button. On click on button the app will navigate to the device app setting screen to enable location permission.

If Gps is not enabled the app will show an error screen, title and button. On click on button, user will be taken to offline city UI, where he can pick a city to see the weather info.

On Refresh, the app always fetches information using current location.

Users can select two themes light and dark, by default the app goes with system settings. If dark mode enabled the app will be dark mode otherwise light mode.

Users can also select two measurement units Metric and imperial, by default Metric units are used.

Users can navigate settings and city search from the toolbar option menu.

## Improvement area:
- Could have written it Jetpack compose, unfortunately I haven’t  learn UI test for jetpack compose and still learning Jetpack compose
- Could have Use room database for offline caching instead I used retrofit caching(Due to time constraints)
- Weather Api key could have store more securely with secrets gradle plugins
- Could have write more Unit/UI test to cover more scenario
- Could have write  kotlin dsl plugin to avoid redundant plugin import in module

## Some Trade-off:
Like most of the applications I also have trade off decisions in this application.
- I put the ISettingPreference use case into the preference module, I did it because in some cases apps may need SettingPreference info lke theme mode, Unit measurement in other modules. If I put ISettingPreference in the settings module, In that case I have to add settings feature module to that module. It better to have implement a utils module rather than feature module
- All test utils are put into testutils module and in gradle they were added as implementation rather than testImplementation. And later added it as testImplementation and androidTestImplementation in the feature module so test util could be shared in every feature module and it looks like a circular dependency. But it’s in the testing layer.
- Since app are using retrofit caching, it does not have any locale database layer, so I skip repository class. In some cases, I connected data layer directly to use cases


## Demo
<a href="url"><img src="https://github.com/Th3Alch3m1st/WeatherApp/blob/main/screenshots/app_demo.gif" height="480" width="230" />
  
## Screenshots
<a href="url"><img src="https://github.com/Th3Alch3m1st/WeatherApp/blob/main/screenshots/sceen_home.png" height="480" width="230" />
<a href="url"><img src="https://github.com/Th3Alch3m1st/WeatherApp/blob/main/screenshots/screen_home_dark.png" height="480" width="230" />
<a href="url"><img src="https://github.com/Th3Alch3m1st/WeatherApp/blob/main/screenshots/screen_search.png" height="480" width="230" />
<a href="url"><img src="https://github.com/Th3Alch3m1st/WeatherApp/blob/main/screenshots/screen_search_result.png" height="480" width="230" />
<a href="url"><img src="https://github.com/Th3Alch3m1st/WeatherApp/blob/main/screenshots/screen_setting_selection.png" height="480" width="230" />
<a href="url"><img src="https://github.com/Th3Alch3m1st/WeatherApp/blob/main/screenshots/screen_settings.png" height="480" width="230" />

## Architecture
MVVM  with clean Architecture and moduler approch
  
## Third-party libraries
- Architecture Components: Lifecycle, ViewModel, Navigation, Safe Args
- UI component: Material
- Data Binding
- Coroutine, Flow
- Dependency injector: Hilt
- Networking: Retrofit, Moshi
- Glide: Image loading
- Unit testing: JUnit4, AssertJ, MockitoKotlin, Espresso Arch core testing (InstantTaskExecutorRule), Kotlinx coroutines test
- UI testing: Espresso
- Full list: https://github.com/Th3Alch3m1st/WeatherApp/blob/main/buildSrc/src/main/java/Dependencies.kt

## Project Structure
  <a href="url"><img src="https://github.com/Th3Alch3m1st/WeatherApp/blob/main/screenshots/projectStructure.png" height="466" width="485" />
  <a href="url"><img src="https://github.com/Th3Alch3m1st/WeatherApp/blob/main/screenshots/project_sructure.png" width="440" />
    
## Application Data flow
  <a href="url"><img src="https://github.com/Th3Alch3m1st/WeatherApp/blob/main/screenshots/flow.png" height="241" width="818" />  
    
## Approach Explanation
- ViewModel separate UI and Data layer. ViewModel allows data to survive configuration changes and improve testabilities.
- Jetpack DataBinding to bind the layouts views and it's null safe.
- Use Kotlin DSL for gradle management - it helps better gradle management in multi module projects. And increase readability, provide code navigation and auto suggestions
- Write code maintaining SOLID principle
- Used mapper class to convert network response into UI model
- Wrote Unit test and UI test to ensure app stability and performance
- Wrote some infix function to increase unit test readability
- Add documentation in UI test to explain test scenario and write short comment for unit test

## Limitation
- When the App launches for the first time, if the device is in battery saver mode, app will not be able to get the current location even if GPS is enabled.
- Above Android SDk - 28, Some UI test in CitySearchFragment may fail, due to searchview components 
    
## Build tools
- Android Studio Electric Eel | 2022.1.1
- Gradle 7.4.0
