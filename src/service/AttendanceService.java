package service;

import model.Attendance;
import repository.AttendanceRepository;
import repository.InMemoryAttendanceRepository;
import java.util.List;

/**
 * AttendanceService - Business logic for employee attendance tracking.
 */
public class AttendanceService {
    private AttendanceRepository attendanceRepository;

    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public AttendanceService() {
        this.attendanceRepository = new InMemoryAttendanceRepository();
    }

    public void addAttendance(Attendance attendance) throws IllegalArgumentException {
        if (attendance == null) {
            throw new IllegalArgumentException("Attendance cannot be null");
        }
        validateAttendance(attendance);
        if (attendanceRepository.exists(attendance.getEmployeeCode(), attendance.getDepartmentCode())) {
            throw new IllegalArgumentException("Attendance record already exists for this employee and department");
        }
        attendanceRepository.save(attendance);
    }

    private void validateAttendance(Attendance attendance) throws IllegalArgumentException {
        if (attendance.getEmployeeCode() == null || attendance.getEmployeeCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Employee code cannot be empty");
        }
        if (attendance.getDepartmentCode() == null || attendance.getDepartmentCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Department code cannot be empty");
        }
        attendance.calculatePercentage();
    }

    public Attendance getAttendance(String employeeCode, String departmentCode) throws IllegalArgumentException {
        Attendance attendance = attendanceRepository.findById(employeeCode, departmentCode);
        if (attendance == null) {
            throw new IllegalArgumentException("Attendance record not found");
        }
        return attendance;
    }

    public List<Attendance> getAttendanceForEmployee(String employeeCode) {
        return attendanceRepository.findByEmployeeCode(employeeCode);
    }

    public List<Attendance> getAttendanceForDepartment(String departmentCode) {
        return attendanceRepository.findByDepartmentCode(departmentCode);
    }

    public void updateAttendance(String employeeCode, String departmentCode, int totalWorkDays, int daysPresent)
            throws IllegalArgumentException {
        getAttendance(employeeCode, departmentCode);
        Attendance updatedAttendance = new Attendance(employeeCode, departmentCode, totalWorkDays, daysPresent);
        validateAttendance(updatedAttendance);
        attendanceRepository.update(updatedAttendance);
    }

    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    public double calculateAttendancePercentage(String employeeCode, String departmentCode) throws IllegalArgumentException {
        return getAttendance(employeeCode, departmentCode).calculatePercentage();
    }

    public String getAttendanceStatus(String employeeCode, String departmentCode) throws IllegalArgumentException {
        return getAttendance(employeeCode, departmentCode).getAttendanceStatus();
    }

    public boolean isInGoodStanding(String employeeCode, String departmentCode) throws IllegalArgumentException {
        return getAttendance(employeeCode, departmentCode).isEligible();
    }

    public double getOverallAttendancePercentage(String employeeCode) throws IllegalArgumentException {
        List<Attendance> attendances = getAttendanceForEmployee(employeeCode);
        if (attendances.isEmpty()) {
            throw new IllegalArgumentException("No attendance records found for employee: " + employeeCode);
        }
        double totalPercentage = 0;
        for (Attendance attendance : attendances) {
            totalPercentage += attendance.calculatePercentage();
        }
        return totalPercentage / attendances.size();
    }

    public boolean attendanceExists(String employeeCode, String departmentCode) {
        return attendanceRepository.exists(employeeCode, departmentCode);
    }
}
