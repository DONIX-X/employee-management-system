package model;

/**
 * Attendance class - Demonstrates proper validation and exception handling.
 * This class tracks working days for an employee in a department.
 * It includes methods to calculate attendance percentage and status.
 */
public class Attendance {
    private String employeeCode;
    private String departmentCode;
    private int totalWorkDays;
    private int daysPresent;

    public static final double ELIGIBLE_PERCENTAGE = 75.0;

    // Constructor
    public Attendance(String employeeCode, String departmentCode, int totalWorkDays, int daysPresent) {
        this.employeeCode = employeeCode;
        this.departmentCode = departmentCode;
        this.totalWorkDays = totalWorkDays;
        this.daysPresent = daysPresent;
    }

    // Encapsulation: Getters and Setters
    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public int getTotalWorkDays() {
        return totalWorkDays;
    }

    public void setTotalWorkDays(int totalWorkDays) {
        this.totalWorkDays = totalWorkDays;
    }

    public int getDaysPresent() {
        return daysPresent;
    }

    public void setDaysPresent(int daysPresent) {
        this.daysPresent = daysPresent;
    }

    // Calculate attendance percentage
    // Exception handling: Check for division by zero
    public double calculatePercentage() throws IllegalArgumentException {
        if (totalWorkDays <= 0) {
            throw new IllegalArgumentException("Total work days must be greater than 0");
        }
        if (daysPresent > totalWorkDays) {
            throw new IllegalArgumentException("Days present cannot exceed total work days");
        }
        if (daysPresent < 0 || totalWorkDays < 0) {
            throw new IllegalArgumentException("Work days cannot be negative");
        }
        return (double) daysPresent / totalWorkDays * 100;
    }

    // Get attendance status based on percentage
    public String getAttendanceStatus() {
        try {
            double percentage = calculatePercentage();
            return percentage >= ELIGIBLE_PERCENTAGE ? "GOOD STANDING" : "LOW ATTENDANCE";
        } catch (IllegalArgumentException e) {
            return "INVALID";
        }
    }

    // Check if attendance is eligible
    public boolean isEligible() {
        try {
            return calculatePercentage() >= ELIGIBLE_PERCENTAGE;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        try {
            return "Attendance{" +
                    "employeeCode='" + employeeCode + '\'' +
                    ", departmentCode='" + departmentCode + '\'' +
                    ", present=" + daysPresent + "/" + totalWorkDays +
                    ", percentage=" + String.format("%.2f", calculatePercentage()) + "%" +
                    ", status=" + getAttendanceStatus() +
                    '}';
        } catch (IllegalArgumentException e) {
            return "Attendance{employeeCode='" + employeeCode + "', departmentCode='" + departmentCode + "', status=INVALID}";
        }
    }
}
