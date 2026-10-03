package repository;

import model.Attendance;
import java.util.ArrayList;
import java.util.List;

/**
 * InMemoryAttendanceRepository - Implementation of AttendanceRepository using ArrayList.
 */
public class InMemoryAttendanceRepository implements AttendanceRepository {
    private static ArrayList<Attendance> attendances = new ArrayList<>();

    @Override
    public void save(Attendance attendance) {
        // Check if attendance entry already exists
        for (Attendance a : attendances) {
            if (a.getEmployeeCode().equals(attendance.getEmployeeCode()) &&
                a.getDepartmentCode().equals(attendance.getDepartmentCode())) {
                return; // Already exists
            }
        }
        attendances.add(attendance);
    }

    @Override
    public Attendance findById(String employeeCode, String departmentCode) {
        for (Attendance attendance : attendances) {
            if (attendance.getEmployeeCode().equals(employeeCode) &&
                attendance.getDepartmentCode().equals(departmentCode)) {
                return attendance;
            }
        }
        return null;
    }

    @Override
    public List<Attendance> findAll() {
        return new ArrayList<>(attendances);
    }

    @Override
    public List<Attendance> findByEmployeeCode(String employeeCode) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance attendance : attendances) {
            if (attendance.getEmployeeCode().equals(employeeCode)) {
                result.add(attendance);
            }
        }
        return result;
    }

    @Override
    public List<Attendance> findByDepartmentCode(String departmentCode) {
        List<Attendance> result = new ArrayList<>();
        for (Attendance attendance : attendances) {
            if (attendance.getDepartmentCode().equals(departmentCode)) {
                result.add(attendance);
            }
        }
        return result;
    }

    @Override
    public void update(Attendance attendance) {
        for (int i = 0; i < attendances.size(); i++) {
            Attendance a = attendances.get(i);
            if (a.getEmployeeCode().equals(attendance.getEmployeeCode()) &&
                a.getDepartmentCode().equals(attendance.getDepartmentCode())) {
                attendances.set(i, attendance);
                return;
            }
        }
    }

    @Override
    public void delete(String employeeCode, String departmentCode) {
        attendances.removeIf(a -> a.getEmployeeCode().equals(employeeCode) &&
                                  a.getDepartmentCode().equals(departmentCode));
    }

    @Override
    public boolean exists(String employeeCode, String departmentCode) {
        return findById(employeeCode, departmentCode) != null;
    }

    public void clear() {
        attendances.clear();
    }
}
