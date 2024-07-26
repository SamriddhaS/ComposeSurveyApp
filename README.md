# ComposeSurveyApp

ComposeSurveyApp is a sample survey app, built with
[Jetpack Compose](https://developer.android.com/jetpack/compose). The goal of the sample is to
showcase text input, validation and state capabilities of Compose.

This is a clone from  [Official Jetpack Compose samples](https://github.com/android/compose-samples/tree/main) . You can find the original repo from 
[here](https://github.com/android/compose-samples/tree/main/Jetsurvey).

## Screenshots
<img src="screenshot/Screenshot 2024-07-26 at 11.25.44 PM.png" width="240" height="540"/> | <img src="screenshot/Screenshot 2024-07-26 at 11.24.15 PM.png" width="240" height="540"/>
<img src="screenshot/Screenshot 2024-07-26 at 11.24.34 PM.png" width="240" height="540"/>
<img src="screenshot/Screenshot 2024-07-26 at 11.24.45 PM.png" width="240" height="540"/>
<img src="screenshot/Screenshot 2024-07-26 at 11.25.00 PM.png" width="240" height="540"/>
<img src="screenshot/Screenshot 2024-07-26 at 11.25.16 PM.png" width="240" height="540"/>
<img src="screenshot/Screenshot 2024-07-26 at 11.25.25 PM.png" width="240" height="540"/>

## Features

This sample contains several screens: a welcome screen, where the user can enter their email, sign in and sign up screens and a survey screen. The app has light and dark themes.

### App scaffolding

Package [`app/src/main/java/com/example/composesurveyapp`][1]

[`MainActivity`][2] is the application's entry point. Each screen is implemented inside a `Fragment` and [`MainActivity`][2] is the host `Activity` for all of the `Fragment`s.
The navigation between them uses the [Navigation library][3]. The screens and the navigation are defined in [`Navigation.kt`][4]

[1]: app/src/main/java/com/example/composesurveyapp
[2]: app/src/main/java/com/example/composesurveyapp/MainActivity.kt
[3]: https://developer.android.com/guide/navigation
[4]: app/src/main/java/com/example/composesurveyapp/AppNavigations.kt

### Complete a survey

Package [`app/src/main/java/com/example/composesurveyapp`][6]

This screen allows the user to fill out a survey, showing how to handle complex state. UI state is kept and restored on recompositions triggered by different reasons like a configuration change or a new question being displayed on the screen.

See how to:

* Use `RadioButton`s - for single item selection
* Use `Checkbox`es - for multi-item selection
* Use `Slider` - for picking a value from a range
* Use `Scaffold` - for screens with top bar, bottom bar and body
* Display a `DialogFragment` when requested from compose

[6]: app/src/main/java/com/example/compose/jetsurvey/survey

### Data

The data in the sample is static, held in the `*Repository` classes.
