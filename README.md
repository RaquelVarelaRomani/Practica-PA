# Cinema Ticket Booking 🎬

A full-stack web application for cinema ticket booking and management, developed as part of the Computer Engineering degree at the University of A Coruña (UDC).

The project implements a cinema ticket purchasing system, allowing users to browse movies, view available sessions, purchase tickets, manage their purchases and collect tickets.

## 🎓 Academic Context

- **University:** University of A Coruña (UDC)
- **Degree:** Computer Engineering
- **Specialization:** Software Engineering
- **Course:** Advanced Programming (Programación Avanzada)
- **Academic year:** 2025/2026
- **Project type:** Collaborative university project

## 🛠️ Tech Stack

| Category | Technologies |
|---|---|
| Backend | Java, Spring Boot, Spring Data JPA |
| Frontend | React, JavaScript |
| Database | MySQL |
| Build Tools | Maven, npm, Vite |
| Version Control | Git, GitHub |
| Testing | JUnit, Selenium |

## ✨ Features

### 👤 User Management
- User registration and authentication.
- Role-based access control.
- User profile and purchase management.

### 🎥 Movie Catalogue
- Browse available movies.
- View movie details and cinema sessions.
- Search for available movie sessions.

### 🎟️ Ticket Purchasing
- Purchase tickets for available sessions.
- Validate ticket availability.
- Store purchase information.
- View purchase history.

### 🧾 Ticket Collection
- Ticket seller authentication.
- Ticket collection using purchase information.
- Validation to prevent duplicate ticket collection.
- Purchase delivery status management.

## 🏗️ Project Architecture

The application follows a layered architecture that separates frontend, backend and persistence responsibilities.

- **Frontend:** React-based user interface.
- **Backend:** REST API developed with Spring Boot.
- **Service Layer:** Business logic and transaction management.
- **Persistence Layer:** Database access using Spring Data JPA.
- **Database:** MySQL relational database.

## 🚀 Getting Started

Follow the instructions below to configure and run the application in your local development environment.

### Requirements 📋

Make sure the following tools are installed:

- Java JDK
- Maven
- Node.js and npm
- MySQL
- Git

### Installation 🔧

**1. Clone the repository**

```bash
git clone <repository-url>
cd <repository-name>
```

**2. Configure the database**

Create the required MySQL databases and configure the connection properties according to the project's backend configuration.

**3. Start the backend**

From the project root directory:

```bash
mvn spring-boot:run
```

**4. Install frontend dependencies**

```bash
cd frontend
npm install
```

**5. Start the frontend**

```bash
npm run dev
```

The application should now be accessible through the local development server.

## 🧪 Testing

The project includes automated tests to validate the application's functionality.

- **Unit tests:** Verify individual business logic components.
- **Integration tests:** Validate interactions between services and the persistence layer.
- **End-to-end tests:** Test application workflows using Selenium.

Run backend tests with:

```bash
mvn test
```

## 👥 Contributors

This project was developed collaboratively as part of the Advanced Programming course at UDC.

## 🙏 Acknowledgements

This project is based on the Spring Boot and React template provided by the University of A Coruña (UDC).

## 📚 Disclaimer

This repository is intended for educational and portfolio purposes. It was developed as part of a university assignment and is not intended for production use.
