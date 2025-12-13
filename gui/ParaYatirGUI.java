package gui;

import app.BankaUygulamasi;
import model.Hesap;
import model.Musteri;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ParaYatirGUI extends JFrame {

    private JComboBox<Musteri> musteriComboBox;
    private JComboBox<Hesap> hesapComboBox;
    private JTextField miktarField;
    private JLabel bakiyeLabel;

    public ParaYatirGUI() {
        BankaTema.pencereAyarla(this, "Para Yatırma", 550, 480);
        setLayout(new BorderLayout());

        // Başlık
        add(BankaTema.baslikOlustur("💵 Para Yatır"), BorderLayout.NORTH);

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
        JPanel bakiyePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bakiyePanel.setBackground(new Color(232, 245, 253));
        bakiyePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BankaTema.PRIMARY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        bakiyeLabel = new JLabel("Mevcut Bakiye: - TL");
        bakiyeLabel.setFont(BankaTema.FONT_SUBTITLE);
        bakiyeLabel.setForeground(BankaTema.PRIMARY_DARK);
        bakiyePanel.add(bakiyeLabel);

        // Form satırları
        cardPanel.add(BankaTema.formSatiriOlustur("Müşteri:", musteriComboBox));
        cardPanel.add(BankaTema.formSatiriOlustur("Hesap:", hesapComboBox));
        cardPanel.add(Box.createVerticalStrut(10));
        cardPanel.add(bakiyePanel);
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

        JButton yatirBtn = BankaTema.butonOlustur("💵 Para Yatır", BankaTema.SUCCESS);
        JButton iptalBtn = BankaTema.butonOlustur("✗ İptal", BankaTema.DANGER);

        yatirBtn.addActionListener(e -> paraYatir());
        iptalBtn.addActionListener(e -> dispose());

        buttonPanel.add(yatirBtn);
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
            bakiyeLabel.setText("Mevcut Bakiye: " + String.format("%.2f", secilen.getBakiye()) + " TL");
        } else {
            bakiyeLabel.setText("Mevcut Bakiye: - TL");
        }
    }

    private void paraYatir() {
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
            hesap.paraYatir(miktar);
            double yeniBakiye = hesap.getBakiye();

            BankaTema.basariMesaji(this,
                    "Para yatırma başarılı!\n\n" +
                            "Yatırılan: " + String.format("%.2f", miktar) + " TL\n" +
                            "Önceki Bakiye: " + String.format("%.2f", oncekiBakiye) + " TL\n" +
                            "Yeni Bakiye: " + String.format("%.2f", yeniBakiye) + " TL");
            dispose();

        } catch (NumberFormatException ex) {
            BankaTema.hataMesaji(this, "Miktar sayısal bir değer olmalıdır!");
        }
    }
}
