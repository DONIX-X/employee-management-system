package repository;

import model.Salary;
import util.DBConnection;
import util.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcSalaryRepository - MySQL implementation of SalaryRepository.
 */
public class JdbcSalaryRepository implements SalaryRepository {
    @Override
    public void save(Salary salary) {
        String sql = "INSERT INTO salary_records (employee_code, pay_period, base_salary, bonus, deductions) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            bindSalary(statement, salary);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("save salary", exception);
        }
    }

    @Override
    public Salary findById(String employeeCode, String payPeriod) {
        String sql = "SELECT * FROM salary_records WHERE employee_code = ? AND pay_period = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeCode);
            statement.setString(2, payPeriod);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? mapSalary(result) : null;
            }
        } catch (SQLException exception) {
            throw new DataAccessException("find salary", exception);
        }
    }

    @Override
    public List<Salary> findAll() {
        List<Salary> salaries = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM salary_records ORDER BY pay_period DESC, employee_code");
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                salaries.add(mapSalary(result));
            }
            return salaries;
        } catch (SQLException exception) {
            throw new DataAccessException("list salaries", exception);
        }
    }

    @Override
    public List<Salary> findByEmployeeCode(String employeeCode) {
        List<Salary> salaries = new ArrayList<>();
        String sql = "SELECT * FROM salary_records WHERE employee_code = ? ORDER BY pay_period DESC";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeCode);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    salaries.add(mapSalary(result));
                }
            }
            return salaries;
        } catch (SQLException exception) {
            throw new DataAccessException("list employee salaries", exception);
        }
    }

    @Override
    public void update(Salary salary) {
        String sql = "UPDATE salary_records SET base_salary = ?, bonus = ?, deductions = ? "
                + "WHERE employee_code = ? AND pay_period = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, salary.getBaseSalary());
            statement.setDouble(2, salary.getBonus());
            statement.setDouble(3, salary.getDeductions());
            statement.setString(4, salary.getEmployeeCode());
            statement.setString(5, salary.getPayPeriod());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("update salary", exception);
        }
    }

    @Override
    public void delete(String employeeCode, String payPeriod) {
        String sql = "DELETE FROM salary_records WHERE employee_code = ? AND pay_period = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeCode);
            statement.setString(2, payPeriod);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("delete salary", exception);
        }
    }

    @Override
    public boolean exists(String employeeCode, String payPeriod) {
        String sql = "SELECT 1 FROM salary_records WHERE employee_code = ? AND pay_period = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeCode);
            statement.setString(2, payPeriod);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } catch (SQLException exception) {
            throw new DataAccessException("check salary", exception);
        }
    }

    private void bindSalary(PreparedStatement statement, Salary salary) throws SQLException {
        statement.setString(1, salary.getEmployeeCode());
        statement.setString(2, salary.getPayPeriod());
        statement.setDouble(3, salary.getBaseSalary());
        statement.setDouble(4, salary.getBonus());
        statement.setDouble(5, salary.getDeductions());
    }

    private Salary mapSalary(ResultSet result) throws SQLException {
        return new Salary(result.getString("employee_code"), result.getString("pay_period"),
                result.getDouble("base_salary"), result.getDouble("bonus"), result.getDouble("deductions"));
    }
}