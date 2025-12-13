package gui;

import app.BankaUygulamasi;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HesapAcGUI extends JFrame {

    private JComboBox<Musteri> musteriComboBox;
    private JComboBox<String> hesapTuruComboBox;
    private JTextField hesapNoField;
    private JTextField ekOzellikField;
    private JLabel ekOzellikLabel;

    public HesapAcGUI() {
        BankaTema.pencereAyarla(this, "Yeni Hesap Aç", 620, 550);
        setLayout(new BorderLayout());

        // Başlık
        add(BankaTema.baslikOlustur("📁 Yeni Hesap Aç"), BorderLayout.NORTH);

        // Müşteri kontrolü
        List<Musteri> musteriler = BankaUygulamasi.getMusteriler();
        if (musteriler.isEmpty()) {
            BankaTema.hataMesaji(this, "Kayıtlı müşteri bulunamadı!\nÖnce müşteri eklemelisiniz.");
            dispose();
            return;
        }

        // Ana Panel
        JPanel cardPanel = BankaTema.kartPanelOlustur();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        // Form alanları
        musteriComboBox = BankaTema.comboBoxOlustur(musteriler.toArray(new Musteri[0]));
        hesapNoField = BankaTema.textFieldOlustur(15);

        String[] turler = { "Vadesiz Hesap", "Vadeli Hesap", "Döviz Hesap" };
        hesapTuruComboBox = BankaTema.comboBoxOlustur(turler);

        ekOzellikLabel = BankaTema.labelOlustur("Limit (TL):");
        ekOzellikField = BankaTema.textFieldOlustur(15);

        // Form satırları
        cardPanel.add(BankaTema.formSatiriOlustur("Müşteri:", musteriComboBox));
        cardPanel.add(BankaTema.formSatiriOlustur("Hesap No:", hesapNoField));
        cardPanel.add(BankaTema.formSatiriOlustur("Hesap Türü:", hesapTuruComboBox));

        // Ek özellik satırı
        JPanel ekOzellikRow = new JPanel(new BorderLayout(10, 0));
        ekOzellikRow.setBackground(BankaTema.CARD_BG);
        ekOzellikRow.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        ekOzellikLabel.setPreferredSize(new Dimension(140, 35));
        ekOzellikRow.add(ekOzellikLabel, BorderLayout.WEST);
        ekOzellikRow.add(ekOzellikField, BorderLayout.CENTER);
        cardPanel.add(ekOzellikRow);

        // Bilgi paneli
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(new Color(232, 245, 253));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BankaTema.PRIMARY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        JLabel infoLabel = new JLabel("ℹ️ Vadesiz: Limit | Vadeli: Faiz Oranı (%) | Döviz: Tip (USD, EUR)");
        infoLabel.setFont(BankaTema.FONT_SMALL);
        infoLabel.setForeground(BankaTema.PRIMARY_DARK);
        infoPanel.add(infoLabel);
        cardPanel.add(Box.createVerticalStrut(15));
        cardPanel.add(infoPanel);

        // Orta wrapper
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setBackground(BankaTema.BACKGROUND);
        centerWrapper.add(cardPanel);
        add(centerWrapper, BorderLayout.CENTER);

        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.setBackground(BankaTema.BACKGROUND);

        JButton acBtn = BankaTema.butonOlustur("✓ Hesap Aç", BankaTema.SUCCESS);
        JButton iptalBtn = BankaTema.butonOlustur("✗ İptal", BankaTema.DANGER);

        acBtn.addActionListener(e -> hesapAc());
        iptalBtn.addActionListener(e -> dispose());

        buttonPanel.add(acBtn);
        buttonPanel.add(iptalBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Hesap türü değişince label güncelle
        hesapTuruComboBox.addActionListener(e -> {
            String secim = (String) hesapTuruComboBox.getSelectedItem();
            switch (secim) {
                case "Vadesiz Hesap" -> ekOzellikLabel.setText("Limit (TL):");
                case "Vadeli Hesap" -> ekOzellikLabel.setText("Faiz Oranı (%):");
                case "Döviz Hesap" -> ekOzellikLabel.setText("Döviz Tipi:");
            }
        });
    }

    private void hesapAc() {
        Musteri musteri = (Musteri) musteriComboBox.getSelectedItem();
        String tur = (String) hesapTuruComboBox.getSelectedItem();
        String noStr = hesapNoField.getText().trim();
        String ekOzellikStr = ekOzellikField.getText().trim();

        if (noStr.isEmpty() || ekOzellikStr.isEmpty() || musteri == null) {
            BankaTema.uyariMesaji(this, "Tüm alanları doldurmalısınız!");
            return;
        }

        try {
            int hesapNo = Integer.parseInt(noStr);
            Hesap yeniHesap = null;

            switch (tur) {
                case "Vadesiz Hesap" -> {
                    double limit = Double.parseDouble(ekOzellikStr);
                    yeniHesap = new VadesizHesap(hesapNo, musteri, limit);
                }
                case "Vadeli Hesap" -> {
                    double faizOrani = Double.parseDouble(ekOzellikStr);
                    yeniHesap = new VadeliHesap(hesapNo, musteri, faizOrani);
                }
                case "Döviz Hesap" -> {
                    yeniHesap = new DovizHesap(hesapNo, musteri, ekOzellikStr.toUpperCase());
                }
            }

            if (yeniHesap != null) {
                musteri.hesapEkle(yeniHesap);
                BankaTema.basariMesaji(this,
                        tur + " başarıyla açıldı!\n\n" +
                                "Hesap No: " + hesapNo + "\n" +
                                "Müşteri: " + musteri.getAdSoyad());
                dispose();
            }

        } catch (NumberFormatException ex) {
            BankaTema.hataMesaji(this, "Hesap numarası ve sayısal değerler doğru formatta olmalıdır!");
        }
    }
}
