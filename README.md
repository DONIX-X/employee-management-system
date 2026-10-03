# Employee Management System - OOP Project

A comprehensive Java desktop application demonstrating Object-Oriented Programming (OOP) concepts for B.Tech students.

## 📌 Project Overview

**Employee Management System (EMS)** is a Java Swing desktop application for managing employees, departments, payroll, and workday attendance.

### Key Features

- ✅ **Employee Management** - Add, search, update, and delete employee records
- ✅ **Department Management** - Maintain department codes, names, locations, and descriptions
- ✅ **Payroll Management** - Record monthly base salary, bonus, deductions, and net salary
- ✅ **Attendance Management** - Track employee workdays and presence by department
- ✅ **Report Generation** - Generate employee, department, payroll, and attendance reports
- ✅ **Professional GUI** - Clean, intuitive Java Swing interface
- ✅ **Demo Data** - Comes with sample data for immediate testing

## 🛠️ Technology Stack

- **Language**: Java (JDK 8+)
- **GUI Framework**: Java Swing
- **Data Storage**: MySQL through JDBC
- **JDBC Driver**: MySQL Connector/J 26.7.0
- **Architecture Pattern**: Service-Repository Pattern
- **Database**: MySQL database `employee_management` (created on first launch)

## 📁 Project Structure

```
EmployeeManagementSystem/
│
├── src/
│   ├── model/
│   │   ├── Person.java              # Abstract parent class
│   │   ├── Employee.java            # Employee entity (inherits Person)
│   │   ├── Teacher.java             # Employee specialization
│   │   ├── Department.java          # Department entity
│   │   ├── Salary.java              # Monthly payroll record
│   │   └── Attendance.java          # Attendance tracking
│   │
│   ├── interface_/
│   │   └── ReportGenerator.java     # Interface for report generation
│   │
│   ├── repository/
│   │   ├── EmployeeRepository.java          # Interface
│   │   ├── InMemoryEmployeeRepository.java  # Implementation
│   │   ├── JdbcEmployeeRepository.java      # MySQL implementation
│   │   ├── DepartmentRepository.java        # Interface
│   │   ├── InMemoryDepartmentRepository.java# Implementation
│   │   ├── JdbcDepartmentRepository.java    # MySQL implementation
│   │   ├── SalaryRepository.java            # Interface
│   │   ├── InMemorySalaryRepository.java    # Implementation
│   │   ├── JdbcSalaryRepository.java        # MySQL implementation
│   │   ├── AttendanceRepository.java        # Interface
│   │   ├── InMemoryAttendanceRepository.java# Implementation
│   │   └── JdbcAttendanceRepository.java    # MySQL implementation
│   │
│   ├── service/
│   │   ├── EmployeeService.java     # Business logic for employees
│   │   ├── DepartmentService.java   # Business logic for departments
│   │   ├── SalaryService.java       # Business logic for payroll
│   │   └── AttendanceService.java    # Business logic for attendance
│   │
│   ├── gui/
│   │   ├── LoginFrame.java           # Login screen
│   │   ├── DashboardFrame.java       # Main dashboard
│   │   ├── EmployeePanel.java        # Employee management UI
│   │   ├── DepartmentPanel.java      # Department management UI
│   │   ├── SalaryPanel.java          # Payroll management UI
│   │   ├── EmployeeAttendancePanel.java # Workday attendance UI
│   │   └── EmployeeReportPanel.java  # Workforce reports UI
│   │
│   ├── util/
│   │   ├── DBConnection.java         # MySQL connection and schema setup
│   │   ├── DataAccessException.java  # Repository persistence errors
│   │   └── ValidationUtil.java       # Reusable validation methods
│   │
│   ├── data/
│   │   └── DemoData.java             # Demo data initialization
│   │
│   └── Main.java                     # Entry point
│
└── README.md                          # This file
```

## 🚀 How to Run

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- MySQL Server running locally or reachable over the network
- Connector/J JAR at `lib/mysql-connector-j-26.7.0.jar`
- A MySQL account allowed to create the database and tables

### Steps

1. If your database is not named `employee_management` or your MySQL account is not `root`, set `EMS_DB_NAME` and `EMS_DB_USER` in PowerShell before launching. For example:
   ```powershell
   $env:EMS_DB_NAME = 'your_database_name'
   $env:EMS_DB_USER = 'your_mysql_user'
   ```
   For a remote MySQL server, also set `EMS_DB_HOST` and, if needed, `EMS_DB_PORT`. Defaults are `localhost` and `3306`.

