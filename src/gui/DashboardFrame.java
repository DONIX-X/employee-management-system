package gui;

import data.AppSession;
import java.awt.*;
import javax.swing.*;
import service.AttendanceService;
import service.DepartmentService;
import service.EmployeeService;
import service.SalaryService;
import util.AppTheme;

/**
 * DashboardFrame - Main dashboard for the Employee Management System.
 * Demonstrates:
 * - Swing GUI with CardLayout for switching between panels
 * - Navigation/sidebar layout
 * - Integration of all services
 * - Dashboard statistics
 */
public class DashboardFrame extends JFrame {
    private AttendanceService attendanceService;
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private SalaryService salaryService;

    private CardLayout cardLayout;
    private JPanel contentPanel;
    private EmployeePanel employeePanel;
    private DepartmentPanel departmentPanel;
    private SalaryPanel salaryPanel;
    private EmployeeAttendancePanel employeeAttendancePanel;
    private JLabel statsDepartmentsLabel;
    private JLabel statsPayrollLabel;
    private JLabel statsAttendanceLabel;
    private JLabel statsEmployeesLabel;

    public DashboardFrame() {
        AppTheme.setDarkMode(false);

        setTitle("Employee Management System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setLocationRelativeTo(null);
        setResizable(true);

        AppSession.initialize();
        attendanceService = AppSession.getAttendanceService();
        employeeService = AppSession.getEmployeeService();
        departmentService = AppSession.getDepartmentService();
        salaryService = AppSession.getSalaryService();

        createUI();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void rebuildUI() {
        getContentPane().removeAll();
        createUI();
        revalidate();
        repaint();
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(AppTheme.BACKGROUND);

        JPanel sidebarPanel = createSidebarPanel();
        mainPanel.add(sidebarPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(createDashboardPanel(), "Dashboard");
        employeePanel = new EmployeePanel(employeeService, departmentService);
        departmentPanel = new DepartmentPanel(departmentService);
        salaryPanel = new SalaryPanel(employeeService, salaryService);
        employeeAttendancePanel = new EmployeeAttendancePanel(employeeService, departmentService, attendanceService);
        contentPanel.add(employeePanel, "Employees");
        contentPanel.add(departmentPanel, "Departments");
        contentPanel.add(salaryPanel, "Payroll");
        contentPanel.add(employeeAttendancePanel, "Attendance");
        contentPanel.add(new EmployeeReportPanel(employeeService, departmentService, salaryService, attendanceService), "Reports");
        contentPanel.setBackground(AppTheme.BACKGROUND);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

   private JPanel createSidebarPanel() {

    JPanel sidebar = new JPanel();

    sidebar.setLayout(
        new BoxLayout(
            sidebar,
            BoxLayout.Y_AXIS
        )
    );

    sidebar.setBackground(
        AppTheme.NAVY
    );

    sidebar.setBorder(
        BorderFactory.createEmptyBorder(
            30, 20, 25, 20
        )
    );

    JLabel logoLabel = new JLabel("EMS");
    logoLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
    logoLabel.setForeground(AppTheme.WHITE);
    logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    sidebar.add(logoLabel);

    JLabel subtitleLabel = new JLabel("EMPLOYEE MANAGEMENT");
    subtitleLabel.setFont(AppTheme.SMALL_FONT);
    subtitleLabel.setForeground(new Color(191, 219, 254));
    subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    sidebar.add(subtitleLabel);

    sidebar.add(Box.createVerticalStrut(35));

    String[] menuItems = {"Dashboard", "Employees", "Departments", "Payroll", "Attendance", "Reports"};

    for (String item : menuItems) {
        JButton button = new JButton(item);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(205, 45));
        button.setPreferredSize(new Dimension(205, 45));
        button.setFont(AppTheme.BUTTON_FONT);
        button.setForeground(AppTheme.WHITE);
        button.setBackground(AppTheme.NAVY_LIGHT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(96, 165, 250, 60), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.addActionListener(e -> {
            cardLayout.show(contentPanel, item);
            refreshPanel(item);
            updateDashboardStats();
        });

        sidebar.add(button);
        sidebar.add(Box.createVerticalStrut(10));
    }

    sidebar.add(Box.createVerticalGlue());

    JButton logoutButton = new JButton("Logout");
    logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    logoutButton.setMaximumSize(new Dimension(205, 45));
    logoutButton.setPreferredSize(new Dimension(205, 45));
    logoutButton.setFont(AppTheme.BUTTON_FONT);
    logoutButton.setForeground(AppTheme.WHITE);
    logoutButton.setBackground(AppTheme.BURGUNDY);
    logoutButton.setFocusPainted(false);
    logoutButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
    logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    logoutButton.setOpaque(true);
    logoutButton.setContentAreaFilled(true);
    logoutButton.setBorderPainted(false);
    logoutButton.addActionListener(e -> {
        new LoginFrame();
        this.dispose();
    });

    sidebar.add(Box.createVerticalStrut(12));
    sidebar.add(logoutButton);
    sidebar.add(Box.createVerticalStrut(8));

    return sidebar;
}

    private JPanel createDashboardPanel() {

    JPanel dashboardPanel = new JPanel(new BorderLayout(0, 25));
    dashboardPanel.setBackground(AppTheme.BACKGROUND);
    dashboardPanel.setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));

    JPanel headerPanel = new JPanel(new BorderLayout());
    headerPanel.setBackground(AppTheme.BACKGROUND);

    JPanel headerTextPanel = new JPanel();
    headerTextPanel.setLayout(new BoxLayout(headerTextPanel, BoxLayout.Y_AXIS));
    headerTextPanel.setBackground(AppTheme.BACKGROUND);

    JLabel welcomeLabel = new JLabel("Employee Management");
    welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
    welcomeLabel.setForeground(AppTheme.TEXT);

    JLabel descriptionLabel = new JLabel("Manage your people, departments and payroll");
    descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
    descriptionLabel.setForeground(AppTheme.TEXT_LIGHT);

    headerTextPanel.add(welcomeLabel);
    headerTextPanel.add(Box.createVerticalStrut(7));
    headerTextPanel.add(descriptionLabel);
    headerPanel.add(headerTextPanel, BorderLayout.WEST);

    JPanel accentPanel = new JPanel();
    accentPanel.setPreferredSize(new Dimension(8, 65));
    accentPanel.setBackground(AppTheme.BURGUNDY);
    accentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    headerPanel.add(accentPanel, BorderLayout.EAST);
    dashboardPanel.add(headerPanel, BorderLayout.NORTH);

    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setBackground(AppTheme.BACKGROUND);

    JLabel overviewLabel = new JLabel("Overview");
    overviewLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
    overviewLabel.setForeground(AppTheme.TEXT);
    overviewLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    centerPanel.add(overviewLabel);
    centerPanel.add(Box.createVerticalStrut(15));

    JPanel statsPanel = new JPanel(new GridLayout(1, 4, 18, 0));
    statsPanel.setBackground(AppTheme.BACKGROUND);
    statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    statsPanel.add(createStatsCard("TOTAL EMPLOYEES", "0", AppTheme.NAVY));
    statsPanel.add(createStatsCard("DEPARTMENTS", "0", AppTheme.BURGUNDY));
    statsPanel.add(createStatsCard("TOTAL PAYROLL", "0.00", AppTheme.NAVY));
    statsPanel.add(createStatsCard("ATTENDANCE", "0%", AppTheme.BURGUNDY));

    centerPanel.add(statsPanel);
    centerPanel.add(Box.createVerticalStrut(30));

    JLabel quickAccessLabel = new JLabel("Quick Access");
    quickAccessLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
    quickAccessLabel.setForeground(AppTheme.TEXT);
    quickAccessLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    centerPanel.add(quickAccessLabel);
    centerPanel.add(Box.createVerticalStrut(15));

    JPanel quickPanel = new JPanel(new GridLayout(1, 3, 18, 0));
    quickPanel.setBackground(AppTheme.BACKGROUND);
    quickPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    quickPanel.add(createQuickAccessCard("Employees", "Manage employee information", "Employees"));
    quickPanel.add(createQuickAccessCard("Departments", "Manage company departments", "Departments"));
    quickPanel.add(createQuickAccessCard("Payroll", "Manage employee salaries", "Payroll"));

    centerPanel.add(quickPanel);
    dashboardPanel.add(centerPanel, BorderLayout.CENTER);

    return dashboardPanel;
}

   private JPanel createQuickAccessCard(
        String title,
        String description,
        String panelName) {

    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(AppTheme.SURFACE);
    card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(AppTheme.BORDER, 1),
            BorderFactory.createEmptyBorder(22, 22, 22, 22)));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(AppTheme.LARGE_TITLE_FONT.deriveFont(18f));
    titleLabel.setForeground(AppTheme.TEXT);

    JLabel descriptionLabel = new JLabel(description);
    descriptionLabel.setFont(AppTheme.SUBTITLE_FONT);
    descriptionLabel.setForeground(AppTheme.TEXT_LIGHT);

    JButton openButton = new JButton("Open");
    openButton.setFont(new Font("SansSerif", Font.BOLD, 12));
    openButton.setForeground(AppTheme.WHITE);
    openButton.setBackground(AppTheme.BURGUNDY);
    openButton.setFocusPainted(false);
    openButton.setBorder(BorderFactory.createEmptyBorder(9, 18, 9, 18));
    openButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    openButton.setOpaque(true);
    openButton.setContentAreaFilled(true);
    openButton.setBorderPainted(false);
    openButton.addActionListener(e -> {
        cardLayout.show(contentPanel, panelName);
        refreshPanel(panelName);
        updateDashboardStats();
    });

    card.add(titleLabel);
    card.add(Box.createVerticalStrut(8));
    card.add(descriptionLabel);
    card.add(Box.createVerticalGlue());
    card.add(openButton);

    return card;
}
private JPanel createStatsCard(
        String title,
        String value,
        Color accentColor) {

    JPanel card = new JPanel(new BorderLayout(0, 10));
    card.setBackground(AppTheme.SURFACE);
    card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 4, 0, 0, accentColor),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
    titleLabel.setForeground(AppTheme.TEXT_LIGHT);

