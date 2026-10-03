# Employee Management System - Implementation Summary

## Application

A Java Swing desktop application for employee records, departments, payroll, and workday attendance. It retains the layered structure:

```text
Swing GUI -> Service -> Repository interface -> JDBC repository -> MySQL
```

## Domain Modules

- **Employee:** identity, contact details, employee code, department code, and designation.
- **Department:** department code, name, location, and description.
- **Salary:** monthly base salary, bonus, deductions, and calculated net salary.
- **Attendance:** total workdays and days present for an employee and department.

Each module uses a model, service, repository interface, and JDBC repository. In-memory implementations remain available. `Person` is the abstract parent model; `Teacher` is retained as a specialized `Employee`. `ReportGenerator` is used by the workforce report screen.

## Application Startup

`Main` initializes the MySQL schema, then initializes `AppSession` and opens `LoginFrame` on the Swing event thread. `AppSession` constructs JDBC-backed services and initializes `DemoData`. Demo records are inserted only when their corresponding tables are empty.

Demo login: `admin` / `admin123`.

## Screens

- `EmployeePanel`: employee CRUD and search.
- `DepartmentPanel`: department CRUD and search.
- `SalaryPanel`: payroll entry and maintenance.
- `EmployeeAttendancePanel`: attendance entry, update, and calculation.
- `EmployeeReportPanel`: directory and summary reports.
- `DashboardFrame`: navigation and employee, department, payroll, and attendance statistics.

## Persistence

MySQL is the active persistence layer. `DBConnection` creates the `employee_management` database and required tables on startup. The launcher securely prompts for the password when `EMS_DB_PASSWORD` is unset; optional settings are `EMS_DB_HOST`, `EMS_DB_PORT`, `EMS_DB_NAME`, and `EMS_DB_USER`. The in-memory repository implementations remain available for tests.

## Build

From the project root, run:

```powershell
.\run.ps1
```