2. From the project root, run the launcher. It compiles all Java sources with Connector/J and securely prompts for the database password if `EMS_DB_PASSWORD` is not already set. The MySQL account needs permission to create the database (if missing) and tables:
   ```powershell
   .\run.ps1
   ```

   On startup, the app creates the database and tables if they do not exist, then seeds demo data only when the corresponding tables are empty. If MySQL connection settings were provided but the connection fails, the app shows an error instead of silently using temporary in-memory data. To intentionally run offline, start the Java application without setting `EMS_DB_PASSWORD`.

3. **Login with demo credentials**
   - Username: `admin`
   - Password: `admin123`

## 🎓 OOP Concepts Demonstrated

| Concept | Implementation | Location | Explanation |
|---------|-----------------|----------|-------------|
| **Class** | Employee, Department, Salary, Attendance | `model/` | Defines structure and behavior |
| **Object** | Employee objects created through EmployeeService | `service/EmployeeService.java` | Instances of classes used throughout |
| **Encapsulation** | Private fields + getters/setters | `model/Employee.java` | Data is protected; accessed via methods |
| **Inheritance** | `Employee extends Person`, `Teacher extends Employee` | `model/Employee.java`, `model/Teacher.java` | Child classes inherit parent properties and methods |
| **Method Overloading** | `searchEmployee(int id)` and `searchEmployee(String query)` | `service/EmployeeService.java` | Same method name, different parameters |
| **Method Overriding** | `displayDetails()` in Employee and Teacher | `model/Employee.java`, `model/Teacher.java` | Child class overrides the parent method |
| **Polymorphism** | Parent reference calling child methods | Demonstrated throughout | Different implementations execute based on actual object |
| **Abstraction** | `abstract Person` class | `model/Person.java` | Abstract class with abstract method `displayDetails()` |
| **Interface** | `ReportGenerator` interface | `interface_/ReportGenerator.java` | Multiple report generators implement this interface |
| **Collections** | ArrayList-backed repositories | `repository/InMemory*Repository.java` | Alternative in-memory implementations remain available |
| **Exception Handling** | Try-catch blocks, custom exceptions | `service/`, `gui/` | Handles invalid input and application errors gracefully |
| **Repository Pattern** | Interfaces + In-Memory implementations | `repository/` | Separates data access logic; easy MongoDB integration |
| **Service Layer** | Business logic separated from GUI | `service/` | Maintains clean architecture and reusability |

## 🔐 Demo Credentials

For security demonstration purposes, demo credentials are hardcoded:

- **Username**: `admin`
- **Password**: `admin123`

⚠️ **Note**: These are for demo purposes only. Real authentication should use secure methods.

## 📊 Sample Data

The application initializes with sample data:
- **3 Employees** across sample departments
- **3 Departments** with locations and descriptions
- **3 Monthly payroll records** with bonuses and deductions
- **3 Attendance records** with workday percentages

This ensures the GUI is populated immediately upon launch.

## 🗂️ Architecture & Design Patterns

### Layered Architecture

```
┌─────────────────────────────────┐
│      GUI Layer (Swing)          │
│  LoginFrame, DashboardFrame     │
│  EmployeePanel, DepartmentPanel│
│  SalaryPanel, AttendancePanel  │
└────────────────┬────────────────┘
                 ↓
┌─────────────────────────────────┐
│      Service Layer              │
│  EmployeeService, DepartmentService│
│  SalaryService, AttendanceService│
└────────────────┬────────────────┘
                 ↓
┌─────────────────────────────────┐
│  Repository Interfaces          │
│  JDBC and In-Memory Implementations│
└────────────────┬────────────────┘
                 ↓
┌─────────────────────────────────┐
│  MySQL Data Access (JDBC)       │
│  JdbcEmployeeRepository, etc.  │
└─────────────────────────────────┘
```

### Benefits of This Architecture

1. **Separation of Concerns** - Each layer has a specific responsibility
2. **Testability** - Each layer can be tested independently
3. **Reusability** - Services can be used by different GUIs
4. **Maintainability** - Changes in one layer don't affect others
5. **Scalability** - Easy to add new features or replace implementations

## 🔄 Current vs. Future Architecture

### Current: MySQL Storage
```
GUI → Service → Repository interface → JDBC repository → MySQL
```

### Alternative: In-Memory Storage
```
GUI → Service → Repository interface → InMemory repository
```

The application uses JDBC repositories by default. In-memory implementations remain available for tests or offline use by passing them to the existing service constructors.

