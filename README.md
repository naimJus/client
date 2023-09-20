# Android User List App

This Android project is a simple user list app that fetches a list of users from a remote API and displays them in a user-friendly interface. It is designed
following Clean Architecture principles, using the MVVM (Model-View-ViewModel) architectural pattern and built using Jetpack Compose, OkHTTP, Retrofit, GSON,
Dagger II, etc.

## Features

- Fetches a list of users from the [JSONPlaceholder API](https://jsonplaceholder.typicode.com/users).
- Dark / Light mode
- Displays each user's name and email in a list.
- Users can tap on a user to view additional details (bonus feature).

## Architecture

The project is structured into three distinct layers:

1. **Presentation Layer (app module)**: This layer is responsible for the user interface and interaction. It contains the Jetpack Compose UI components,
   ViewModels, and ViewModelsFactory. It observes the data from the domain layer and displays it to the user. In this layer, we also implement DI using Dagger.

2. **Domain Layer**: The domain layer is responsible for encapsulating complex business logic, or simple business logic that is reused by multiple ViewModels.
   This layer is used to handle complexity or favor reusability. To keep these classes simple and lightweight, each use case has responsibility over a single
   functionality, and they don’t contain mutable data. This layer defines the use cases, which are responsible for interacting with the data layer. In this
   layer, we map the raw user data to a more user-friendly `UserItem` class, which is suitable for the presentation layer.

3. **Data Layer**: While the UI layer contains UI-related state and UI logic, the data layer contains application data and business logic. The business logic is
   what gives value to the app—it's made of real-world business rules that determine how application data must be created, stored, and changed. This separation
   of concerns allows the data layer to be used on multiple screens, share information between different parts of the app, and reproduce business logic outside
   of the UI for unit testing. The data layer handles data retrieval and storage. It interacts with the remote API using Retrofit and OkHttp. The repository
   pattern is used to abstract the data source, allowing us to easily switch between remote and local data sources if needed. The data layer also handles
   exceptions and error handling.

## Code Organization

The project follows best practices and coding standards:

- Code is organized into packages for clear separation of concerns.
- Dependency injection is used to provide dependencies to different layers.
- Interface-based programming is employed to allow for flexibility and easy testing.
- Unit testing is performed on the domain and data layers to ensure business logic is correct and error handling is robust.

## Future-Proof Design

The project is designed to be extensible:

- New features can be added without affecting existing code.
- Multiple developers or teams can collaborate on the project seamlessly.
- Business logic is separated from the presentation, making it easier to modify or expand the app.

## How to Run the App

To run the app, follow these steps:

1. Clone this repository to your local machine:

   ```
   git clone https://github.com/yourusername/android-user-list-app.git
   ```

2. Open the project in Android Studio.

3. Build and run the app on an Android emulator or physical device.

## Testing

Unit tests are included in the project to ensure code correctness and reliability. You can run the unit tests by right-clicking on the test directory and
selecting "Run tests in...". You can run the tests from the command line using the following command:

```
./gradlew testVariantNameUnitTest
```

## Bonus Feature

The app includes a bonus feature where users can tap on a user in the list to view additional details. This demonstrates the flexibility and modularity of the
architecture, as new features like this can be added easily.

## Conclusion

This Android User List App is a demonstration of a clean and maintainable architecture that follows best practices. It is designed to handle future updates and
expansions gracefully while providing a smooth user experience.

Feel free to explore the code, run the app, and extend it with additional features if desired.