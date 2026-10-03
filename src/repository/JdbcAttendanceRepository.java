package repository;

import model.Attendance;
import util.DBConnection;
import util.DataAccessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * JdbcAttendanceRepository - MySQL implementation of AttendanceRepository.
 */
public class JdbcAttendanceRepository implements AttendanceRepository {
    @Override
    public void save(Attendance attendance) {
        String sql = "INSERT INTO attendance_records (employee_code, department_code, total_work_days, days_present) "
                + "VALUES (?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            bindAttendance(statement, attendance);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("save attendance", exception);
        }
    }

    @Override
    public Attendance findById(String employeeCode, String departmentCode) {
        String sql = "SELECT * FROM attendance_records WHERE employee_code = ? AND department_code = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeCode);
            statement.setString(2, departmentCode);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? mapAttendance(result) : null;
            }
        } catch (SQLException exception) {
            throw new DataAccessException("find attendance", exception);
        }
    }

    @Override
    public List<Attendance> findAll() {
        List<Attendance> attendances = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM attendance_records ORDER BY employee_code");
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                attendances.add(mapAttendance(result));
            }
            return attendances;
        } catch (SQLException exception) {
            throw new DataAccessException("list attendance", exception);
        }
    }

    @Override
    public List<Attendance> findByEmployeeCode(String employeeCode) {
        return findMany("SELECT * FROM attendance_records WHERE employee_code = ?", employeeCode);
    }

    @Override
    public List<Attendance> findByDepartmentCode(String departmentCode) {
        return findMany("SELECT * FROM attendance_records WHERE department_code = ?", departmentCode);
    }

    private List<Attendance> findMany(String sql, String value) {
        List<Attendance> attendances = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, value);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    attendances.add(mapAttendance(result));
                }
            }
            return attendances;
        } catch (SQLException exception) {
            throw new DataAccessException("search attendance", exception);
        }
    }

    @Override
    public void update(Attendance attendance) {
        String sql = "UPDATE attendance_records SET total_work_days = ?, days_present = ? "
                + "WHERE employee_code = ? AND department_code = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, attendance.getTotalWorkDays());
            statement.setInt(2, attendance.getDaysPresent());
            statement.setString(3, attendance.getEmployeeCode());
            statement.setString(4, attendance.getDepartmentCode());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("update attendance", exception);
        }
    }

    @Override
    public void delete(String employeeCode, String departmentCode) {
        String sql = "DELETE FROM attendance_records WHERE employee_code = ? AND department_code = ?";
        try (Connection connection = DBConnection.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeCode);
            statement.setString(2, departmentCode);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("delete attendance", exception);
        }
    }

    @Override
    public boolean exists(String employeeCode, String departmentCode) {
        return findById(employeeCode, departmentCode) != null;
    }

    private void bindAttendance(PreparedStatement statement, Attendance attendance) throws SQLException {
        statement.setString(1, attendance.getEmployeeCode());
        statement.setString(2, attendance.getDepartmentCode());
        statement.setInt(3, attendance.getTotalWorkDays());
        statement.setInt(4, attendance.getDaysPresent());
    }

    private Attendance mapAttendance(ResultSet result) throws SQLException {
        return new Attendance(result.getString("employee_code"), result.getString("department_code"),
                result.getInt("total_work_days"), result.getInt("days_present"));
    }
}