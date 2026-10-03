package util;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

public final class ThemeUtil {

    private ThemeUtil() {
    }

    // =========================================================
    // LABEL
    // =========================================================

    public static void styleLabel(JLabel label) {

        label.setFont(AppTheme.BODY_FONT);
        label.setForeground(AppTheme.TEXT);
    }

    public static void styleTitle(JLabel label) {

        label.setFont(AppTheme.TITLE_FONT);
        label.setForeground(AppTheme.NAVY);
    }

    public static void styleSectionTitle(JLabel label) {

        label.setFont(AppTheme.SECTION_FONT);
        label.setForeground(AppTheme.NAVY);
    }

    // =========================================================
    // TEXT FIELD
    // =========================================================

    public static void styleTextField(JTextField field) {

        field.setFont(AppTheme.BODY_FONT);
        field.setForeground(AppTheme.TEXT);
        field.setBackground(AppTheme.WHITE);

        field.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER),
                new EmptyBorder(8, 10, 8, 10)
            )
        );
    }

    // =========================================================
    // PASSWORD FIELD
    // =========================================================

    public static void stylePasswordField(JPasswordField field) {

        field.setFont(AppTheme.BODY_FONT);
        field.setForeground(AppTheme.TEXT);
        field.setBackground(AppTheme.WHITE);

        field.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER),
                new EmptyBorder(8, 10, 8, 10)
            )
        );
    }

    // =========================================================
    // TEXT AREA
    // =========================================================

    public static void styleTextArea(JTextArea area) {

        area.setFont(AppTheme.BODY_FONT);
        area.setForeground(AppTheme.TEXT);
        area.setBackground(AppTheme.WHITE);

        area.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.BORDER),
                new EmptyBorder(8, 10, 8, 10)
            )
        );
    }

    // =========================================================
    // PRIMARY BUTTON
    // =========================================================

    public static void stylePrimaryButton(JButton button) {

        button.setFont(AppTheme.BUTTON_FONT);
        button.setForeground(AppTheme.WHITE);
        button.setBackground(AppTheme.BURGUNDY);

        button.setFocusPainted(false);
        button.setBorder(
            BorderFactory.createEmptyBorder(
                10, 18, 10, 18
            )
        );

        button.setOpaque(true);
    }

    // =========================================================
    // SECONDARY BUTTON
    // =========================================================

    public static void styleSecondaryButton(JButton button) {

        button.setFont(AppTheme.BUTTON_FONT);
        button.setForeground(AppTheme.NAVY);
        button.setBackground(AppTheme.WHITE);

        button.setFocusPainted(false);

        button.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                    AppTheme.BORDER
                ),
                new EmptyBorder(
                    9, 17, 9, 17
                )
            )
        );

        button.setOpaque(true);
    }

    // =========================================================
    // DANGER BUTTON
    // =========================================================

    public static void styleDangerButton(JButton button) {

        button.setFont(AppTheme.BUTTON_FONT);
        button.setForeground(AppTheme.WHITE);
        button.setBackground(AppTheme.BURGUNDY_DARK);

        button.setFocusPainted(false);

        button.setBorder(
            BorderFactory.createEmptyBorder(
                10, 18, 10, 18
            )
        );

        button.setOpaque(true);
    }

    // =========================================================
    // COMBO BOX
    // =========================================================

    public static void styleComboBox(JComboBox<?> comboBox) {

        comboBox.setFont(AppTheme.BODY_FONT);
        comboBox.setForeground(AppTheme.TEXT);
        comboBox.setBackground(AppTheme.WHITE);

        comboBox.setBorder(
            BorderFactory.createLineBorder(
                AppTheme.BORDER
            )
        );
    }

    // =========================================================
    // TABLE
    // =========================================================

    public static void styleTable(JTable table) {

        table.setFont(AppTheme.BODY_FONT);
        table.setForeground(AppTheme.TEXT);
        table.setBackground(AppTheme.WHITE);

        table.setRowHeight(34);

        table.setShowGrid(false);

        table.setIntercellSpacing(
            new java.awt.Dimension(0, 0)
        );

        JTableHeader header = table.getTableHeader();

        header.setFont(AppTheme.BUTTON_FONT);
        header.setForeground(AppTheme.WHITE);
        header.setBackground(AppTheme.NAVY);

        header.setOpaque(true);

        header.setPreferredSize(
            new java.awt.Dimension(0, 40)
        );
    }

    // =========================================================
    // SCROLL PANE
    // =========================================================

    public static void styleScrollPane(
            JScrollPane scrollPane) {

        scrollPane.setBorder(
            BorderFactory.createLineBorder(
                AppTheme.BORDER
            )
        );

        scrollPane.getViewport()
                  .setBackground(AppTheme.WHITE);
    }
}