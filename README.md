# T_Assignment

Repository for assignment provided by company as part of process.
## Setup
**Requirement**
  - JDK 8
  - Latest Android SDK tools
  - Latest Android platform tools
  - Android SDK 28
  - Android Support Repository
  
  **Dependancy**
  - Retrofit : HTTP library to make network calls.
  - Glide : Image downloading and caching library for Android.

## Architecture
**MVVM**

MVVM is an architectural pattern that was created to simplify user interface programming. Google appears to be encouraging the use of MVVM for data binding. In fact, the Architecture Components of its Data Binding Library are modeled on the MVVM pattern.

The main components of MVVM are the Model, View and ViewModel, and its structure essentially supports two-way data binding between the latter two.

  -The View defines the user interface structure, layout and design and consists of views, layouts, scroll listeners and so on. It also notifies the ViewModel about different actions.
  -The ViewModel serves as the intermediary between the View and the Model. It provides data to the View via bindings and handles View logic. It calls methods on the Model, provides the Model’s data to the View and notifies the View of updates.
  -The Model is the data domain model and the source of application logic and rules. It provides data to the ViewModel and can update the ViewModel using notification mechanisms such as data access objects, models, repositories and gateways.

![## Setup](https://cdn2.phunware.com/wp-content/uploads/2017/12/blog-android-data-binding-chart.png)



## Screenshots

![## Setup](https://github.com/pravinkakde003/T_Assignment/blob/Develope/Screens/ScreenShots.png)
