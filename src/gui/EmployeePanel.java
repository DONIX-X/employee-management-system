package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Department;
import model.Employee;
import service.DepartmentService;
import service.EmployeeService;
import util.AppTheme;
import util.ValidationUtil;

/**
 * EmployeePanel - GUI for employee management (Add, Update, Delete, Search).
 */
public class EmployeePanel extends JPanel {
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private JTable employeeTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;
    private JTextField idField, nameField, ageField, phoneField, emailField;
    private JTextField employeeCodeField, designationField;
    private JComboBox<Department> departmentCombo;

    public EmployeePanel(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        setLayout(new BorderLayout());
        setBackground(AppTheme.darkMode ? AppTheme.BACKGROUND : new Color(236, 240, 241));

        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.WEST);
        add(createTablePanel(), BorderLayout.CENTER);
        refreshTable();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppTheme.darkMode ? AppTheme.NAVY : new Color(15, 118, 110));
        panel.setPreferredSize(new Dimension(getWidth(), 50));
        JLabel label = new JLabel("Employee Management");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(label, BorderLayout.WEST);
        return panel;
    }

    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(AppTheme.darkMode ? AppTheme.BACKGROUND : new Color(236, 240, 241));
        panel.setPreferredSize(new Dimension(245, getHeight()));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));

        JLabel searchLabel = new JLabel("SEARCH");
        searchLabel.setFont(new Font("Arial", Font.BOLD, 12));
        searchLabel.setForeground(AppTheme.TEXT);
        panel.add(searchLabel);
        searchTypeCombo = new JComboBox<>(new String[]{"Employee ID", "Name or Code"});
        searchTypeCombo.setPreferredSize(new Dimension(200, 28));
        searchTypeCombo.setMaximumSize(new Dimension(200, 28));
        searchTypeCombo.setBackground(AppTheme.SURFACE);
        searchTypeCombo.setForeground(AppTheme.TEXT);
        panel.add(searchTypeCombo);
        searchField = addField(panel, "Search value:");
        panel.add(createButton("Search", e -> performSearch()));
        panel.add(Box.createVerticalStrut(12));

        JLabel formLabel = new JLabel("ADD/UPDATE");
        formLabel.setFont(new Font("Arial", Font.BOLD, 12));
        formLabel.setForeground(AppTheme.TEXT);
        panel.add(formLabel);
        idField = addField(panel, "Person ID:");
        idField.setEditable(false);
        nameField = addField(panel, "Name:");
        ageField = addField(panel, "Age:");
        phoneField = addField(panel, "Phone:");
        emailField = addField(panel, "Email:");
        employeeCodeField = addField(panel, "Employee Code:");
        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        departmentLabel.setForeground(AppTheme.TEXT);
        panel.add(departmentLabel);
        departmentCombo = new JComboBox<>();
        departmentCombo.setMaximumSize(new Dimension(250, 25));
        departmentCombo.setBackground(AppTheme.SURFACE);
        departmentCombo.setForeground(AppTheme.TEXT);
        refreshDepartments();
        panel.add(departmentCombo);
        designationField = addField(panel, "Designation:");

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        buttonPanel.setBackground(AppTheme.darkMode ? AppTheme.BACKGROUND : new Color(236, 240, 241));
        buttonPanel.setMaximumSize(new Dimension(250, 80));
        buttonPanel.add(createButton("Add", e -> addEmployee()));
        buttonPanel.add(createButton("Update", e -> updateEmployee()));
        buttonPanel.add(createButton("Delete", e -> deleteEmployee()));
        buttonPanel.add(createButton("Clear", e -> clearForm()));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private JTextField addField(JPanel panel, String label) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        fieldLabel.setForeground(AppTheme.TEXT);
        fieldLabel.setMaximumSize(new Dimension(250, 15));
        panel.add(fieldLabel);
        JTextField field = new JTextField(15);
        field.setPreferredSize(new Dimension(250, 22));
        field.setMaximumSize(new Dimension(250, 22));
        field.setBackground(AppTheme.SURFACE);
        field.setForeground(AppTheme.TEXT);
        field.setCaretColor(AppTheme.TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        panel.add(field);
        return field;
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setBackground(AppTheme.BURGUNDY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.addActionListener(action);
        return button;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppTheme.darkMode ? AppTheme.BACKGROUND : new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        String[] columns = {"ID", "Name", "Age", "Phone", "Email", "Employee Code", "Department", "Designation"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        employeeTable = new JTable(tableModel);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        employeeTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                int row = employeeTable.getSelectedRow();
                if (row >= 0) {
                    loadEmployeeToForm(row);
                }
            }
        });
        panel.add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        return panel;
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            refreshTable();
            return;
        }
        List<Employee> results;
        if ("Employee ID".equals(searchTypeCombo.getSelectedItem())) {
            if (!ValidationUtil.isValidInteger(searchTerm)) {
                JOptionPane.showMessageDialog(this, "Please enter a valid ID number");
                return;
            }
            results = employeeService.searchEmployee(Integer.parseInt(searchTerm));
        } else {
            results = employeeService.searchEmployee(searchTerm);
        }
        tableModel.setRowCount(0);
        for (Employee employee : results) {
            addEmployeeToTable(employee);
        }
    }

    private void addEmployee() {
        try {
            Employee employee = readEmployee(0);
            employeeService.addEmployee(employee);
            JOptionPane.showMessageDialog(this, "Employee added successfully");
            clearForm();
            refreshTable();
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private void updateEmployee() {
        try {
            if (idField.getText().isEmpty()) {
                throw new IllegalArgumentException("Select an employee to update");
            }
            Employee employee = readEmployee(Integer.parseInt(idField.getText()));
            employeeService.updateEmployee(employee);
            JOptionPane.showMessageDialog(this, "Employee updated successfully");
            clearForm();
            refreshTable();
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private void deleteEmployee() {
        try {
            if (idField.getText().isEmpty()) {
                throw new IllegalArgumentException("Select an employee to delete");
            }
            int confirm = JOptionPane.showConfirmDialog(this, "Delete this employee?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                employeeService.deleteEmployee(Integer.parseInt(idField.getText()));
                clearForm();
                refreshTable();
            }
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private Employee readEmployee(int id) {
        if (!ValidationUtil.isNotEmpty(nameField.getText())) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (!ValidationUtil.isValidInteger(ageField.getText())) {
            throw new IllegalArgumentException("Age must be a number");
        }
        if (!ValidationUtil.isValidPhone(phoneField.getText())) {
            throw new IllegalArgumentException("Phone must be 10 digits");
        }
        if (!ValidationUtil.isValidEmail(emailField.getText())) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (departmentCombo.getSelectedItem() == null) {
            throw new IllegalArgumentException("Select a department");
        }
        if (!ValidationUtil.isNotEmpty(employeeCodeField.getText())
            || !ValidationUtil.isNotEmpty(designationField.getText())) {
            throw new IllegalArgumentException("Employee code and designation are required");
        }
        return new Employee(id, nameField.getText().trim(), Integer.parseInt(ageField.getText()),
                phoneField.getText().trim(), emailField.getText().trim(), employeeCodeField.getText().trim(),
            ((Department) departmentCombo.getSelectedItem()).getDepartmentCode(), designationField.getText().trim());
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        phoneField.setText("");
        emailField.setText("");
        employeeCodeField.setText("");
        if (departmentCombo.getItemCount() > 0) {
            departmentCombo.setSelectedIndex(0);
        }
        designationField.setText("");
        searchField.setText("");
    }

    private void loadEmployeeToForm(int row) {
        idField.setText(tableModel.getValueAt(row, 0).toString());
        nameField.setText(tableModel.getValueAt(row, 1).toString());
        ageField.setText(tableModel.getValueAt(row, 2).toString());
        phoneField.setText(tableModel.getValueAt(row, 3).toString());
        emailField.setText(tableModel.getValueAt(row, 4).toString());
        employeeCodeField.setText(tableModel.getValueAt(row, 5).toString());
        String departmentCode = tableModel.getValueAt(row, 6).toString();
        for (int index = 0; index < departmentCombo.getItemCount(); index++) {
            if (departmentCombo.getItemAt(index).getDepartmentCode().equals(departmentCode)) {
                departmentCombo.setSelectedIndex(index);
                break;
            }
        }
        designationField.setText(tableModel.getValueAt(row, 7).toString());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Employee employee : employeeService.getAllEmployees()) {
            addEmployeeToTable(employee);
        }
    }

    public void refreshData() {
        refreshDepartments();
        refreshTable();
    }

    private void refreshDepartments() {
        departmentCombo.removeAllItems();
        for (Department department : departmentService.getAllDepartments()) {
            departmentCombo.addItem(department);
        }
    }

    private void addEmployeeToTable(Employee employee) {
        tableModel.addRow(new Object[]{employee.getId(), employee.getName(), employee.getAge(),
                employee.getPhone(), employee.getEmail(), employee.getEmployeeCode(), employee.getDepartmentCode(),
                employee.getDesignation()});
    }

    private void showError(Exception exception) {
        JOptionPane.showMessageDialog(this, "Error: " + exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}