## 🧪 Validation & Error Handling

The application includes comprehensive validation:

- ✅ Required field validation
- ✅ Email format validation
- ✅ Phone number validation (10 digits)
- ✅ Employee age validation (18-100)
- ✅ Non-negative salary, bonus, and deductions
- ✅ Duplicate employee and payroll record checks
- ✅ Attendance percentage validation
- ✅ Division by zero prevention
- ✅ User-friendly error messages

## 📋 Key Classes & Their Roles

### Model Classes
- **Person (Abstract)** - Base class for employee identity and contact details
- **Employee** - Represents an employee with an employee code, department, and designation
- **Teacher** - Employee specialization retaining the teaching role
- **Department** - Represents an organizational department
- **Salary** - Stores monthly payroll inputs and calculates net salary
- **Attendance** - Tracks employee workdays and presence percentage

### Service Classes
- **EmployeeService** - Handles employee CRUD, validation, and search
- **DepartmentService** - Handles department CRUD and search
- **SalaryService** - Handles monthly payroll records and totals
- **AttendanceService** - Handles attendance tracking and calculation

### Repository Classes
- **EmployeeRepository (Interface)** - Contract for employee data access
- **InMemoryEmployeeRepository** - ArrayList-based employee implementation
- **DepartmentRepository and SalaryRepository** - Data access contracts with in-memory implementations
- **AttendanceRepository** - Employee attendance data access

### GUI Classes
- **LoginFrame** - Authentication screen
- **DashboardFrame** - Main navigation and statistics
- **EmployeePanel** - Employee CRUD and search
- **DepartmentPanel** - Department CRUD and search
- **SalaryPanel** - Monthly payroll entry and maintenance
- **EmployeeAttendancePanel** - Workday attendance
- **EmployeeReportPanel** - Workforce reports using ReportGenerator

## 🎯 Learning Outcomes

After studying this project, you will understand:

1. **How to structure a Java application** - Proper package organization
2. **OOP principles in practice** - Not just theory, but real implementation
3. **Swing GUI development** - Building professional desktop interfaces
4. **Design patterns** - Service, Repository, MVC concepts
5. **Exception handling** - Proper error management
6. **Separation of concerns** - Why it matters and how to achieve it
7. **Scalability** - How to design for future changes (like MongoDB)

## 📝 Code Quality

- **Meaningful variable and method names** - Code is self-documenting
- **Strategic comments** - Explains "why" not "what"
- **No monolithic classes** - Each class has a single responsibility
- **Proper encapsulation** - Private fields, public getters/setters
- **Reusable utilities** - ValidationUtil prevents code duplication
- **Clean separation** - GUI, business logic, and data are separate

## 🔮 Future Enhancements

- [ ] MongoDB database integration (plug-and-play via repositories)
- [ ] User authentication with database
- [ ] Role-based access control (Admin, HR, Manager)
- [ ] Export reports to PDF/Excel
- [ ] Email notifications
- [ ] Payroll export and payslip generation
- [ ] Automatic backup
- [ ] Web version using Spring Boot

## 🐛 Known Limitations

- Requires MySQL Server and configured connection environment variables
- Demo credentials are hardcoded
- Report export is not implemented yet
- No multi-user support
- No database persistence

## ⚠️ Important Notes for Learners

1. This is an **educational project** - Focus on OOP concepts, not production-level features
2. Study the **architecture patterns** - They're more important than the features
3. Understand **why** things are organized this way
4. Don't copy-paste - **read and understand** the code
5. Experiment - **modify the code** to test your understanding

## 📚 Concepts to Study

Study these files in this order to understand the architecture:

1. **model/Person.java** - Abstract class and inheritance basics
2. **model/Employee.java** - Employee fields and method overriding
3. **model/Salary.java** - Payroll calculation
4. **repository/EmployeeRepository.java** - Interface design
5. **repository/InMemoryEmployeeRepository.java** - Interface implementation
6. **service/EmployeeService.java** - Service layer and separation of concerns
7. **interface_/ReportGenerator.java** - Report contract
8. **gui/EmployeeReportPanel.java** - Report implementations
9. **gui/DashboardFrame.java** - Application wiring

## 📞 Support

For questions about OOP concepts or the project structure, consult:
- Code comments in each file
- JavaDoc-style documentation above classes
- The README and this file

## 📄 License

This is an educational project. Feel free to use, modify, and learn from it.

---

**Happy Learning! Remember: The goal is to understand OOP, not to build a production system.**
