package repository;

import model.Employee;
import util.DBConnection;
import util.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcEmployeeRepository - MySQL implementation of EmployeeRepository.
 */
public class JdbcEmployeeRepository implements EmployeeRepository {
    @Override
    public void save(Employee employee) {
        String sql = "INSERT INTO employees (name, age, phone, email, employee_code, department_code, designation) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            bindEmployee(statement, employee);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    employee.setId(keys.getInt(1));
                }
            }
        } catch (SQLException exception) {
            throw new DataAccessException("save employee", exception);
        }
    }

    @Override
    public Employee findById(int id) {
        return findOne("SELECT * FROM employees WHERE id = ?", statement -> statement.setInt(1, id));
    }

    @Override
    public Employee findByEmployeeCode(String employeeCode) {
        return findOne("SELECT * FROM employees WHERE employee_code = ?", statement -> statement.setString(1, employeeCode));
    }

    private Employee findOne(String sql, StatementBinder binder) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            binder.bind(statement);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? mapEmployee(result) : null;
            }
        } catch (SQLException exception) {
            throw new DataAccessException("find employee", exception);
        }
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees ORDER BY id");
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                employees.add(mapEmployee(result));
            }
            return employees;
        } catch (SQLException exception) {
            throw new DataAccessException("list employees", exception);
        }
    }

    @Override
    public void update(Employee employee) {
        String sql = "UPDATE employees SET name = ?, age = ?, phone = ?, email = ?, employee_code = ?, "
                + "department_code = ?, designation = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            bindEmployee(statement, employee);
            statement.setInt(8, employee.getId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("update employee", exception);
        }
    }

    @Override
    public void delete(int id) {
        execute("DELETE FROM employees WHERE id = ?", statement -> statement.setInt(1, id), "delete employee");
    }

    @Override
    public boolean exists(int id) {
        return exists("SELECT 1 FROM employees WHERE id = ?", statement -> statement.setInt(1, id));
    }

    private boolean exists(String sql, StatementBinder binder) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            binder.bind(statement);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } catch (SQLException exception) {
            throw new DataAccessException("check employee", exception);
        }
    }

    private void execute(String sql, StatementBinder binder, String operation) {
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            binder.bind(statement);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException(operation, exception);
        }
    }

    private void bindEmployee(PreparedStatement statement, Employee employee) throws SQLException {
        statement.setString(1, employee.getName());
        statement.setInt(2, employee.getAge());
        statement.setString(3, employee.getPhone());
        statement.setString(4, employee.getEmail());
        statement.setString(5, employee.getEmployeeCode());
        statement.setString(6, employee.getDepartmentCode());
        statement.setString(7, employee.getDesignation());
    }

    private Employee mapEmployee(ResultSet result) throws SQLException {
        return new Employee(result.getInt("id"), result.getString("name"), result.getInt("age"),
                result.getString("phone"), result.getString("email"), result.getString("employee_code"),
                result.getString("department_code"), result.getString("designation"));
    }

    @FunctionalInterface
    private interface StatementBinder {
        void bind(PreparedStatement statement) throws SQLException;
    }
}