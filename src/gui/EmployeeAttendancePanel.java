package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Attendance;
import model.Department;
import model.Employee;
import service.AttendanceService;
import service.DepartmentService;
import service.EmployeeService;
import util.AppTheme;

/**
 * EmployeeAttendancePanel - GUI for employee workday attendance.
 */
public class EmployeeAttendancePanel extends JPanel {
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private AttendanceService attendanceService;
    private JComboBox<Employee> employeeCombo;
    private JComboBox<Department> departmentCombo;
    private JTextField totalWorkDaysField;
    private JTextField daysPresentField;
    private JLabel percentageLabel;
    private JLabel statusLabel;
    private JTable attendanceTable;
    private DefaultTableModel tableModel;

    public EmployeeAttendancePanel(EmployeeService employeeService, DepartmentService departmentService,
                                   AttendanceService attendanceService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.attendanceService = attendanceService;
        setLayout(new BorderLayout());
        setBackground(AppTheme.darkMode ? AppTheme.BACKGROUND : AppTheme.SURFACE_ALT);
        add(createHeader(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.WEST);
        add(createTable(), BorderLayout.CENTER);
        refreshOptions();
        refreshTable();
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppTheme.darkMode ? AppTheme.NAVY : new Color(20, 115, 95));
        JLabel title = new JLabel("Employee Attendance");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));
        panel.add(title, BorderLayout.WEST);
        return panel;
    }

    private JPanel createForm() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AppTheme.darkMode ? AppTheme.BACKGROUND : AppTheme.SURFACE_ALT);
        panel.setPreferredSize(new Dimension(280, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        panel.add(new JLabel("Employee:"));
        employeeCombo = new JComboBox<>();
        employeeCombo.setMaximumSize(new Dimension(250, 26));
        employeeCombo.setBackground(AppTheme.SURFACE);
        employeeCombo.setForeground(AppTheme.TEXT);
        panel.add(employeeCombo);
        panel.add(new JLabel("Department:"));
        departmentCombo = new JComboBox<>();
        departmentCombo.setMaximumSize(new Dimension(250, 26));
        departmentCombo.setBackground(AppTheme.SURFACE);
        departmentCombo.setForeground(AppTheme.TEXT);
        panel.add(departmentCombo);
        totalWorkDaysField = addField(panel, "Total Work Days:");
        daysPresentField = addField(panel, "Days Present:");
        percentageLabel = new JLabel("Attendance: -");
        statusLabel = new JLabel("Status: -");
        panel.add(percentageLabel);
        panel.add(statusLabel);
        JPanel buttons = new JPanel(new GridLayout(2, 2, 5, 5));
        buttons.setMaximumSize(new Dimension(250, 80));
        buttons.add(createButton("Calculate", e -> calculate()));
        buttons.add(createButton("Save", e -> save()));
        buttons.add(createButton("Update", e -> update()));
        buttons.add(createButton("Clear", e -> clearForm()));
        panel.add(buttons);
        return panel;
    }

    private JTextField addField(JPanel parent, String label) {
        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(AppTheme.TEXT);
        parent.add(jLabel);
        JTextField field = new JTextField(15);
        field.setMaximumSize(new Dimension(250, 24));
        field.setBackground(AppTheme.SURFACE);
        field.setForeground(AppTheme.TEXT);
        field.setCaretColor(AppTheme.TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        parent.add(field);
        return field;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(AppTheme.BURGUNDY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.addActionListener(action);
        return button;
    }

    private JPanel createTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppTheme.darkMode ? AppTheme.BACKGROUND : AppTheme.SURFACE_ALT);
        tableModel = new DefaultTableModel(new String[]{"Employee", "Department", "Work Days", "Present", "Attendance", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        attendanceTable = new JTable(tableModel);
        attendanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        attendanceTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                int row = attendanceTable.getSelectedRow();
                if (row >= 0) {
                    selectEmployee(tableModel.getValueAt(row, 0).toString());
                    selectDepartment(tableModel.getValueAt(row, 1).toString());
                    totalWorkDaysField.setText(tableModel.getValueAt(row, 2).toString());
                    daysPresentField.setText(tableModel.getValueAt(row, 3).toString());
                    percentageLabel.setText("Attendance: " + tableModel.getValueAt(row, 4) + "%");
                    statusLabel.setText("Status: " + tableModel.getValueAt(row, 5));
                }
            }
        });
        panel.add(new JScrollPane(attendanceTable), BorderLayout.CENTER);
        return panel;
    }

    private void refreshOptions() {
        employeeCombo.removeAllItems();
        for (Employee employee : employeeService.getAllEmployees()) {
            employeeCombo.addItem(employee);
        }
        departmentCombo.removeAllItems();
        for (Department department : departmentService.getAllDepartments()) {
            departmentCombo.addItem(department);
        }
    }

    private Attendance readAttendance() {
        Employee employee = (Employee) employeeCombo.getSelectedItem();
        Department department = (Department) departmentCombo.getSelectedItem();
        if (employee == null || department == null) {
            throw new IllegalArgumentException("Select an employee and department");
        }
        try {
            return new Attendance(employee.getEmployeeCode(), department.getDepartmentCode(),
                    Integer.parseInt(totalWorkDaysField.getText().trim()),
                    Integer.parseInt(daysPresentField.getText().trim()));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Work days must be whole numbers");
        }
    }

    private void calculate() {
        try {
            Attendance attendance = readAttendance();
            percentageLabel.setText(String.format("Attendance: %.2f%%", attendance.calculatePercentage()));
            statusLabel.setText("Status: " + attendance.getAttendanceStatus());
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private void save() {
        try {
            attendanceService.addAttendance(readAttendance());
            clearForm();
            refreshTable();
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private void update() {
        try {
            Attendance attendance = readAttendance();
            attendanceService.updateAttendance(attendance.getEmployeeCode(), attendance.getDepartmentCode(),
                    attendance.getTotalWorkDays(), attendance.getDaysPresent());
            refreshTable();
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Attendance attendance : attendanceService.getAllAttendance()) {
            tableModel.addRow(new Object[]{attendance.getEmployeeCode(), attendance.getDepartmentCode(),
                    attendance.getTotalWorkDays(), attendance.getDaysPresent(),
                    String.format("%.2f", attendance.calculatePercentage()), attendance.getAttendanceStatus()});
        }
    }

    public void refreshData() {
        refreshOptions();
        refreshTable();
    }

    private void selectEmployee(String code) {
        for (int index = 0; index < employeeCombo.getItemCount(); index++) {
            if (employeeCombo.getItemAt(index).getEmployeeCode().equals(code)) {
                employeeCombo.setSelectedIndex(index);
                return;
            }
        }
    }

    private void selectDepartment(String code) {
        for (int index = 0; index < departmentCombo.getItemCount(); index++) {
            if (departmentCombo.getItemAt(index).getDepartmentCode().equals(code)) {
                departmentCombo.setSelectedIndex(index);
                return;
            }
        }
    }

    private void clearForm() {
        totalWorkDaysField.setText("");
        daysPresentField.setText("");
        percentageLabel.setText("Attendance: -");
        statusLabel.setText("Status: -");
    }

    private void showError(Exception exception) {
        JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}