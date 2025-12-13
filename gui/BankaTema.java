package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Banka uygulaması için modern tema ve stil yardımcıları
 */
public class BankaTema {

    // === RENK PALETİ ===
    public static final Color PRIMARY = new Color(41, 128, 185); // Mavi
    public static final Color PRIMARY_DARK = new Color(31, 97, 141); // Koyu Mavi
    public static final Color SUCCESS = new Color(39, 174, 96); // Yeşil
    public static final Color DANGER = new Color(231, 76, 60); // Kırmızı
    public static final Color WARNING = new Color(243, 156, 18); // Turuncu
    public static final Color BACKGROUND = new Color(236, 240, 241); // Açık Gri
    public static final Color CARD_BG = Color.WHITE;
    public static final Color TEXT_PRIMARY = new Color(44, 62, 80); // Koyu Gri
    public static final Color TEXT_SECONDARY = new Color(127, 140, 141);
    public static final Color BORDER_COLOR = new Color(189, 195, 199);

    // === FONTLAR ===
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);
    // Emoji için özel font
    public static final Font FONT_EMOJI = new Font("Segoe UI Emoji", Font.PLAIN, 16);

    public static void pencereAyarla(JFrame frame, String baslik, int genislik, int yukseklik) {
        frame.setTitle(baslik);
        frame.setSize(genislik, yukseklik);
        frame.setMinimumSize(new Dimension(genislik, yukseklik));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(BACKGROUND);
        frame.setResizable(true);
    }

    public static JButton butonOlustur(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 45));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = bgColor;

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(originalColor);
            }
        });
        return btn;
    }

    public static JButton menuButonuOlustur(String text, String emoji) {
        JButton btn = new JButton(emoji + "  " + text);
        btn.setFont(FONT_NORMAL);
        btn.setForeground(TEXT_PRIMARY);
        btn.setBackground(CARD_BG);
        btn.setFocusPainted(false);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setPreferredSize(new Dimension(280, 50));

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(new Color(232, 245, 253));
                btn.setForeground(PRIMARY);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(CARD_BG);
                btn.setForeground(TEXT_PRIMARY);
            }
        });
        return btn;
    }

    public static JTextField textFieldOlustur(int columns) {
        JTextField field = new JTextField(columns);
        field.setFont(FONT_NORMAL);
        field.setPreferredSize(new Dimension(200, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return field;
    }

    public static JLabel labelOlustur(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_NORMAL);
        label.setForeground(TEXT_PRIMARY);
        return label;
    }

    public static JLabel baslikOlustur(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(FONT_TITLE);
        label.setForeground(PRIMARY);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        return label;
    }

    public static JLabel altBaslikOlustur(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(FONT_SUBTITLE);
        label.setForeground(TEXT_PRIMARY);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }

    public static <T> JComboBox<T> comboBoxOlustur(T[] items) {
        JComboBox<T> combo = new JComboBox<>(items);
        combo.setFont(FONT_NORMAL);
        combo.setPreferredSize(new Dimension(200, 35));
        combo.setBackground(Color.WHITE);
        return combo;
    }

    public static JPanel kartPanelOlustur() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)));
        return panel;
    }

    public static JPanel formSatiriOlustur(String labelText, JComponent input) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(CARD_BG);
        row.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        JLabel label = labelOlustur(labelText);
        label.setPreferredSize(new Dimension(140, 35));

        row.add(label, BorderLayout.WEST);
        row.add(input, BorderLayout.CENTER);

        return row;
    }

    public static void basariMesaji(Component parent, String mesaj) {
        JOptionPane.showMessageDialog(parent, mesaj, "✓ Başarılı", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void hataMesaji(Component parent, String mesaj) {
        JOptionPane.showMessageDialog(parent, mesaj, "✗ Hata", JOptionPane.ERROR_MESSAGE);
    }

    public static void uyariMesaji(Component parent, String mesaj) {
        JOptionPane.showMessageDialog(parent, mesaj, "⚠ Uyarı", JOptionPane.WARNING_MESSAGE);
    }

    public static void tabloStilUygula(JTable table) {
        table.setFont(FONT_NORMAL);
        table.setRowHeight(35);
        table.setGridColor(BORDER_COLOR);
        table.setSelectionBackground(PRIMARY);
        table.setSelectionForeground(Color.WHITE);
        table.getTableHeader().setFont(FONT_BUTTON);
        table.getTableHeader().setBackground(PRIMARY);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
    }

    public static JTextArea textAreaOlustur(int rows, int cols) {
        JTextArea area = new JTextArea(rows, cols);
        area.setFont(new Font("Consolas", Font.PLAIN, 13));
        area.setEditable(false);
        area.setBackground(new Color(250, 250, 250));
        area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return area;
    }
}
