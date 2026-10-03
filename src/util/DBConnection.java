package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * DBConnection - Creates the Employee Management System MySQL schema and connections.
 */
public final class DBConnection {
    private static final String HOST = getSetting("EMS_DB_HOST", "localhost");
    private static final String PORT = getSetting("EMS_DB_PORT", "3306");
    private static final String DATABASE = getSetting("EMS_DB_NAME", "employee_management");
    private static final String USER = getSetting("EMS_DB_USER", "root");

    private DBConnection() {
    }

    public static void initializeDatabase() throws SQLException {
        String password = System.getenv("EMS_DB_PASSWORD");
        if (password == null) {
            throw new SQLException("Set the EMS_DB_PASSWORD environment variable before starting the application.");
        }
        if (!DATABASE.matches("[A-Za-z0-9_]+")) {
            throw new SQLException("EMS_DB_NAME may contain only letters, numbers, and underscores.");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException exception) {
            throw new SQLException("MySQL Connector/J is missing from the application classpath.", exception);
        }

        String serverUrl = "jdbc:mysql://" + HOST + ":" + PORT + "/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        try (Connection connection = DriverManager.getConnection(serverUrl, createProperties(password));
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS `" + DATABASE + "` CHARACTER SET utf8mb4");
        }

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS departments ("
                    + "department_code VARCHAR(16) PRIMARY KEY, name VARCHAR(120) NOT NULL, "
                    + "location VARCHAR(120), description VARCHAR(500)) ENGINE=InnoDB");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS employees ("
                    + "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(120) NOT NULL, age INT NOT NULL, "
                    + "phone VARCHAR(20), email VARCHAR(180), employee_code VARCHAR(32) NOT NULL UNIQUE, "
                    + "department_code VARCHAR(16) NOT NULL, designation VARCHAR(120) NOT NULL, "
                    + "CONSTRAINT fk_employee_department FOREIGN KEY (department_code) "
                    + "REFERENCES departments(department_code)) ENGINE=InnoDB");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS salary_records ("
                    + "employee_code VARCHAR(32) NOT NULL, pay_period CHAR(7) NOT NULL, "
                    + "base_salary DECIMAL(14,2) NOT NULL, bonus DECIMAL(14,2) NOT NULL, "
                    + "deductions DECIMAL(14,2) NOT NULL, PRIMARY KEY (employee_code, pay_period), "
                    + "CONSTRAINT fk_salary_employee FOREIGN KEY (employee_code) "
                    + "REFERENCES employees(employee_code) ON DELETE CASCADE) ENGINE=InnoDB");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS attendance_records ("
                    + "employee_code VARCHAR(32) NOT NULL, department_code VARCHAR(16) NOT NULL, "
                    + "total_work_days INT NOT NULL, days_present INT NOT NULL, "
                    + "PRIMARY KEY (employee_code, department_code), "
                    + "CONSTRAINT fk_attendance_employee FOREIGN KEY (employee_code) "
                    + "REFERENCES employees(employee_code) ON DELETE CASCADE, "
                    + "CONSTRAINT fk_attendance_department FOREIGN KEY (department_code) "
                    + "REFERENCES departments(department_code)) ENGINE=InnoDB");
        }
    }

    public static Connection getConnection() throws SQLException {
        String password = System.getenv("EMS_DB_PASSWORD");
        if (password == null) {
            throw new SQLException("Set the EMS_DB_PASSWORD environment variable before starting the application.");
        }
        String url = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        return DriverManager.getConnection(url, createProperties(password));
    }

    private static Properties createProperties(String password) {
        Properties properties = new Properties();
        properties.setProperty("user", USER);
        properties.setProperty("password", password);
        return properties;
    }

    private static String getSetting(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.trim().isEmpty() ? defaultValue : value.trim();
    }
}
