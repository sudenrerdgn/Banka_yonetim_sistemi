package gui;

import app.BankaUygulamasi;
import model.Sube;

import javax.swing.*;
import java.awt.*;

public class SubeEkleGUI extends JFrame {

    private JTextField kodField;
    private JTextField adField;

    public SubeEkleGUI() {
        BankaTema.pencereAyarla(this, "Yeni Şube Ekle", 500, 400);
        setLayout(new BorderLayout());

        // Başlık
        add(BankaTema.baslikOlustur("🏢 Yeni Şube Ekle"), BorderLayout.NORTH);

        // Ana Panel
        JPanel cardPanel = BankaTema.kartPanelOlustur();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        // Form alanları
        kodField = BankaTema.textFieldOlustur(15);
        adField = BankaTema.textFieldOlustur(15);

        // Form satırları
        cardPanel.add(BankaTema.formSatiriOlustur("Şube Kodu:", kodField));
        cardPanel.add(BankaTema.formSatiriOlustur("Şube Adı:", adField));

        // Bilgi notu
        JPanel notePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        notePanel.setBackground(new Color(232, 245, 253));
        notePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BankaTema.PRIMARY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        JLabel noteLabel = new JLabel("ℹ️ Şube kodu benzersiz bir sayı olmalıdır.");
        noteLabel.setFont(BankaTema.FONT_SMALL);
        noteLabel.setForeground(BankaTema.PRIMARY_DARK);
        notePanel.add(noteLabel);
        cardPanel.add(Box.createVerticalStrut(20));
        cardPanel.add(notePanel);

        // Orta wrapper
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setBackground(BankaTema.BACKGROUND);
        centerWrapper.add(cardPanel);
        add(centerWrapper, BorderLayout.CENTER);

        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.setBackground(BankaTema.BACKGROUND);

        JButton ekleBtn = BankaTema.butonOlustur("✓ Şube Ekle", BankaTema.SUCCESS);
        JButton iptalBtn = BankaTema.butonOlustur("✗ İptal", BankaTema.DANGER);

        ekleBtn.addActionListener(e -> subeEkle());
        iptalBtn.addActionListener(e -> dispose());

        buttonPanel.add(ekleBtn);
        buttonPanel.add(iptalBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void subeEkle() {
        String kodStr = kodField.getText().trim();
        String ad = adField.getText().trim();

        // Validasyon
        if (kodStr.isEmpty() || ad.isEmpty()) {
            BankaTema.uyariMesaji(this, "Tüm alanlar doldurulmalıdır!");
            return;
        }

        try {
            int kod = Integer.parseInt(kodStr);

            // Kod kontrolü
            for (Sube s : BankaUygulamasi.getSubeler()) {
                if (s.getSubeKodu() == kod) {
                    BankaTema.hataMesaji(this, "Bu şube kodu zaten kullanılıyor!");
                    return;
                }
            }

            // Şube ekle
            Sube yeniSube = new Sube(kod, ad);
            BankaUygulamasi.getSubeler().add(yeniSube);

            BankaTema.basariMesaji(this,
                    "Şube başarıyla eklendi!\n\n" +
                            "Şube Kodu: " + kod + "\n" +
                            "Şube Adı: " + ad);
            dispose();

        } catch (NumberFormatException ex) {
            BankaTema.hataMesaji(this, "Şube kodu sayısal bir değer olmalıdır!");
        }
    }
}
