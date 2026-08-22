# 🍩 RUDonuts Android App

<div align="center">

**A Java-based Android ordering application for customizing and managing donut, coffee, and sandwich purchases.**

[![Java](https://img.shields.io/badge/Java-11-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Android](https://img.shields.io/badge/Android-SDK%2036-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
[![Android Studio](https://img.shields.io/badge/Android%20Studio-IDE-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white)](https://developer.android.com/studio)
[![OOP](https://img.shields.io/badge/Object--Oriented-Programming-5382A1?style=for-the-badge)](https://www.java.com/)

</div>

---

## 📱 Overview

RUDonuts is an Android ordering application developed using **Java and Android Studio**.

The application provides a mobile ordering workflow that allows users to browse products, customize selections, manage their current order, review previous orders, and cancel orders.

The project demonstrates practical experience with **object-oriented programming, Android application development, event-driven user interfaces, RecyclerView-based item selection, application navigation, and centralized order management**.

## ✨ Features

### 🍩 Donut Ordering

Users can browse and select from multiple donut categories, including:

- Yeast Donuts
- Cake Donuts
- Donut Holes
- Seasonal Donuts

The donut ordering interface uses a `Spinner` for selecting donut categories and a `RecyclerView` for displaying available flavors.

Users can:

- Select a donut flavor
- Enter a quantity
- View the calculated subtotal
- Add the selection to the current order
- Continue adding additional items

### ☕ Coffee Ordering

The application provides a dedicated coffee ordering interface that allows users to add coffee products to their order.

### 🥪 Sandwich Ordering

Users can browse and select sandwich products through a dedicated sandwich ordering screen.

### 🛒 Order Management

The application maintains a centralized current order through the `OrderManager` component.

Order management functionality includes:

- Adding items to the current order
- Assigning order numbers
- Reviewing the current order
- Placing completed orders
- Starting a new order
- Clearing the current order
- Maintaining order history
- Cancelling previous orders

### 📋 Order Summary

Users can review their current order before placing it.

### 📜 Order History

Users can view previously placed orders through the order history screen.

### ❌ Order Cancellation

Users can select an order from their order history and cancel it through the application's order-management functionality.

---

## 🛠️ Technologies

### Programming Language

- **Java 11**

### Development Environment

- **Android Studio**

### Android Technologies

- Android SDK 36
- AndroidX AppCompat
- Material Components
- ConstraintLayout
- RecyclerView
- CardView

### Development Concepts

- Object-Oriented Programming
- Event-Driven Programming
- Android Activity Navigation
- RecyclerView Adapters
- UI Input Handling
- Application State Management
- Singleton Design Pattern
- Model-Based Application Design
- Testing and Debugging

---

## 🏗️ Project Structure

```text
RUDonuts-Android-App/
│
├── RUDonuts/
│   ├── app/
│   │   └── src/
│   │       └── main/
│   │           ├── java/com/example/project5/
│   │           │   ├── MainActivity.java
│   │           │   ├── DonutActivity.java
│   │           │   ├── CoffeeActivity.java
│   │           │   ├── SandwichActivity.java
│   │           │   ├── OrderSummaryActivity.java
│   │           │   ├── OrderHistoryActivity.java
│   │           │   ├── OrderManager.java
│   │           │   ├── OrderSingleton.java
│   │           │   ├── DonutAdapter.java
│   │           │   └── models/
│   │           │       └── ...
│   │           ├── res/
│   │           │   ├── drawable/
│   │           │   ├── layout/
│   │           │   └── values/
│   │           └── AndroidManifest.xml
│   └── ...
│
└── README.md
```

### Main Components

| Component | Purpose |
|---|---|
| `MainActivity` | Main application screen and navigation |
| `DonutActivity` | Donut selection and ordering |
| `CoffeeActivity` | Coffee ordering |
| `SandwichActivity` | Sandwich ordering |
| `OrderSummaryActivity` | Displays the current order |
| `OrderHistoryActivity` | Displays previously placed orders |
| `OrderManager` | Centralized current-order and order-history management |
| `OrderSingleton` | Supports centralized order state |
| `DonutAdapter` | Displays donut products using RecyclerView |
| `models/` | Contains application data models |

---

## 🔄 Application Workflow

```text
                         ┌─────────────────┐
                         │   Main Screen   │
                         └────────┬────────┘
                                  │
                ┌─────────────────┼─────────────────┐
                │                 │                 │
                ▼                 ▼                 ▼
           🍩 Donuts          ☕ Coffee        🥪 Sandwiches
                │                 │                 │
                └─────────────────┼─────────────────┘
                                  │
                                  ▼
                           🛒 Current Order
                                  │
                         ┌────────┴────────┐
                         │                 │
                         ▼                 ▼
                  Order Summary      Order History
                                           │
                                           ▼
                                    Cancel Order
```

The main activity uses Android `Intent`s to navigate between the application's major screens.

---

## 🧠 Object-Oriented Design

The project separates application responsibilities across activities, adapters, order-management components, and model classes.

- Activities manage individual application screens and user interactions.
- Model classes represent application data.
- `DonutAdapter` manages the presentation of donut items in a `RecyclerView`.
- `OrderManager` manages the current order and order history.
- The order-management system provides a centralized way for different parts of the application to access order information.

### Singleton Pattern

The project uses a Singleton-based approach for centralized order management.

`OrderManager` provides a shared point of access for managing the current order and order history throughout the application.

---

## 📲 User Interface

The application uses Android layouts and UI components to provide interactive ordering screens.

The donut interface includes:

- Category selection
- Product selection
- Quantity input
- Subtotal calculation
- Add-to-order functionality
- Confirmation interactions

RecyclerView is used to dynamically display available donut products and flavors.

---

## 🚀 Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio)
- Java 11-compatible development environment
- Android SDK 36
- Android emulator or compatible Android device

The project uses:

```text
Java:         11
Compile SDK:  36
Target SDK:   36
Minimum SDK:  34
```

### Installation

1. Clone the repository:

```bash
git clone https://github.com/KhanR3/RUDonuts-Android-App.git
```

2. Open the `RUDonuts` directory in Android Studio.
3. Allow Android Studio to synchronize the Gradle project.
4. Select an Android emulator or compatible physical Android device.
5. Build and run the `app` module.

---

## 📸 Screenshots

Screenshots of the application's major screens can be added here.

Recommended screenshots include:

- Main screen
- Donut selection screen
- Coffee ordering screen
- Sandwich ordering screen
- Order summary
- Order history

Example:

```markdown
![Main Screen](screenshots/main-screen.png)
```

---

## 🎥 Demonstration

Demonstration videos can be linked here to show the application's functionality.

Recommended demonstrations include:

- Navigating the application
- Selecting and customizing donuts
- Adding products to an order
- Reviewing an order
- Viewing order history
- Cancelling an order

Large video files are not stored directly in this repository; external video links can be added here.

---

## 📚 What This Project Demonstrates

This project provided hands-on experience with:

- Android application development
- Java programming
- Object-oriented software design
- Android UI development
- Activity-based navigation
- Event-driven programming
- RecyclerView implementation
- Adapter development
- User input handling
- Application state management
- Singleton design pattern
- Data modeling
- Application testing
- Debugging

---

## 🎓 Project Context

RUDonuts was developed as part of a **Software Methodology** project using Java and Android Studio.

The project focused on applying software development concepts to a functional Android application while working with multiple application components, user interfaces, application state, and object-oriented design.

---

## 👤 Author

### Raahil Khan

Computer Science graduate from **Rutgers University** with a minor in Mathematics.

Interested in:

- Software Development
- IT Support / Help Desk
- Technical Support
- Database & Data Roles
- Cybersecurity

### Links

- 💻 GitHub: [KhanR3](https://github.com/KhanR3)
- 💼 LinkedIn: [Raahil Khan](https://www.linkedin.com/in/raahilkhan3)

---

## ⭐ Related Projects

Check out my other projects on GitHub, including:

- **HMDA Database & Natural Language → SQL Platform**
- **FIFA World Cup 2026 Predictor**
- **Vehicle Management System**

---

<div align="center">

⭐ **Thanks for visiting the RUDonuts project!**

</div>
