# Employee Management System

A Java Swing desktop application for managing employee records, departments, payroll, and attendance. The project demonstrates object-oriented programming and a layered service/repository architecture, with MySQL persistence through JDBC.

## Features

- Create, search, update, and delete employee records
- Manage departments and their details
- Record monthly salary, bonuses, and deductions
- Track employee attendance and workdays
- View workforce and payroll reports
- Validate form data and persist records in MySQL
- Seed example records when the corresponding database tables are empty

## Technology

- Java 17+
- Java Swing
- MySQL 8+
- JDBC with the bundled MySQL Connector/J driver
- PowerShell launcher for Windows

## Requirements

- Windows with PowerShell
- JDK 17 or newer
- MySQL Server running locally or reachable on a trusted network
- A MySQL account permitted to create the application database and tables

The MySQL Connector/J driver is included at `lib\mysql-connector-j-26.7.0.jar`.

## Run the application

Open PowerShell in the project directory and run:

```powershell
.\run.ps1
```

The launcher compiles the Java sources and starts the application. If `EMS_DB_PASSWORD` is not already set, it prompts for the MySQL password without displaying the typed characters.

On startup, the application creates the database and tables if needed. The default connection settings are:

| Setting | Default |
| --- | --- |
| `EMS_DB_HOST` | `localhost` |
| `EMS_DB_PORT` | `3306` |
| `EMS_DB_NAME` | `employee_management` |
| `EMS_DB_USER` | `root` |

To use different settings, set them in PowerShell before launching:

```powershell
$env:EMS_DB_HOST = 'localhost'
$env:EMS_DB_PORT = '3306'
$env:EMS_DB_NAME = 'employee_management'
$env:EMS_DB_USER = 'your_mysql_user'
.\run.ps1
```

Replace the example values with your server details. Do not put database passwords in source files or commit them to Git.

## Sign in

The demonstration login is:

- **Username:** `admin`
- **Password:** `admin123`

This is a hardcoded demonstration login, not production authentication. Do not use it to protect sensitive or production systems.

## Architecture

The application separates the user interface, business logic, and persistence:

```text
Swing GUI → Services → Repository interfaces → JDBC repositories → MySQL
```

In-memory repository implementations are also included for use in code/tests. `DBConnection` initializes the MySQL schema, and `AppSession` wires repositories and services at startup.

## Project layout

```text
src/
├── data/         Application session and demo-data initialization
├── gui/          Login, dashboard, and management screens
├── interface_/   Report contracts
├── model/        Employee, department, salary, and attendance models
├── repository/   Repository interfaces and JDBC/in-memory implementations
├── service/      Business rules and validation
└── util/         Database connection, validation, and shared utilities
lib/              MySQL Connector/J
run.ps1           Windows build and launch script
```

## Database tables

The application creates these tables in `employee_management` by default:

- `departments`
- `employees`
- `salary_records`
- `attendance_records`

Demo records are added only when each corresponding table is empty. Existing database contents are not overwritten by demo-data initialization.

## Notes

- Keep employee database exports and credentials private.
- The launcher expects Windows PowerShell and a JDK 17+ installation.
- The application is intended as an educational project and does not provide production-grade authentication or multi-user authorization.
