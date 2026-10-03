package repository;

import model.Attendance;
import java.util.List;

/**
 * AttendanceRepository interface - Data access layer for Attendance objects.
 */
public interface AttendanceRepository {
    void save(Attendance attendance);
    Attendance findById(String employeeCode, String departmentCode);
    List<Attendance> findAll();
    List<Attendance> findByEmployeeCode(String employeeCode);
    List<Attendance> findByDepartmentCode(String departmentCode);
    void update(Attendance attendance);
    void delete(String employeeCode, String departmentCode);
    boolean exists(String employeeCode, String departmentCode);
}
