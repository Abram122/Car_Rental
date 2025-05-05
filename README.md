
## 🚗 Car Rental Management System (CRMS) Application

A desktop application for managing car rentals, built with Java Swing and MySQL.  
It follows a modular MVC-inspired architecture, with database migrations, DAOs, and domain models.

---

## Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [Database Migrations](#database-migrations)
- [Running the Application](#running-the-application)
- [Project Structure](#project-structure)
- [Future Improvements](#future-improvements)
- [Contributing](#contributing)
- [License](#license)

---

## Features

- **User & Admin Authentication**: Secure login for users and admins.
- **Customer Registration**: Register new customers with license details.
- **Car Management**: CRUD operations for car inventory and categories.
- **Booking System**: Reserve cars for specific dates.
- **Payments & Invoices**: Manage payments, apply discounts, generate invoices.
- **Rental History**: Track returns with comments and extra charges.
- **Reviews**: Collect user feedback and ratings.
- **Maintenance Tracking**: Log maintenance events and costs.

---

## Technologies

- **Language**: Java SE
- **GUI Framework**: Swing
- **Database**: MySQL
- **Build Tool**: Maven (optional)
- **JDBC**: MySQL Connector/J

---

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- MySQL Server
- MySQL Connector/J (JDBC driver)
- (Optional) Maven or Gradle

---

## Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/Abram122/Car_Rental.git
cd Car_Rental
```

### 2. Build the project (Optional if using Maven)

```bash
mvn clean package
```

### 3. Add MySQL Connector/J to your IDE’s classpath

If using Maven, add this dependency:

```xml
<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.33</version>
</dependency>
```

---

## Configuration

Edit or create the file:

```
src/resources/DBPropFile.properties
```

Add your database settings:

```properties
MYSQL_DB_URL=jdbc:mysql://localhost:3306/car_rental_db
USER=your_db_username
PASSWORD=your_db_password
```

---

## Database Migrations

Run the migration script from:

```
src/migrations/DBMigration.java
```

Example code to apply migrations:

```java
Connection conn = MySQLConnection.getInstance().getConnection();
DBMigration.migrate(conn);
```

- Migrations create all tables: `User`, `Admin`, `Category`, `Car_Model`, `Car`, `Manage_Car`, `Booking`, `Review`, `Payment`, `Rental_History`, `Discount`, `Apply_Discount`, `Invoice`, `Maintenance`
- Migrations are tracked to prevent re-execution.

---

## Running the Application

1. Start your MySQL server.
2. Make sure database and tables exist (run migrations if needed).
3. Run the app:

```bash
java -cp target/Car_Rental.jar Main
```

Replace `target/Car_Rental.jar` with your compiled file path.

---

## Project Structure

```plaintext
Car_Rental/
├── src/
│   ├── controllers/      # Controllers (UI & Service interaction)
│   ├── dao/              # Data Access Objects
│   ├── migrations/       # Database Migration scripts
│   ├── models/           # Domain models (User, Customer, RentalHistory, etc.)
│   ├── utils/            # DB Connection and Utilities
│   ├── views/            # Java Swing Views
│   └── Main.java         # Main Application entry point
└── resources/
    └── DBPropFile.properties  # Database config (local only)
```

---

## Future Improvements

- Add full UI for all features.
- Encrypt passwords (e.g., BCrypt).
- Add better logging and error handling.
- Create unit/integration tests.
- Build CI/CD pipeline.
- Support multi-language UI.

---

## Contributing

Pull requests are welcome!

1. Fork the repo.
2. Create a new branch:

```bash
git checkout -b feature/YourFeature
```

3. Commit your changes:

```bash
git commit -m "Add YourFeature"
```

4. Push to your branch:

```bash
git push origin feature/YourFeature
```

5. Open a pull request.

---

## License

This project is licensed under the **MIT License**.  
See the [LICENSE](LICENSE) file for details.

---
```
