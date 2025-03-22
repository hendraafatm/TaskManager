#Task Manager

A simple task management app built with Jetpack Compose, Kotlin, and Room Database. The app follows the Clean Architecture pattern along with MVVM and uses Hilt for dependency injection.

🛠️ #Setup Instructions

#1. Clone the Repository
First, clone the repository to your local machine:

git clone https://github.com/hendraafatm/TaskManager.git

#2. Install Dependencies

Make sure you have the required dependencies installed for the project to run. Follow the instructions below based on your environment:

Android Studio Setup:
Open Android Studio.

Import the project by selecting File > Open and choosing the folder where you cloned the repository.

Android Studio should automatically sync Gradle. If it doesn't, click Sync Now in the toolbar.

#Dependencies:

Kotlin version 2.0.0

Jetpack Compose 1.6.x

Android SDK (compileSdk: 34, targetSdk: 34, minSdk: 24)

Hilt for Dependency Injection

Room Database for local data storage

For any additional dependencies, you can install them using Gradle:

./gradlew build

🚀 #Running the Project
Once the dependencies are set up, follow the steps below to run the project on an Android emulator or a physical device:

Connect your device via USB or use an emulator.

In Android Studio, click the Run button (green triangle) or select Run > Run 'app' from the menu.

The app will be installed on your device or emulator.

🧑‍💻 #Design Rationale

1. #Architecture
This project follows the Clean Architecture pattern along with MVVM (Model-View-ViewModel). Clean Architecture helps separate the code into three main layers: data, domain, and presentation.

Layers in Clean Architecture:
Data Layer: This layer is responsible for handling data operations, including interactions with Room Database for local storage, network calls, and any data repositories. The data layer abstracts the data sources, ensuring that the business logic doesn't directly depend on data sources.

Domain Layer: Contains the business logic and use cases of the application. It is independent of the frameworks or libraries, allowing it to be easily tested and maintained. The domain layer interacts with the data layer through repositories, which provide the necessary data for the application.

Presentation Layer: This layer is responsible for the UI and user interaction, following the MVVM architecture. It contains:

View: Composables that represent the UI, implemented using Jetpack Compose.

ViewModel: Handles UI-related data and business logic, exposing data to the UI and managing state.

The separation of concerns allows for:

Testability: All layers can be tested independently, with the domain and data layers being particularly testable.

Scalability: Adding new features or modules becomes easier without affecting other layers of the app.

2. #Dependency Injection

We use Hilt for dependency injection to decouple the components and make them more modular and easier to test. By using @HiltAndroidApp and @Inject, we ensure dependencies are automatically provided throughout the app.

3. #UI Design
The UI is designed to be simple, intuitive, and user-friendly. We use Jetpack Compose to build the UI in a declarative way, which helps reduce boilerplate and improves code readability. Some key principles followed:

Accessibility: The app is designed to be accessible, with proper contrast and font sizes.

Responsiveness: The layout adjusts to different screen sizes and orientations.

4. #Database Design
The app uses Room Database for local data storage. This allows users to store tasks locally, even when offline. The schema was designed with scalability in mind, making it easier to extend the app with new features.


#Database Schema
Table: tasks
The tasks table stores information about the tasks, including their title, description, priority, due date, and completion status.

| **Column Name**   | **Type**    | **Description**                                                              |
|-------------------|-------------|------------------------------------------------------------------------------|
| `id`              | `INTEGER`   | The unique identifier for each task. Auto-generated as the primary key.       |
| `title`           | `TEXT`      | The title of the task.                                                        |
| `description`     | `TEXT`      | An optional description of the task. Can be null.                            |
| `priority`        | `TEXT`      | The priority level of the task (e.g., "Low", "Medium", "High").               |
| `dueDate`         | `TEXT`      | The due date of the task in string format (e.g., "YYYY-MM-DD").               |
| `isCompleted`     | `BOOLEAN`   | A flag indicating whether the task is completed (true/false).                |


