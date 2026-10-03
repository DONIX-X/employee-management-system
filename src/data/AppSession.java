package data;

import service.AttendanceService;
import service.EmployeeService;
import service.DepartmentService;
import service.SalaryService;
import repository.AttendanceRepository;
import repository.DepartmentRepository;
import repository.EmployeeRepository;
import repository.JdbcAttendanceRepository;
import repository.JdbcDepartmentRepository;
import repository.JdbcEmployeeRepository;
import repository.JdbcSalaryRepository;
import repository.InMemoryAttendanceRepository;
import repository.InMemoryDepartmentRepository;
import repository.InMemoryEmployeeRepository;
import repository.InMemorySalaryRepository;
import repository.SalaryRepository;
import util.DBConnection;

import java.sql.SQLException;

public final class AppSession {
    private static final Object LOCK = new Object();

    private static AttendanceService attendanceService;
    private static EmployeeService employeeService;
    private static DepartmentService departmentService;
    private static SalaryService salaryService;
    private static boolean demoDataInitialized = false;
    private static boolean databaseAvailable = false;
    private static boolean databaseCheckCompleted = false;

    private AppSession() {
    }

    public static void initialize() {
        synchronized (LOCK) {
            if (!databaseCheckCompleted) {
                databaseAvailable = canUseDatabase();
                databaseCheckCompleted = true;
            }

            if (attendanceService == null) {
                attendanceService = new AttendanceService(getAttendanceRepository());
            }
            if (employeeService == null) {
                employeeService = new EmployeeService(getEmployeeRepository());
            }
            if (departmentService == null) {
                departmentService = new DepartmentService(getDepartmentRepository());
            }
            if (salaryService == null) {
                salaryService = new SalaryService(getSalaryRepository());
            }
            if (!demoDataInitialized) {
                DemoData demoData = new DemoData(employeeService, departmentService, salaryService, attendanceService);
                demoData.initializeDemoData();
                demoDataInitialized = true;
            }
        }
    }

    private static boolean canUseDatabase() {
        try {
            DBConnection.initializeDatabase();
            return true;
        } catch (SQLException | RuntimeException exception) {
            System.out.println("MySQL unavailable; switching to in-memory demo mode: " + exception.getMessage());
            return false;
        }
    }

    private static AttendanceRepository getAttendanceRepository() {
        return databaseAvailable ? new JdbcAttendanceRepository() : new InMemoryAttendanceRepository();
    }

    private static EmployeeRepository getEmployeeRepository() {
        return databaseAvailable ? new JdbcEmployeeRepository() : new InMemoryEmployeeRepository();
    }

    private static DepartmentRepository getDepartmentRepository() {
        return databaseAvailable ? new JdbcDepartmentRepository() : new InMemoryDepartmentRepository();
    }

    private static SalaryRepository getSalaryRepository() {
        return databaseAvailable ? new JdbcSalaryRepository() : new InMemorySalaryRepository();
    }

    public static AttendanceService getAttendanceService() {
        initialize();
        return attendanceService;
    }

    public static EmployeeService getEmployeeService() {
        initialize();
        return employeeService;
    }

    public static DepartmentService getDepartmentService() {
        initialize();
        return departmentService;
    }

    public static SalaryService getSalaryService() {
        initialize();
        return salaryService;
    }

    public static boolean isDemoDataInitialized() {
        return demoDataInitialized;
    }
}
