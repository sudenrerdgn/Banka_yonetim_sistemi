package gui;

import app.BankaUygulamasi;
import model.Sube;

import javax.swing.*;
import java.awt.*;

/**
 * Banka Yönetim Sistemi GUI Başlatıcı
 */
public class GUIBaslat {

    public static void main(String[] args) {
        // Look and Feel ayarla
        try {
            // Nimbus tema (daha modern görünüm)
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            // Ek UI özelleştirmeleri
            UIManager.put("control", BankaTema.BACKGROUND);
            UIManager.put("nimbusBase", BankaTema.PRIMARY);
            UIManager.put("nimbusBlueGrey", BankaTema.BACKGROUND);
            UIManager.put("text", BankaTema.TEXT_PRIMARY);

        } catch (Exception e) {
            // Varsayılan L&F kullan
            System.out.println("Nimbus tema yüklenemedi, varsayılan kullanılıyor.");
        }

        // Anti-aliasing (font düzgünleştirme)
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        // Başlangıç verilerini hazırla
        if (BankaUygulamasi.getSubeler().isEmpty()) {
            BankaUygulamasi.getSubeler().add(new Sube(1, "Merkez Şube"));
            System.out.println("✓ Varsayılan şube oluşturuldu: Merkez Şube");
        }

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║    Banka Yönetim Sistemi Başlıyor      ║");
        System.out.println("╚════════════════════════════════════════╝");

        // GUI'yi Event Dispatch Thread'de başlat
        SwingUtilities.invokeLater(() -> {
            // Splash Screen (opsiyonel)
            JWindow splash = splashEkraniGoster();

            // Ana menüyü aç
            Timer timer = new Timer(1500, e -> {
                splash.dispose();
                BankaMenuGUI anaMenu = new BankaMenuGUI();
                anaMenu.setVisible(true);
            });
            timer.setRepeats(false);
            timer.start();
        });
    }

    /**
     * Açılış splash ekranı gösterir
     */
    private static JWindow splashEkraniGoster() {
        JWindow splash = new JWindow();

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(BankaTema.PRIMARY);
        content.setBorder(BorderFactory.createLineBorder(BankaTema.PRIMARY_DARK, 3));

        // Logo
        JLabel logoLabel = new JLabel("");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Başlık
        JLabel titleLabel = new JLabel("Banka Yönetim Sistemi");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Alt başlık
        JLabel subtitleLabel = new JLabel("Yükleniyor...");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(200, 220, 240));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Progress bar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setMaximumSize(new Dimension(200, 8));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(Box.createVerticalStrut(40));
        content.add(logoLabel);
        content.add(Box.createVerticalStrut(15));
        content.add(titleLabel);
        content.add(Box.createVerticalStrut(10));
        content.add(subtitleLabel);
        content.add(Box.createVerticalStrut(20));
        content.add(progressBar);
        content.add(Box.createVerticalStrut(40));

        splash.setContentPane(content);
        splash.setSize(350, 280);
        splash.setLocationRelativeTo(null);
        splash.setVisible(true);

        return splash;
    }
}
