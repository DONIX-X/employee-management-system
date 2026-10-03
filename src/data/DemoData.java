package data;

import model.Attendance;
import model.Employee;
import model.Department;
import model.Salary;
import service.AttendanceService;
import service.EmployeeService;
import service.DepartmentService;
import service.SalaryService;
import java.time.YearMonth;

/**
 * DemoData initializes sample records for the Employee Management System.
 */
public class DemoData {
    private static boolean initialized = false;

    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private SalaryService salaryService;
    private AttendanceService attendanceService;

    public DemoData(EmployeeService employeeService, DepartmentService departmentService,
                    SalaryService salaryService, AttendanceService attendanceService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.salaryService = salaryService;
        this.attendanceService = attendanceService;
    }

    // Initialize all demo data exactly once per application session.
    public synchronized void initializeDemoData() {
        if (initialized) {
            System.out.println("Demo data already initialized for this session.");
            return;
        }

        if (departmentService.getAllDepartments().isEmpty()) {
            initializeDepartments();
        }
        if (employeeService.getAllEmployees().isEmpty()) {
            initializeEmployees();
        }
        if (salaryService.getAllSalaries().isEmpty()) {
            initializeSalaries();
        }
        if (attendanceService.getAllAttendance().isEmpty()) {
            initializeAttendance();
        }
        initialized = true;
        System.out.println("Demo employee data initialized successfully");
    }

    private void initializeDepartments() {
        departmentService.addDepartment(new Department("HR", "Human Resources", "Building A", "People operations"));
        departmentService.addDepartment(new Department("ENG", "Engineering", "Building B", "Product development"));
        departmentService.addDepartment(new Department("FIN", "Finance", "Building C", "Financial operations"));
    }

    private void initializeEmployees() {
        String[][] employees = {
                {"Jordan Lee", "32", "9876500001", "jordan.lee@example.com", "EMP001", "HR", "HR Manager"},
                {"Morgan Patel", "28", "9876500002", "morgan.patel@example.com", "EMP002", "ENG", "Software Engineer"},
                {"Casey Brown", "35", "9876500003", "casey.brown@example.com", "EMP003", "FIN", "Accountant"},
                {"Avery Wilson", "30", "9876500004", "avery.wilson@example.com", "EMP004", "HR", "Recruitment Specialist"},
                {"Riley Johnson", "27", "9876500005", "riley.johnson@example.com", "EMP005", "ENG", "Frontend Developer"},
                {"Quinn Martinez", "33", "9876500006", "quinn.martinez@example.com", "EMP006", "FIN", "Financial Analyst"},
                {"Harper Davis", "29", "9876500007", "harper.davis@example.com", "EMP007", "HR", "Operations Lead"},
                {"Blake Thompson", "31", "9876500008", "blake.thompson@example.com", "EMP008", "ENG", "Backend Developer"},
                {"Dylan Nguyen", "26", "9876500009", "dylan.nguyen@example.com", "EMP009", "FIN", "Tax Associate"},
                {"Skyler Miller", "34", "9876500010", "skyler.miller@example.com", "EMP010", "HR", "HR Generalist"},
                {"Taylor Anderson", "41", "9876500011", "taylor.anderson@example.com", "EMP011", "ENG", "DevOps Engineer"},
                {"Cameron Garcia", "38", "9876500012", "cameron.garcia@example.com", "EMP012", "FIN", "Senior Accountant"},
                {"Peyton Lewis", "24", "9876500013", "peyton.lewis@example.com", "EMP013", "HR", "Training Coordinator"},
                {"Alex Walker", "36", "9876500014", "alex.walker@example.com", "EMP014", "ENG", "QA Engineer"},
                {"Reese Hall", "29", "9876500015", "reese.hall@example.com", "EMP015", "FIN", "Budget Manager"},
                {"Jordan Allen", "32", "9876500016", "jordan.allen@example.com", "EMP016", "HR", "Payroll Officer"},
                {"Parker Young", "27", "9876500017", "parker.young@example.com", "EMP017", "ENG", "Full Stack Developer"},
                {"Rowan King", "39", "9876500018", "rowan.king@example.com", "EMP018", "FIN", "Audit Specialist"},
                {"Hayden Wright", "30", "9876500019", "hayden.wright@example.com", "EMP019", "HR", "Compensation Analyst"},
                {"Jamie Lopez", "28", "9876500020", "jamie.lopez@example.com", "EMP020", "ENG", "Mobile Developer"},
                {"Drew Hill", "42", "9876500021", "drew.hill@example.com", "EMP021", "FIN", "Controller"},
                {"Riley Scott", "35", "9876500022", "riley.scott@example.com", "EMP022", "HR", "Employee Relations Officer"},
                {"Avery Green", "25", "9876500023", "avery.green@example.com", "EMP023", "ENG", "Systems Engineer"},
                {"Cameron Baker", "31", "9876500024", "cameron.baker@example.com", "EMP024", "FIN", "Accounts Payable Analyst"},
                {"Quinn Adams", "33", "9876500025", "quinn.adams@example.com", "EMP025", "HR", "Benefits Specialist"},
                {"Morgan Nelson", "37", "9876500026", "morgan.nelson@example.com", "EMP026", "ENG", "Network Administrator"},
                {"Harper Carter", "26", "9876500027", "harper.carter@example.com", "EMP027", "FIN", "Treasury Analyst"},
                {"Jordan Mitchell", "29", "9876500028", "jordan.mitchell@example.com", "EMP028", "HR", "Talent Acquisition Manager"},
                {"Blake Perez", "40", "9876500029", "blake.perez@example.com", "EMP029", "ENG", "Security Engineer"},
                {"Skyler Roberts", "34", "9876500030", "skyler.roberts@example.com", "EMP030", "FIN", "Risk Analyst"},
                {"Peyton Turner", "27", "9876500031", "peyton.turner@example.com", "EMP031", "HR", "Performance Manager"},
                {"Dylan Phillips", "36", "9876500032", "dylan.phillips@example.com", "EMP032", "ENG", "Cloud Engineer"},
                {"Avery Campbell", "29", "9876500033", "avery.campbell@example.com", "EMP033", "FIN", "Corporate Finance Lead"},
                {"Riley Parker", "32", "9876500034", "riley.parker@example.com", "EMP034", "HR", "Facilities Coordinator"},
                {"Cameron Evans", "28", "9876500035", "cameron.evans@example.com", "EMP035", "ENG", "Data Analyst"},
                {"Taylor Edwards", "39", "9876500036", "taylor.edwards@example.com", "EMP036", "FIN", "Compliance Officer"},
                {"Reese Collins", "30", "9876500037", "reese.collins@example.com", "EMP037", "HR", "Learning Specialist"},
                {"Jamie Stewart", "31", "9876500038", "jamie.stewart@example.com", "EMP038", "ENG", "UI Designer"},
                {"Hayden Sanchez", "35", "9876500039", "hayden.sanchez@example.com", "EMP039", "FIN", "Accounts Receivable Lead"},
                {"Alex Morris", "26", "9876500040", "alex.morris@example.com", "EMP040", "HR", "HR Assistant"},
                {"Parker Rogers", "33", "9876500041", "parker.rogers@example.com", "EMP041", "ENG", "Platform Engineer"},
                {"Rowan Reed", "29", "9876500042", "rowan.reed@example.com", "EMP042", "FIN", "Senior Auditor"},
                {"Cameron Cook", "37", "9876500043", "cameron.cook@example.com", "EMP043", "HR", "HR Business Partner"},
                {"Blake Morgan", "34", "9876500044", "blake.morgan@example.com", "EMP044", "ENG", "Database Administrator"},
                {"Quinn Bell", "28", "9876500045", "quinn.bell@example.com", "EMP045", "FIN", "Portfolio Analyst"},
                {"Dylan Murphy", "30", "9876500046", "dylan.murphy@example.com", "EMP046", "HR", "Recruitment Manager"},
                {"Avery Bailey", "25", "9876500047", "avery.bailey@example.com", "EMP047", "ENG", "Blockchain Developer"},
                {"Morgan Rivera", "38", "9876500048", "morgan.rivera@example.com", "EMP048", "FIN", "Business Analyst"},
                {"Harper Cooper", "32", "9876500049", "harper.cooper@example.com", "EMP049", "HR", "HR Coordinator"},
                {"Taylor Richardson", "36", "9876500050", "taylor.richardson@example.com", "EMP050", "ENG", "Senior Engineer"}
        };

        for (String[] employeeData : employees) {
            employeeService.addEmployee(new Employee(
                    0,
                    employeeData[0],
                    Integer.parseInt(employeeData[1]),
                    employeeData[2],
                    employeeData[3],
                    employeeData[4],
                    employeeData[5],
                    employeeData[6]));
        }
    }

