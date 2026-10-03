package util;

import java.awt.Color;
import java.awt.Font;

public final class AppTheme {
    public static boolean darkMode = false;

    public static Color NAVY = new Color(15, 23, 42);
    public static Color NAVY_LIGHT = new Color(30, 41, 59);
    public static Color BURGUNDY = new Color(99, 102, 241);
    public static Color BURGUNDY_DARK = new Color(79, 70, 229);
    public static Color BURGUNDY_SOFT = new Color(224, 231, 255);
    public static Color BACKGROUND = new Color(241, 245, 249);
    public static Color SURFACE = Color.WHITE;
    public static Color SURFACE_ALT = new Color(248, 250, 252);
    public static Color TEXT = new Color(15, 23, 42);
    public static Color TEXT_LIGHT = new Color(71, 85, 105);
    public static Color TEXT_MUTED = new Color(100, 116, 139);
    public static Color BORDER = new Color(203, 213, 225);
    public static Color LIGHT_GRAY = new Color(226, 232, 240);
    public static Color WHITE = Color.WHITE;
    public static Color SUCCESS = new Color(16, 185, 129);
    public static Color WARNING = new Color(245, 158, 11);
    public static Color DANGER = new Color(239, 68, 68);

    public static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 30);
    public static final Font LARGE_TITLE_FONT = new Font("SansSerif", Font.BOLD, 36);
    public static final Font SECTION_FONT = new Font("SansSerif", Font.BOLD, 20);
    public static final Font SUBTITLE_FONT = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font BODY_FONT = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 13);
    public static final Font CARD_TITLE_FONT = new Font("SansSerif", Font.BOLD, 12);
    public static final Font CARD_VALUE_FONT = new Font("SansSerif", Font.BOLD, 28);
    public static final Font SMALL_FONT = new Font("SansSerif", Font.PLAIN, 11);

    public static void setDarkMode(boolean enabled) {
        darkMode = enabled;

        if (enabled) {
            NAVY = new Color(15, 23, 42);
            NAVY_LIGHT = new Color(30, 41, 59);
            BURGUNDY = new Color(129, 140, 248);
            BURGUNDY_DARK = new Color(99, 102, 241);
            BURGUNDY_SOFT = new Color(30, 41, 59);
            BACKGROUND = new Color(2, 6, 23);
            SURFACE = new Color(15, 23, 42);
            SURFACE_ALT = new Color(17, 24, 39);
            TEXT = new Color(248, 250, 252);
            TEXT_LIGHT = new Color(191, 219, 254);
            TEXT_MUTED = new Color(148, 163, 184);
            BORDER = new Color(51, 65, 85);
            LIGHT_GRAY = new Color(30, 41, 59);
            WHITE = Color.WHITE;
        } else {
            NAVY = new Color(15, 23, 42);
            NAVY_LIGHT = new Color(30, 41, 59);
            BURGUNDY = new Color(99, 102, 241);
            BURGUNDY_DARK = new Color(79, 70, 229);
            BURGUNDY_SOFT = new Color(224, 231, 255);
            BACKGROUND = new Color(241, 245, 249);
            SURFACE = Color.WHITE;
            SURFACE_ALT = new Color(248, 250, 252);
            TEXT = new Color(15, 23, 42);
            TEXT_LIGHT = new Color(71, 85, 105);
            TEXT_MUTED = new Color(100, 116, 139);
            BORDER = new Color(203, 213, 225);
            LIGHT_GRAY = new Color(226, 232, 240);
            WHITE = Color.WHITE;
        }

        javax.swing.UIManager.put("Button.background", BURGUNDY);
        javax.swing.UIManager.put("Button.foreground", WHITE);
        javax.swing.UIManager.put("Button.focus", new java.awt.Color(0, 0, 0, 0));
        javax.swing.UIManager.put("Panel.background", BACKGROUND);
        javax.swing.UIManager.put("TextField.background", SURFACE);
        javax.swing.UIManager.put("TextField.foreground", TEXT);
        javax.swing.UIManager.put("TextArea.background", SURFACE);
        javax.swing.UIManager.put("TextArea.foreground", TEXT);
        javax.swing.UIManager.put("Table.background", SURFACE);
        javax.swing.UIManager.put("Table.foreground", TEXT);
        javax.swing.UIManager.put("Label.foreground", TEXT);
        javax.swing.UIManager.put("ComboBox.background", SURFACE);
        javax.swing.UIManager.put("ComboBox.foreground", TEXT);
    }

    private AppTheme() {
    }
}