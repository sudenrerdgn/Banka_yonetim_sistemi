package gui;

import app.BankaUygulamasi;
import model.Musteri;
import model.Sube;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MusteriEkleGUI extends JFrame {

    private JTextField adSoyadField;
    private JTextField tcField;
    private JTextField telefonField;
    private JTextField emailField;
    private JComboBox<Sube> subeComboBox;

    public MusteriEkleGUI() {
        BankaTema.pencereAyarla(this, "Yeni Müşteri Ekle", 600, 500);
        setLayout(new BorderLayout());

        // Başlık
        add(BankaTema.baslikOlustur("👤 Yeni Müşteri"), BorderLayout.NORTH);

        // Ana Panel (Kart)
        JPanel cardPanel = BankaTema.kartPanelOlustur();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        // Form alanları
        adSoyadField = BankaTema.textFieldOlustur(20);
        tcField = BankaTema.textFieldOlustur(20);
        telefonField = BankaTema.textFieldOlustur(20);
        emailField = BankaTema.textFieldOlustur(20);

        // Şube listesi
        List<Sube> subeler = BankaUygulamasi.getSubeler();
        if (subeler.isEmpty()) {
            BankaTema.hataMesaji(this, "Kayıtlı şube bulunamadı!\nÖnce şube eklemelisiniz.");
            dispose();
            return;
        }
        subeComboBox = BankaTema.comboBoxOlustur(subeler.toArray(new Sube[0]));

        // Form satırları
        cardPanel.add(BankaTema.formSatiriOlustur("Ad Soyad:", adSoyadField));
        cardPanel.add(BankaTema.formSatiriOlustur("TC Kimlik:", tcField));
        cardPanel.add(BankaTema.formSatiriOlustur("Telefon:", telefonField));
        cardPanel.add(BankaTema.formSatiriOlustur("E-posta:", emailField));
        cardPanel.add(BankaTema.formSatiriOlustur("Şube:", subeComboBox));

        // Orta wrapper
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setBackground(BankaTema.BACKGROUND);
        centerWrapper.add(cardPanel);
        add(centerWrapper, BorderLayout.CENTER);

        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.setBackground(BankaTema.BACKGROUND);

        JButton ekleBtn = BankaTema.butonOlustur("✓ Müşteri Ekle", BankaTema.SUCCESS);
        JButton iptalBtn = BankaTema.butonOlustur("✗ İptal", BankaTema.DANGER);

        ekleBtn.addActionListener(e -> musteriyiKaydet());
        iptalBtn.addActionListener(e -> dispose());

        buttonPanel.add(ekleBtn);
        buttonPanel.add(iptalBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void musteriyiKaydet() {
        String adSoyad = adSoyadField.getText().trim();
        String tc = tcField.getText().trim();
        String telefon = telefonField.getText().trim();
        String email = emailField.getText().trim();
        Sube secilenSube = (Sube) subeComboBox.getSelectedItem();

        // Validasyon
        if (adSoyad.isEmpty() || tc.isEmpty()) {
            BankaTema.uyariMesaji(this, "Ad Soyad ve TC Kimlik alanları zorunludur!");
            return;
        }

        if (tc.length() != 11 || !tc.matches("\\d+")) {
            BankaTema.uyariMesaji(this, "TC Kimlik 11 haneli sayı olmalıdır!");
            return;
        }

        if (secilenSube == null) {
            BankaTema.hataMesaji(this, "Lütfen bir şube seçin!");
            return;
        }

        // Müşteri oluştur - DÜZELTİLMİŞ 5 PARAMETRELİ CONSTRUCTOR
        Musteri yeniMusteri = new Musteri(adSoyad, tc, telefon, email, secilenSube);

        // Şubeye ve ana listeye ekle
        secilenSube.musteriEkle(yeniMusteri);
        BankaUygulamasi.getMusteriler().add(yeniMusteri);

        BankaTema.basariMesaji(this,
                "Müşteri başarıyla eklendi!\n\nAd: " + adSoyad + "\nŞube: " + secilenSube.getSubeAdi());
        dispose();
    }
}
