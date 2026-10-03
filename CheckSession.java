import data.AppSession;
import model.Employee;

public class CheckSession {
    public static void main(String[] args) {
        AppSession.initialize();
        System.out.println("initial=" + AppSession.getEmployeeService().getTotalEmployees());
        AppSession.initialize();
        System.out.println("second_init=" + AppSession.getEmployeeService().getTotalEmployees());

        AppSession.getEmployeeService().addEmployee(new Employee(0, "Test Employee", 30, "9999999999",
            "test@example.com", "EMPTEST", "HR", "Test Role"));
        System.out.println("after_add=" + AppSession.getEmployeeService().getTotalEmployees());

        AppSession.initialize();
        System.out.println("after_reinit=" + AppSession.getEmployeeService().getTotalEmployees());
    }
}
