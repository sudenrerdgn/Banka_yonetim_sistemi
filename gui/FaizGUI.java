package gui;

import app.BankaUygulamasi;
import model.Hesap;
import model.Musteri;
import model.VadeliHesap;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FaizGUI extends JFrame {

    private JComboBox<Musteri> musteriComboBox;
    private JComboBox<Hesap> hesapComboBox;
    private JLabel bakiyeLabel;
    private JLabel faizOraniLabel;
    private JLabel kazancLabel;

    public FaizGUI() {
        BankaTema.pencereAyarla(this, "Faiz Hesaplama", 550, 520);
        setLayout(new BorderLayout());

        // Başlık
        add(BankaTema.baslikOlustur("📈 Vadeli Hesap Faizi"), BorderLayout.NORTH);

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

        // Bilgi paneli
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(232, 245, 253));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BankaTema.PRIMARY, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        bakiyeLabel = new JLabel("Mevcut Bakiye: - TL");
        bakiyeLabel.setFont(BankaTema.FONT_SUBTITLE);
        bakiyeLabel.setForeground(BankaTema.PRIMARY_DARK);

        faizOraniLabel = new JLabel("Faiz Oranı: - %");
        faizOraniLabel.setFont(BankaTema.FONT_NORMAL);
        faizOraniLabel.setForeground(BankaTema.TEXT_SECONDARY);

        kazancLabel = new JLabel("Tahmini Kazanç: - TL");
        kazancLabel.setFont(BankaTema.FONT_SUBTITLE);
        kazancLabel.setForeground(BankaTema.SUCCESS);

        infoPanel.add(bakiyeLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(faizOraniLabel);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(kazancLabel);

        // Form satırları
        cardPanel.add(BankaTema.formSatiriOlustur("Müşteri:", musteriComboBox));
        cardPanel.add(BankaTema.formSatiriOlustur("Vadeli Hesap:", hesapComboBox));
        cardPanel.add(Box.createVerticalStrut(15));
        cardPanel.add(infoPanel);

        // Uyarı notu
        JPanel notePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        notePanel.setBackground(new Color(255, 243, 224));
        notePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BankaTema.WARNING, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        JLabel noteLabel = new JLabel("⚠️ Sadece vadeli hesaplara faiz uygulanabilir.");
        noteLabel.setFont(BankaTema.FONT_SMALL);
        noteLabel.setForeground(new Color(230, 126, 34));
        notePanel.add(noteLabel);
        cardPanel.add(Box.createVerticalStrut(15));
        cardPanel.add(notePanel);

        // Orta wrapper
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setBackground(BankaTema.BACKGROUND);
        centerWrapper.add(cardPanel);
        add(centerWrapper, BorderLayout.CENTER);

        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.setBackground(BankaTema.BACKGROUND);

        JButton faizBtn = BankaTema.butonOlustur("📈 Faiz Ekle", BankaTema.SUCCESS);
        JButton iptalBtn = BankaTema.butonOlustur("✗ İptal", BankaTema.DANGER);

        faizBtn.addActionListener(e -> faizEkle());
        iptalBtn.addActionListener(e -> dispose());

        buttonPanel.add(faizBtn);
        buttonPanel.add(iptalBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listener'lar
        musteriComboBox.addActionListener(e -> hesaplariGuncelle());
        hesapComboBox.addActionListener(e -> bilgileriGuncelle());

        // İlk yükleme
        hesaplariGuncelle();
    }

    private void hesaplariGuncelle() {
        Musteri secilen = (Musteri) musteriComboBox.getSelectedItem();
        hesapComboBox.removeAllItems();

        if (secilen != null) {
            // Sadece vadeli hesapları listele
            for (Hesap h : secilen.getHesaplar()) {
                if (h instanceof VadeliHesap) {
                    hesapComboBox.addItem(h);
                }
            }
        }
        bilgileriGuncelle();
    }

    private void bilgileriGuncelle() {
        Hesap secilen = (Hesap) hesapComboBox.getSelectedItem();

        if (secilen instanceof VadeliHesap vadeli) {
            double bakiye = vadeli.getBakiye();
            double faizOrani = vadeli.getFaizOrani();
            double kazanc = bakiye * (faizOrani / 100);

            bakiyeLabel.setText("Mevcut Bakiye: " + String.format("%.2f", bakiye) + " TL");
            faizOraniLabel.setText("Faiz Oranı: %" + String.format("%.2f", faizOrani));
            kazancLabel.setText("Tahmini Kazanç: +" + String.format("%.2f", kazanc) + " TL");
        } else {
            bakiyeLabel.setText("Mevcut Bakiye: - TL");
            faizOraniLabel.setText("Faiz Oranı: - %");
            kazancLabel.setText("Tahmini Kazanç: - TL");
        }
    }

    private void faizEkle() {
        Hesap hesap = (Hesap) hesapComboBox.getSelectedItem();

        if (hesap == null) {
            BankaTema.uyariMesaji(this, "Lütfen bir vadeli hesap seçin!");
            return;
        }

        if (hesap instanceof VadeliHesap vadeli) {
            double oncekiBakiye = vadeli.getBakiye();
            double kazanc = oncekiBakiye * (vadeli.getFaizOrani() / 100);

            vadeli.faizEkle();

            double yeniBakiye = vadeli.getBakiye();

            BankaTema.basariMesaji(this,
                    "Faiz başarıyla eklendi!\n\n" +
                            "Faiz Oranı: %" + String.format("%.2f", vadeli.getFaizOrani()) + "\n" +
                            "Kazanç: +" + String.format("%.2f", kazanc) + " TL\n\n" +
                            "Önceki Bakiye: " + String.format("%.2f", oncekiBakiye) + " TL\n" +
                            "Yeni Bakiye: " + String.format("%.2f", yeniBakiye) + " TL");
            dispose();
        } else {
            BankaTema.hataMesaji(this, "Bu hesap vadeli hesap değil!");
        }
    }
}
