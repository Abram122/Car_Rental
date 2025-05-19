# 🚗 Car Rental Management System (CRMS)

A desktop application for managing car rentals, built with Java Swing and MySQL.  
It follows a modular MVC-inspired architecture with migrations, DAOs, domain models, and a Swing GUI.

---

## 📈 Project Stats
[![Repo Stats](https://github-readme-stats.vercel.app/api/pin/?username=Abram122&repo=Car_Rental&show_owner=false&title_color=blue&icon_color=yellow&bg_color=0d1117)](https://github.com/Abram122/Car_Rental)

![Lines of Code](https://tokei.rs/b1/github/Abram122/Car_Rental)
![Total Commits](https://img.shields.io/github/commit-activity/y/Abram122/Car_Rental)
![Last Commit](https://img.shields.io/github/last-commit/Abram122/Car_Rental)

---

## 📑 Table of Contents

- [About](#about)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Database Migrations](#database-migrations)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)
- [Team Members](#team-members)
- [Contact](#contact)
- [Acknowledgements](#acknowledgements)

---

## 📝 About

CRMS is a Java Swing-based desktop application using MySQL for persistence. It implements an MVC-inspired modular architecture:

- DAO pattern for data access  
- Domain models for business logic  
- Database migrations for setup

---

## ✨ Features

- ✅ User & Admin authentication  
- 🧾 Customer registration with license info  
- 🚘 Car inventory management (CRUD + categories)  
- 📅 Booking system with date validation  
- 💳 Payment handling and invoice generation  
- 📊 Rental history and returns tracking  
- ⭐ Customer reviews and ratings  
- 🔧 Maintenance logging and cost tracking  

---

## 💻 Tech Stack

- **Language**: Java SE  
- **GUI**: Java Swing  
- **Database**: MySQL  
- **Build Tool**: Maven (optional)  
- **JDBC**: MySQL Connector/J  
- **Version Control**: Git + GitHub  

---

## 🛠 Prerequisites

- Java 8 or higher  
- MySQL Server  
- MySQL Connector/J  
- (Optional) Maven or Gradle  

---

## 🚀 Installation

```bash
# 1. Clone the repo
git clone https://github.com/Abram122/Car_Rental.git
cd Car_Rental

# 2. (Optional) Build with Maven
mvn clean package
````

---

## ⚙️ Configuration

Create or update:

```
src/resources/DBPropFile.properties
```

```properties
MYSQL_DB_URL=jdbc:mysql://localhost:3306/car_rental_db
USER=your_db_username
PASSWORD=your_db_password
```

---

## 🗄️ Database Migrations

Run the migration script from:

```java
Connection conn = MySQLConnection.getInstance().getConnection();
DBMigration.migrate(conn);
```

Creates tables like `User`, `Admin`, `Car`, `Booking`, `Invoice`, `Review`, `Maintenance`, etc.

---

## ▶️ Usage

1. Start MySQL server
2. Run migration script (if not already)
3. Launch the app:

```bash
java -cp target/Car_Rental.jar Main
```

---

## 📁 Project Structure

```
Car_Rental/
├── src/
│   ├── controllers/      # Handles app logic and UI interaction
│   ├── dao/              # Data Access Objects
│   ├── migrations/       # DB setup scripts
│   ├── models/           # Domain/business logic
│   ├── utils/            # Utilities & DB connection
│   ├── views/            # Java Swing views
│   └── Main.java         # App entry point
└── resources/
    └── DBPropFile.properties
```

---

## 📸 Screenshots

> *(Add real screenshots here in a folder named `/screenshots/`)*

```markdown
![Login](screenshots/login.png)
![Booking Form](screenshots/booking.png)
![Invoice View](screenshots/invoice.png)
```

---

## 🤝 Contributing

1. Fork the repo
2. Create a new branch:

```bash
git checkout -b feature/YourFeature
```

3. Commit your changes:

```bash
git commit -m "Add YourFeature"
```

4. Push and create a pull request

```bash
git push origin feature/YourFeature
```

---

## 📜 License

This project is licensed under the **MIT License**.

---

## 👨‍💻 Team Members

* **Abram Mina**
* **[Kareem Diaa](#https://github.com/kareem-diaa)**
* **Ahmed Mohamed**
* **Zeyad Mahmoud**

---

## 📬 Contact

For inquiries or collaboration, feel free to reach out via GitHub Issues or Discussions.

---

## 🙌 Acknowledgements

* SU TECH Assistant Professors, Teaching Assistants, and Mentors
* Java & Swing Documentation
* MySQL Community Edition
* GitHub Community
* Open-source contributors