    JLabel valueLabel = new JLabel(value);
    valueLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
    valueLabel.setForeground(AppTheme.TEXT);

    if (title.contains("EMPLOYEES")) {

        statsEmployeesLabel = valueLabel;

    } else if (title.contains("DEPARTMENTS")) {

        statsDepartmentsLabel = valueLabel;

    } else if (title.contains("PAYROLL")) {

        statsPayrollLabel = valueLabel;

    } else if (title.contains("ATTENDANCE")) {

        statsAttendanceLabel = valueLabel;
    }


    card.add(
        titleLabel,
        BorderLayout.NORTH
    );

    card.add(
        valueLabel,
        BorderLayout.CENTER
    );


    return card;
}

    private void updateDashboardStats() {
        if (statsEmployeesLabel != null) {
            statsEmployeesLabel.setText(String.valueOf(employeeService.getTotalEmployees()));
        }
        if (statsDepartmentsLabel != null) {
            statsDepartmentsLabel.setText(String.valueOf(departmentService.getTotalDepartments()));
        }
        if (statsPayrollLabel != null) {
            statsPayrollLabel.setText(String.format("%.2f", salaryService.getTotalPayroll(java.time.YearMonth.now().toString())));
        }
        if (statsAttendanceLabel != null) {
            try {
                double avgAttendance = calculateAverageAttendance();
                statsAttendanceLabel.setText(String.format("%.1f%%", avgAttendance));
            } catch (Exception e) {
                statsAttendanceLabel.setText("N/A");
            }
        }
    }

    private double calculateAverageAttendance() {
        try {
            var allAttendances = attendanceService.getAllAttendance();
            if (allAttendances.isEmpty()) return 0;
            
            double total = 0;
            for (var att : allAttendances) {
                total += att.calculatePercentage();
            }
            return total / allAttendances.size();
        } catch (Exception e) {
            return 0;
        }
    }

    private void refreshPanel(String item) {
        if ("Employees".equals(item)) {
            employeePanel.refreshData();
        } else if ("Departments".equals(item)) {
            departmentPanel.refreshData();
        } else if ("Payroll".equals(item)) {
            salaryPanel.refreshData();
        } else if ("Attendance".equals(item)) {
            employeeAttendancePanel.refreshData();
        }
    }
}
