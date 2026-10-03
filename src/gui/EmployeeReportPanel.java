package gui;

import interface_.ReportGenerator;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Attendance;
import model.Department;
import model.Employee;
import model.Salary;
import service.AttendanceService;
import service.DepartmentService;
import service.EmployeeService;
import service.SalaryService;
import util.AppTheme;

/**
 * EmployeeReportPanel - Generates workforce, department, payroll, and attendance reports.
 */
public class EmployeeReportPanel extends JPanel {
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private SalaryService salaryService;
    private AttendanceService attendanceService;
    private JTextArea reportTextArea;
    private JComboBox<String> reportTypeCombo;

    public EmployeeReportPanel(EmployeeService employeeService, DepartmentService departmentService,
                               SalaryService salaryService, AttendanceService attendanceService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.salaryService = salaryService;
        this.attendanceService = attendanceService;
        setLayout(new BorderLayout());
        setBackground(AppTheme.darkMode ? AppTheme.BACKGROUND : AppTheme.SURFACE_ALT);
        add(createHeader(), BorderLayout.NORTH);
        add(createControls(), BorderLayout.WEST);
        reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        reportTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportTextArea.setBackground(AppTheme.darkMode ? AppTheme.SURFACE : AppTheme.WHITE);
        reportTextArea.setForeground(AppTheme.TEXT);
        reportTextArea.setCaretColor(AppTheme.TEXT);
        add(new JScrollPane(reportTextArea), BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppTheme.darkMode ? AppTheme.NAVY : new Color(20, 115, 95));
        JLabel title = new JLabel("Workforce Reports");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        panel.add(title, BorderLayout.WEST);
        return panel;
    }

    private JPanel createControls() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AppTheme.darkMode ? AppTheme.BACKGROUND : AppTheme.SURFACE_ALT);
        panel.setPreferredSize(new Dimension(220, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        JLabel reportLabel = new JLabel("Report Type:");
        reportLabel.setForeground(AppTheme.TEXT);
        panel.add(reportLabel);
        reportTypeCombo = new JComboBox<>(new String[]{"Employee Directory", "Department Directory", "Payroll Summary", "Attendance Summary"});
        reportTypeCombo.setMaximumSize(new Dimension(200, 28));
        reportTypeCombo.setBackground(AppTheme.SURFACE);
        reportTypeCombo.setForeground(AppTheme.TEXT);
        panel.add(reportTypeCombo);
        JButton generateButton = new JButton("Generate Report");
        generateButton.setBackground(AppTheme.BURGUNDY);
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);
        generateButton.setOpaque(true);
        generateButton.setContentAreaFilled(true);
        generateButton.setBorderPainted(false);
        generateButton.addActionListener(event -> generateReport());
        panel.add(Box.createVerticalStrut(10));
        panel.add(generateButton);
        return panel;
    }

    private void generateReport() {
        String reportType = (String) reportTypeCombo.getSelectedItem();
        ReportGenerator generator;
        List<?> data;
        if ("Department Directory".equals(reportType)) {
            generator = new DepartmentReportGenerator();
            data = departmentService.getAllDepartments();
        } else if ("Payroll Summary".equals(reportType)) {
            generator = new SalaryReportGenerator();
            data = salaryService.getAllSalaries();
        } else if ("Attendance Summary".equals(reportType)) {
            generator = new AttendanceReportGenerator();
            data = attendanceService.getAllAttendance();
        } else {
            generator = new EmployeeReportGenerator();
            data = employeeService.getAllEmployees();
        }
        reportTextArea.setText(generator.generateReport(data));
    }

    private class EmployeeReportGenerator implements ReportGenerator {
        @Override
        public String generateReport(List<?> data) {
            StringBuilder report = new StringBuilder("EMPLOYEE DIRECTORY\n\n");
            report.append(String.format("%-12s %-22s %-16s %-24s%n", "Code", "Name", "Department", "Designation"));
            report.append("-".repeat(78)).append('\n');
            for (Object item : data) {
                Employee employee = (Employee) item;
                report.append(String.format("%-12s %-22s %-16s %-24s%n", employee.getEmployeeCode(),
                        employee.getName(), employee.getDepartmentCode(), employee.getDesignation()));
            }
            return report.toString();
        }

        @Override
        public String getReportTitle() {
            return "Employee Directory";
        }
    }

    private class DepartmentReportGenerator implements ReportGenerator {
        @Override
        public String generateReport(List<?> data) {
            StringBuilder report = new StringBuilder("DEPARTMENT DIRECTORY\n\n");
            for (Object item : data) {
                Department department = (Department) item;
                report.append(String.format("%-8s %-24s %-18s %s%n", department.getDepartmentCode(),
                        department.getName(), department.getLocation(), department.getDescription()));
            }
            return report.toString();
        }

        @Override
        public String getReportTitle() {
            return "Department Directory";
        }
    }

    private class SalaryReportGenerator implements ReportGenerator {
        @Override
        public String generateReport(List<?> data) {
            StringBuilder report = new StringBuilder("PAYROLL SUMMARY\n\n");
            report.append(String.format("%-12s %-10s %14s %12s %12s %14s%n", "Employee", "Period", "Base", "Bonus", "Deductions", "Net"));
            for (Object item : data) {
                Salary salary = (Salary) item;
                report.append(String.format("%-12s %-10s %14.2f %12.2f %12.2f %14.2f%n", salary.getEmployeeCode(),
                        salary.getPayPeriod(), salary.getBaseSalary(), salary.getBonus(), salary.getDeductions(), salary.getNetSalary()));
            }
            return report.toString();
        }

        @Override
        public String getReportTitle() {
            return "Payroll Summary";
        }
    }

    private class AttendanceReportGenerator implements ReportGenerator {
        @Override
        public String generateReport(List<?> data) {
            StringBuilder report = new StringBuilder("EMPLOYEE ATTENDANCE SUMMARY\n\n");
            report.append(String.format("%-12s %-14s %-12s %-12s %-12s%n", "Employee", "Department", "Work Days", "Present", "Attendance"));
            for (Object item : data) {
                Attendance attendance = (Attendance) item;
                report.append(String.format("%-12s %-14s %-12d %-12d %10.2f%%%n", attendance.getEmployeeCode(),
                        attendance.getDepartmentCode(), attendance.getTotalWorkDays(), attendance.getDaysPresent(),
                        attendance.calculatePercentage()));
            }
            return report.toString();
        }

        @Override
        public String getReportTitle() {
            return "Attendance Summary";
        }
    }
}