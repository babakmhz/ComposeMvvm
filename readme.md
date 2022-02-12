# Compose MVVM
##### This is simple project to determine how to integrate Jetpack Compose with MVVM architecture.
## Architecture
- MVVM
#### Tools & libraries/Technologies

- kotlin as the main programming language
- Jetpack architecture components
- Hilt
- Jetpack Compose
- Objectbox
- kotlin-coroutines
- Retrofit

#### Packages
- di: Dependency injection module of the applicaiton
- data: Representers Models, Network, Entity and Repository classes/interfaces
- utils: Utils, helper methods of the application
- persentation: View layer of MVVM architecure
#### Key Classes And Functions
- `RepositoryHelper.kt`: Interface to manage behavior of RepositoryImpl class
- `ReposotoryImpl.kt`: Implementation of RepositoryHelper interface which is responsible for feeding viewModel with data

- `ApiService.kt`: Network API calls: responsible for calling the remote source

- `MainViewModel.kt`: Application ViewModel

- `ExtensionFunctions.kt`: Represents extension functions that are made for the application

Cheers!