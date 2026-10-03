package repository;

import model.Department;
import util.DBConnection;
import util.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcDepartmentRepository - MySQL implementation of DepartmentRepository.
 */
public class JdbcDepartmentRepository implements DepartmentRepository {
    @Override
    public void save(Department department) {
        String sql = "INSERT INTO departments (department_code, name, location, description) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            bindDepartment(statement, department);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("save department", exception);
        }
    }

    @Override
    public Department findById(String departmentCode) {
        String sql = "SELECT * FROM departments WHERE department_code = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, departmentCode);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? mapDepartment(result) : null;
            }
        } catch (SQLException exception) {
            throw new DataAccessException("find department", exception);
        }
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM departments ORDER BY name");
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                departments.add(mapDepartment(result));
            }
            return departments;
        } catch (SQLException exception) {
            throw new DataAccessException("list departments", exception);
        }
    }

    @Override
    public void update(Department department) {
        String sql = "UPDATE departments SET name = ?, location = ?, description = ? WHERE department_code = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, department.getName());
            statement.setString(2, department.getLocation());
            statement.setString(3, department.getDescription());
            statement.setString(4, department.getDepartmentCode());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("update department", exception);
        }
    }

    @Override
    public void delete(String departmentCode) {
        String sql = "DELETE FROM departments WHERE department_code = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, departmentCode);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("delete department", exception);
        }
    }

    @Override
    public boolean exists(String departmentCode) {
        String sql = "SELECT 1 FROM departments WHERE department_code = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, departmentCode);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } catch (SQLException exception) {
            throw new DataAccessException("check department", exception);
        }
    }

    private void bindDepartment(PreparedStatement statement, Department department) throws SQLException {
        statement.setString(1, department.getDepartmentCode());
        statement.setString(2, department.getName());
        statement.setString(3, department.getLocation());
        statement.setString(4, department.getDescription());
    }

    private Department mapDepartment(ResultSet result) throws SQLException {
        return new Department(result.getString("department_code"), result.getString("name"),
                result.getString("location"), result.getString("description"));
    }
}