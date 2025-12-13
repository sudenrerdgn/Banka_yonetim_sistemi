package gui;

import app.BankaUygulamasi;
import model.Hesap;
import model.Musteri;
import model.VadesizHesap;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ParaCekGUI extends JFrame {

    private JComboBox<Musteri> musteriComboBox;
    private JComboBox<Hesap> hesapComboBox;
    private JTextField miktarField;
    private JLabel bakiyeLabel;
    private JLabel limitLabel;

    public ParaCekGUI() {
        BankaTema.pencereAyarla(this, "Para Çekme", 600, 500);
        setLayout(new BorderLayout());

        // Başlık
        add(BankaTema.baslikOlustur("💸 Para Çek"), BorderLayout.NORTH);

        // Müşteri kontrolü
        List<Musteri> musteriler = BankaUygulamasi.getMusteriler();
        if (musteriler.isEmpty()) {
            BankaTema.hataMesaji(this, "Kayıtlı müşteri bulunamadı!");
            dispose();
            return;
        }

        // Ana Panel
        JPanel cardPanel = BankaTema.kartPanelOlustur();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        // Form alanları
        musteriComboBox = BankaTema.comboBoxOlustur(musteriler.toArray(new Musteri[0]));
        hesapComboBox = new JComboBox<>();
        hesapComboBox.setFont(BankaTema.FONT_NORMAL);
        hesapComboBox.setPreferredSize(new Dimension(200, 35));

        miktarField = BankaTema.textFieldOlustur(15);

        // Bakiye bilgi paneli
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setPreferredSize(new Dimension(400, 80));
        infoPanel.setBackground(new Color(232, 245, 253));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BankaTema.PRIMARY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        bakiyeLabel = new JLabel("Mevcut Bakiye: - TL");
        bakiyeLabel.setFont(BankaTema.FONT_SUBTITLE);
        bakiyeLabel.setForeground(BankaTema.PRIMARY_DARK);

        limitLabel = new JLabel("Çekilebilir Max: - TL");
        limitLabel.setFont(BankaTema.FONT_SMALL);
        limitLabel.setForeground(BankaTema.TEXT_SECONDARY);

        infoPanel.add(bakiyeLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(limitLabel);

        // Form satırları
        cardPanel.add(BankaTema.formSatiriOlustur("Müşteri:", musteriComboBox));
        cardPanel.add(BankaTema.formSatiriOlustur("Hesap:", hesapComboBox));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(infoPanel);
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(BankaTema.formSatiriOlustur("Miktar (TL):", miktarField));

        // Orta wrapper
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setBackground(BankaTema.BACKGROUND);
        centerWrapper.add(cardPanel);
        add(centerWrapper, BorderLayout.CENTER);

        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.setBackground(BankaTema.BACKGROUND);

        JButton cekBtn = BankaTema.butonOlustur("💸 Para Çek", BankaTema.WARNING);
        JButton iptalBtn = BankaTema.butonOlustur("✗ İptal", BankaTema.DANGER);

        cekBtn.addActionListener(e -> paraCek());
        iptalBtn.addActionListener(e -> dispose());

        buttonPanel.add(cekBtn);
        buttonPanel.add(iptalBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listener'lar
        musteriComboBox.addActionListener(e -> hesaplariGuncelle());
        hesapComboBox.addActionListener(e -> bakiyeGuncelle());

        // İlk yükleme
        hesaplariGuncelle();
    }

    private void hesaplariGuncelle() {
        Musteri secilen = (Musteri) musteriComboBox.getSelectedItem();
        hesapComboBox.removeAllItems();

        if (secilen != null) {
            for (Hesap h : secilen.getHesaplar()) {
                hesapComboBox.addItem(h);
            }
        }
        bakiyeGuncelle();
    }

    private void bakiyeGuncelle() {
        Hesap secilen = (Hesap) hesapComboBox.getSelectedItem();
        if (secilen != null) {
            double bakiye = secilen.getBakiye();
            bakiyeLabel.setText("Mevcut Bakiye: " + String.format("%.2f", bakiye) + " TL");

            // Vadesiz hesapsa limit dahil göster
            if (secilen instanceof VadesizHesap vadesiz) {
                double max = bakiye + vadesiz.getLimit();
                limitLabel.setText("Çekilebilir Max (Limit dahil): " + String.format("%.2f", max) + " TL");
            } else {
                limitLabel.setText("Çekilebilir Max: " + String.format("%.2f", bakiye) + " TL");
            }
        } else {
            bakiyeLabel.setText("Mevcut Bakiye: - TL");
            limitLabel.setText("Çekilebilir Max: - TL");
        }
    }

    private void paraCek() {
        Hesap hesap = (Hesap) hesapComboBox.getSelectedItem();
        String miktarStr = miktarField.getText().trim();

        if (hesap == null) {
            BankaTema.uyariMesaji(this, "Lütfen bir hesap seçin!");
            return;
        }

        if (miktarStr.isEmpty()) {
            BankaTema.uyariMesaji(this, "Lütfen miktar girin!");
            return;
        }

        try {
            double miktar = Double.parseDouble(miktarStr);

            if (miktar <= 0) {
                BankaTema.hataMesaji(this, "Miktar pozitif bir değer olmalıdır!");
                return;
            }

            double oncekiBakiye = hesap.getBakiye();
            boolean basarili = hesap.paraCek(miktar);

            if (basarili) {
                double yeniBakiye = hesap.getBakiye();
                BankaTema.basariMesaji(this,
                        "Para çekme başarılı!\n\n" +
                                "Çekilen: " + String.format("%.2f", miktar) + " TL\n" +
                                "Önceki Bakiye: " + String.format("%.2f", oncekiBakiye) + " TL\n" +
                                "Yeni Bakiye: " + String.format("%.2f", yeniBakiye) + " TL");
                dispose();
            } else {
                BankaTema.hataMesaji(this, "İşlem başarısız!\nYetersiz bakiye veya limit aşımı.");
            }

        } catch (NumberFormatException ex) {
            BankaTema.hataMesaji(this, "Miktar sayısal bir değer olmalıdır!");
        }
    }
}
