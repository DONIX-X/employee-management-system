package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.time.YearMonth;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Employee;
import model.Salary;
import service.EmployeeService;
import service.SalaryService;
import util.AppTheme;

/**
 * SalaryPanel - GUI for monthly payroll records.
 */
public class SalaryPanel extends JPanel {
    private EmployeeService employeeService;
    private SalaryService salaryService;
    private JComboBox<Employee> employeeCombo;
    private JTextField payPeriodField;
    private JTextField baseSalaryField;
    private JTextField bonusField;
    private JTextField deductionsField;
    private JTable salaryTable;
    private DefaultTableModel tableModel;

    public SalaryPanel(EmployeeService employeeService, SalaryService salaryService) {
        this.employeeService = employeeService;
        this.salaryService = salaryService;
        setLayout(new BorderLayout());
        setBackground(AppTheme.darkMode ? AppTheme.BACKGROUND : AppTheme.SURFACE_ALT);
        add(createHeader(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.WEST);
        add(createTable(), BorderLayout.CENTER);
        refreshEmployees();
        refreshTable();
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppTheme.darkMode ? AppTheme.NAVY : new Color(20, 115, 95));
        JLabel title = new JLabel("Payroll Management");
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
        payPeriodField = addField(panel, "Pay Period (YYYY-MM):");
        payPeriodField.setText(YearMonth.now().toString());
        baseSalaryField = addField(panel, "Base Salary:");
        bonusField = addField(panel, "Bonus:");
        deductionsField = addField(panel, "Deductions:");
        JPanel buttons = new JPanel(new GridLayout(2, 2, 5, 5));
        buttons.setMaximumSize(new Dimension(250, 80));
        buttons.add(createButton("Add", e -> addSalary()));
        buttons.add(createButton("Update", e -> updateSalary()));
        buttons.add(createButton("Delete", e -> deleteSalary()));
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
        tableModel = new DefaultTableModel(new String[]{"Employee", "Pay Period", "Base", "Bonus", "Deductions", "Net Salary"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        salaryTable = new JTable(tableModel);
        salaryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        salaryTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                int row = salaryTable.getSelectedRow();
                if (row >= 0) {
                    selectEmployee(tableModel.getValueAt(row, 0).toString());
                    payPeriodField.setText(tableModel.getValueAt(row, 1).toString());
                    baseSalaryField.setText(tableModel.getValueAt(row, 2).toString());
                    bonusField.setText(tableModel.getValueAt(row, 3).toString());
                    deductionsField.setText(tableModel.getValueAt(row, 4).toString());
                }
            }
        });
        panel.add(new JScrollPane(salaryTable), BorderLayout.CENTER);
        return panel;
    }

    private void refreshEmployees() {
        employeeCombo.removeAllItems();
        for (Employee employee : employeeService.getAllEmployees()) {
            employeeCombo.addItem(employee);
        }
    }

    private void selectEmployee(String employeeCode) {
        for (int index = 0; index < employeeCombo.getItemCount(); index++) {
            if (employeeCombo.getItemAt(index).getEmployeeCode().equals(employeeCode)) {
                employeeCombo.setSelectedIndex(index);
                return;
            }
        }
    }

    private Salary readSalary() {
        Employee employee = (Employee) employeeCombo.getSelectedItem();
        if (employee == null) {
            throw new IllegalArgumentException("Add an employee before entering payroll");
        }
        try {
            return new Salary(employee.getEmployeeCode(), payPeriodField.getText().trim(),
                    Double.parseDouble(baseSalaryField.getText().trim()),
                    Double.parseDouble(bonusField.getText().trim()),
                    Double.parseDouble(deductionsField.getText().trim()));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Enter valid numeric salary amounts");
        }
    }

    private void addSalary() {
        try {
            salaryService.addSalary(readSalary());
            clearForm();
            refreshTable();
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private void updateSalary() {
        try {
            salaryService.updateSalary(readSalary());
            refreshTable();
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private void deleteSalary() {
        try {
            Salary salary = readSalary();
            salaryService.deleteSalary(salary.getEmployeeCode(), salary.getPayPeriod());
            clearForm();
            refreshTable();
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Salary salary : salaryService.getAllSalaries()) {
            tableModel.addRow(new Object[]{salary.getEmployeeCode(), salary.getPayPeriod(), salary.getBaseSalary(),
                    salary.getBonus(), salary.getDeductions(), salary.getNetSalary()});
        }
    }

    public void refreshData() {
        refreshEmployees();
        refreshTable();
    }

    private void clearForm() {
        if (employeeCombo.getItemCount() > 0) {
            employeeCombo.setSelectedIndex(0);
        }
        payPeriodField.setText(YearMonth.now().toString());
        baseSalaryField.setText("");
        bonusField.setText("");
        deductionsField.setText("");
    }

    private void showError(Exception exception) {
        JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}