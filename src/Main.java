import javax.swing.UIManager;
import data.AppSession;
import gui.LoginFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Main.java - Entry point for Employee Management System
 * 
 * This application demonstrates Object-Oriented Programming concepts in Java:
 * - Classes and Objects
 * - Encapsulation (private fields + getters/setters)
 * - Inheritance (Employee and specialized employee roles extend Person/Employee)
 * - Polymorphism (Parent reference to child objects)
 * - Method Overloading (multiple searchEmployee methods)
 * - Method Overriding (displayDetails() in child classes)
 * - Abstraction (abstract Person class)
 * - Interfaces (ReportGenerator interface)
 * - Collections (ArrayList for storage)
 * - Exception Handling (proper error management)
 */
public class Main {
    public static void main(String[] args) {
        try {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception exception) {
                System.err.println("Unable to apply system look and feel: " + exception.getMessage());
            }

            AppSession.initialize();
        } catch (RuntimeException exception) {
            String message = "Unable to initialize the Employee Management System.\n"
                    + exception.getMessage()
                    + "\nThe app will continue in offline demo mode if possible.";
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null, message,
                    "Initialization Error", JOptionPane.ERROR_MESSAGE));
        }

        SwingUtilities.invokeLater(() -> {
            System.out.println("Launching Employee Management System...");
            new LoginFrame();
        });
    }
}
