# Dog Shelter App

This is an experimental Android project designed as a personal sandbox for exploring modern APIs, libraries, and architectural patterns. Rather than being a production-ready application, it serves as a technical showcase for implementing a **clean, reactive single-module architecture** and testing the latest features in the Android ecosystem.

## 🚀 Key Highlights

*   **Clean Single-Module Architecture:** Demonstrates how to maintain a highly organized, decoupled codebase within a single module using clear layer separation (Data, Domain, and UI).
*   **Reactive UI Patterns:** Fully reactive data flow utilizing **Kotlin Coroutines**, **Flow**, and **StateFlow** to ensure a predictable and lifecycle-aware UI state.
*   **Cloud AI:** Integration of **Firebase AI** to perform intelligent image analysis and processing.
*   **Custom Camera Implementation:** Leveraging **CameraX** to build a seamless, in-app photo capture experience.
*   **Image Processing:** Efficient image loading and transformation using **Coil**.

## 📸 Screenshots

Here is a look at some of the experimental features in action:

|                                                Get Breed from Photo with AI                                                |                    Generate Notes from Photo and Breed with AI                    |
|:--------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------:|
| <video src="https://github.com/user-attachments/assets/4e469d44-2686-467d-8d52-343743bfdfd2" width="250" controls></video> | <video src="https://github.com/user-attachments/assets/b29bc7fa-3c70-4416-bccd-cb262869260e" width="250" controls></video> |

## 🛠 Tech Stack

*   **Language:** Kotlin
*   **Dependency Injection:** Hilt
*   **Jetpack Compose:** For building a modern, declarative UI.
*   **Navigation:** Navigation Component (Moving toward Navigation 3).
*   **Concurrency:** Kotlin Coroutines & Flow.
*   **Networking/Data:** Repository pattern for abstracting data sources.

## 🏗 Architecture Overview

The project follows the **Repository Pattern** and **Clean Architecture** principles to ensure that the business logic remains independent of the UI and external frameworks.

1.  **UI Layer:** Compose-based views that observe `StateFlow` from ViewModels.
2.  **Domain Layer:** Contains business logic.
3.  **Data Layer:** Repositories that act as the single source of truth, managing data from the Camera, Firebase, and local storage.

## 🔮 Roadmap & Experiments

This project is a work in progress. Future experiments include:

*   [ ] **Navigation 3:** Migrating the current navigation logic to the latest experimental API.
*   [ ] **Firebase Login:** Implementing secure user authentication.
*   [ ] **Local ML:** Expanding on-device capabilities using ML Kit or LiteRT.

## ⚙️ Setup

1.  Clone the repository.
2.  Add your `google-services.json` from the Firebase Console to the `app/` directory.
3.  Build and run using the latest version of **Android Studio**.