package util;

import java.util.regex.Pattern;

/**
 * ValidationUtil - Reusable validation methods for the application.
 * 
 * Centralizes validation logic so it's not duplicated in multiple GUI classes.
 * This demonstrates the importance of code reusability and maintaining DRY principle.
 */
public class ValidationUtil {

    // Regex patterns
    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = 
            Pattern.compile("^[0-9]{10}$");
        private static final Pattern EMPLOYEE_CODE_PATTERN =
            Pattern.compile("^EMP[A-Z0-9]{3,}$");
        private static final Pattern DEPARTMENT_CODE_PATTERN =
            Pattern.compile("^[A-Z][A-Z0-9]{1,7}$");

    /**
     * Validate if a string is not empty
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Validate email format
     */
    public static boolean isValidEmail(String email) {
        if (!isNotEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validate phone number (10 digits)
     */
    public static boolean isValidPhone(String phone) {
        if (!isNotEmpty(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Validate age
     */
    public static boolean isValidAge(int age) {
        return age >= 18 && age <= 100;
    }

    /**
     * Validate employee code format
     */
    public static boolean isValidEmployeeCode(String employeeCode) {
        if (!isNotEmpty(employeeCode)) {
            return false;
        }
        return EMPLOYEE_CODE_PATTERN.matcher(employeeCode).matches();
    }

    /**
     * Validate department code format
     */
    public static boolean isValidDepartmentCode(String departmentCode) {
        if (!isNotEmpty(departmentCode)) {
            return false;
        }
        return DEPARTMENT_CODE_PATTERN.matcher(departmentCode).matches();
    }

    /**
     * Validate attendance values
     */
    public static boolean isValidAttendance(int totalWorkDays, int daysPresent) {
        return totalWorkDays > 0 && daysPresent >= 0 && daysPresent <= totalWorkDays;
    }

    /**
     * Validate integer input from string
     */
    public static boolean isValidInteger(String value) {
        if (!isNotEmpty(value)) {
            return false;
        }
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Parse integer safely
     */
    public static int parseInteger(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Get validation error message
     */
    public static String getValidationError(String fieldName, String reason) {
        return fieldName + ": " + reason;
    }
}
