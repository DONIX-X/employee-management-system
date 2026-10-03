package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import util.AppTheme;

/**
 * LoginFrame - The login screen for the Employee Management System.
 * Demonstrates:
 * - Java Swing GUI components
 * - Event handling
 * - Simple authentication (hardcoded demo credentials)
 * 
 * Demo credentials:
 * Username: admin
 * Password: admin123
 */
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    private JLabel messageLabel;

    // Demo credentials
    private static final String DEMO_USERNAME = "admin";
    private static final String DEMO_PASSWORD = "admin123";

    public LoginFrame() {
        setTitle("Employee Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setMinimumSize(new Dimension(500, 620));
        setPreferredSize(new Dimension(500, 620));

        AppTheme.setDarkMode(false);
        createLoginPanel();
        pack();
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void createLoginPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(AppTheme.BACKGROUND);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(AppTheme.NAVY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(28, 24, 28, 24));
        headerPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Employee Management System");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(AppTheme.WHITE);

        JLabel subtitleLabel = new JLabel("Secure access portal");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(148, 163, 184));

        JPanel titleContainer = new JPanel();
        titleContainer.setOpaque(false);
        titleContainer.setLayout(new BoxLayout(titleContainer, BoxLayout.Y_AXIS));
        titleContainer.add(titleLabel);
        titleContainer.add(Box.createVerticalStrut(6));
        titleContainer.add(subtitleLabel);
        headerPanel.add(titleContainer, BorderLayout.CENTER);

        JPanel loginContainer = new JPanel(new GridBagLayout());
        loginContainer.setBackground(AppTheme.BACKGROUND);
        loginContainer.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 15, 0);
        gbc.weightx = 1.0;

        JLabel demoLabel = new JLabel("<html><b>Demo credentials:</b><br/>Username: admin<br/>Password: admin123</html>");
        demoLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        demoLabel.setForeground(AppTheme.TEXT_LIGHT);
        loginContainer.add(demoLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        usernameLabel.setForeground(AppTheme.TEXT);
        loginContainer.add(usernameLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 12, 0);
        usernameField = new JTextField();
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        usernameField.setPreferredSize(new Dimension(260, 38));
        usernameField.setMargin(new Insets(6, 8, 6, 8));
        usernameField.setBackground(AppTheme.SURFACE);
        usernameField.setForeground(AppTheme.TEXT);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        loginContainer.add(usernameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 10, 0);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        passwordLabel.setForeground(AppTheme.TEXT);
        loginContainer.add(passwordLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 12, 0);
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setPreferredSize(new Dimension(260, 38));
        passwordField.setMargin(new Insets(6, 8, 6, 8));
        passwordField.setBackground(AppTheme.SURFACE);
        passwordField.setForeground(AppTheme.TEXT);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        loginContainer.add(passwordField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 12, 0);
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        messageLabel.setForeground(new Color(185, 28, 28));
        loginContainer.add(messageLabel, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setOpaque(false);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        loginButton.setPreferredSize(new Dimension(140, 42));
        loginButton.setBackground(new Color(37, 99, 235));
        loginButton.setForeground(Color.BLACK);
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(true);
        loginButton.setContentAreaFilled(true);
        loginButton.setBorderPainted(true);
        loginButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(29, 78, 216), 1),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(this::handleLogin);

        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        exitButton.setPreferredSize(new Dimension(140, 42));
        exitButton.setBackground(new Color(148, 163, 184));
        exitButton.setForeground(new Color(15, 23, 42));
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(true);
        exitButton.setContentAreaFilled(true);
        exitButton.setBorderPainted(true);
        exitButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(148, 163, 184), 1),
                BorderFactory.createEmptyBorder(10, 18, 10, 18)));
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        gbc.gridy++;
        gbc.insets = new Insets(8, 0, 0, 0);
        loginContainer.add(buttonPanel, gbc);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(loginContainer, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter both username and password");
            return;
        }

        if (DEMO_USERNAME.equals(username) && DEMO_PASSWORD.equals(password)) {
            messageLabel.setText("");

            JButton okButton = new JButton("OK");
            okButton.setForeground(Color.BLACK);
            okButton.setBackground(new Color(224, 231, 255));
            okButton.setFocusPainted(false);
            okButton.setOpaque(true);
            okButton.setBorderPainted(true);
            okButton.setPreferredSize(new Dimension(90, 30));

            UIManager.put("OptionPane.buttonFont", new Font("SansSerif", Font.BOLD, 12));
            UIManager.put("OptionPane.messageForeground", AppTheme.TEXT);

            JOptionPane optionPane = new JOptionPane(
                    "Login successful!",
                    JOptionPane.INFORMATION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    new Object[]{okButton},
                    okButton);
            JDialog dialog = optionPane.createDialog(this, "Success");
            okButton.addActionListener(e1 -> dialog.dispose());
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);

            new DashboardFrame();
            this.dispose();
        } else {
            messageLabel.setText("Invalid username or password");
            passwordField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