    private void initializeSalaries() {
        String payPeriod = YearMonth.now().toString();
        double[] baseSalaries = {6000, 7200, 5700, 4800, 6500, 5800, 6100, 6900, 5400, 5000,
                7600, 5900, 4700, 7100, 6200, 4900, 7800, 5600, 5200, 7300,
                6700, 5100, 8000, 6100, 5400, 6600, 5750, 6900, 6200, 7300,
                5800, 8200, 6000, 6400, 6800, 5500, 7200, 5900, 6100, 7500,
                5300, 7900, 6200, 5700, 7000, 6000, 7600, 6400, 7200, 6800};

        int index = 0;
        for (Employee employee : employeeService.getAllEmployees()) {
            double baseSalary = baseSalaries[index % baseSalaries.length];
            double bonus = baseSalary * 0.08;
            double deductions = baseSalary * 0.04;
            salaryService.addSalary(new Salary(employee.getEmployeeCode(), payPeriod, baseSalary, bonus, deductions));
            index++;
        }
    }

    private void initializeAttendance() {
        int[] attendanceValues = {21, 20, 19, 21, 20, 18, 22, 20, 19, 21,
                20, 18, 22, 21, 19, 20, 22, 18, 21, 20,
                18, 22, 21, 19, 20, 22, 18, 21, 20, 19,
                22, 20, 18, 21, 20, 19, 22, 18, 21, 20,
                19, 22, 18, 21, 20, 19, 22, 20, 18, 21};

        int index = 0;
        for (Employee employee : employeeService.getAllEmployees()) {
            int totalWorkDays = 22;
            int daysPresent = attendanceValues[index % attendanceValues.length];
            attendanceService.addAttendance(new Attendance(employee.getEmployeeCode(), employee.getDepartmentCode(), totalWorkDays, daysPresent));
            index++;
        }
    }
}
