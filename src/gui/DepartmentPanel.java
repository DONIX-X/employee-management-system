package gui;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Department;
import service.DepartmentService;
import util.AppTheme;
import util.ValidationUtil;

/**
 * DepartmentPanel - GUI for department CRUD and search.
 */
public class DepartmentPanel extends JPanel {
    private DepartmentService departmentService;
    private JTable departmentTable;
    private DefaultTableModel tableModel;
    private JTextField codeField;
    private JTextField nameField;
    private JTextField locationField;
    private JTextField descriptionField;
    private JTextField searchField;

    public DepartmentPanel(DepartmentService departmentService) {
        this.departmentService = departmentService;
        setLayout(new BorderLayout());
        setBackground(AppTheme.darkMode ? AppTheme.BACKGROUND : AppTheme.SURFACE_ALT);
        add(createHeader(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.WEST);
        add(createTable(), BorderLayout.CENTER);
        refreshTable();
    }

    private JPanel createHeader() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(AppTheme.darkMode ? AppTheme.NAVY : new Color(20, 115, 95));
        JLabel title = new JLabel("Department Management");
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
        searchField = addField(panel, "Search code or name:");
        panel.add(createButton("Search", e -> search()));
        panel.add(Box.createVerticalStrut(12));
        codeField = addField(panel, "Department Code:");
        nameField = addField(panel, "Department Name:");
        locationField = addField(panel, "Location:");
        descriptionField = addField(panel, "Description:");
        JPanel buttons = new JPanel(new GridLayout(2, 2, 5, 5));
        buttons.setMaximumSize(new Dimension(250, 80));
        buttons.add(createButton("Add", e -> addDepartment()));
        buttons.add(createButton("Update", e -> updateDepartment()));
        buttons.add(createButton("Delete", e -> deleteDepartment()));
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
        tableModel = new DefaultTableModel(new String[]{"Code", "Department", "Location", "Description"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        departmentTable = new JTable(tableModel);
        departmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        departmentTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent event) {
                int row = departmentTable.getSelectedRow();
                if (row >= 0) {
                    codeField.setText(tableModel.getValueAt(row, 0).toString());
                    nameField.setText(tableModel.getValueAt(row, 1).toString());
                    locationField.setText(tableModel.getValueAt(row, 2).toString());
                    descriptionField.setText(tableModel.getValueAt(row, 3).toString());
                }
            }
        });
        panel.add(new JScrollPane(departmentTable), BorderLayout.CENTER);
        return panel;
    }

    private Department readDepartment() {
        if (!ValidationUtil.isNotEmpty(codeField.getText()) || !ValidationUtil.isNotEmpty(nameField.getText())) {
            throw new IllegalArgumentException("Department code and name are required");
        }
        return new Department(codeField.getText().trim(), nameField.getText().trim(),
                locationField.getText().trim(), descriptionField.getText().trim());
    }

    private void addDepartment() {
        try {
            departmentService.addDepartment(readDepartment());
            clearForm();
            refreshTable();
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private void updateDepartment() {
        try {
            departmentService.updateDepartment(readDepartment());
            clearForm();
            refreshTable();
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private void deleteDepartment() {
        try {
            departmentService.deleteDepartment(codeField.getText().trim());
            clearForm();
            refreshTable();
        } catch (Exception exception) {
            showError(exception);
        }
    }

    private void search() {
        tableModel.setRowCount(0);
        for (Department department : departmentService.searchDepartment(searchField.getText())) {
            addRow(department);
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Department department : departmentService.getAllDepartments()) {
            addRow(department);
        }
    }

    public void refreshData() {
        refreshTable();
    }

    private void addRow(Department department) {
        tableModel.addRow(new Object[]{department.getDepartmentCode(), department.getName(),
                department.getLocation(), department.getDescription()});
    }

    private void clearForm() {
        codeField.setText("");
        nameField.setText("");
        locationField.setText("");
        descriptionField.setText("");
        searchField.setText("");
    }

    private void showError(Exception exception) {
        JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}