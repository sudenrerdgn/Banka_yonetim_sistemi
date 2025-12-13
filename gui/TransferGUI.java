package gui;

import app.BankaUygulamasi;
import model.Hesap;
import model.Musteri;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TransferGUI extends JFrame {

    private JComboBox<Musteri> kaynakMusteriComboBox;
    private JComboBox<Hesap> kaynakHesapComboBox;
    private JComboBox<Hesap> hedefHesapComboBox;
    private JTextField miktarField;
    private JLabel kaynakBakiyeLabel;
    private JLabel hedefBakiyeLabel;

    public TransferGUI() {
        BankaTema.pencereAyarla(this, "Para Transferi", 650, 620);
        setLayout(new BorderLayout());

        // Başlık
        add(BankaTema.baslikOlustur("🔄 Para Transferi"), BorderLayout.NORTH);

        // Müşteri kontrolü
        List<Musteri> musteriler = BankaUygulamasi.getMusteriler();
        if (musteriler.isEmpty()) {
            BankaTema.hataMesaji(this, "Kayıtlı müşteri bulunamadı!");
            dispose();
            return;
        }

        // Tüm hesapları topla
        List<Hesap> tumHesaplar = new ArrayList<>();
        for (Musteri m : musteriler) {
            tumHesaplar.addAll(m.getHesaplar());
        }

        if (tumHesaplar.size() < 2) {
            BankaTema.hataMesaji(this, "Transfer için en az 2 hesap gereklidir!");
            dispose();
            return;
        }

        // Ana Panel
        JPanel cardPanel = BankaTema.kartPanelOlustur();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));

        // === KAYNAK BÖLÜMÜ ===
        JLabel kaynakBaslik = BankaTema.altBaslikOlustur("📤 Gönderen (Kaynak)");
        kaynakBaslik.setHorizontalAlignment(SwingConstants.LEFT);
        cardPanel.add(kaynakBaslik);

        kaynakMusteriComboBox = BankaTema.comboBoxOlustur(musteriler.toArray(new Musteri[0]));
        kaynakHesapComboBox = new JComboBox<>();
        kaynakHesapComboBox.setFont(BankaTema.FONT_NORMAL);
        kaynakHesapComboBox.setPreferredSize(new Dimension(200, 35));

        cardPanel.add(BankaTema.formSatiriOlustur("Müşteri:", kaynakMusteriComboBox));
        cardPanel.add(BankaTema.formSatiriOlustur("Hesap:", kaynakHesapComboBox));

        // Kaynak bakiye paneli
        JPanel kaynakInfoPanel = bilgiPaneliOlustur();
        kaynakBakiyeLabel = new JLabel("Bakiye: - TL");
        kaynakBakiyeLabel.setFont(BankaTema.FONT_NORMAL);
        kaynakBakiyeLabel.setForeground(BankaTema.PRIMARY_DARK);
        kaynakInfoPanel.add(kaynakBakiyeLabel);
        cardPanel.add(kaynakInfoPanel);

        cardPanel.add(Box.createVerticalStrut(15));

        // === HEDEF BÖLÜMÜ ===
        JLabel hedefBaslik = BankaTema.altBaslikOlustur("📥 Alıcı (Hedef)");
        hedefBaslik.setHorizontalAlignment(SwingConstants.LEFT);
        cardPanel.add(hedefBaslik);

        hedefHesapComboBox = BankaTema.comboBoxOlustur(tumHesaplar.toArray(new Hesap[0]));
        cardPanel.add(BankaTema.formSatiriOlustur("Hedef Hesap:", hedefHesapComboBox));

        // Hedef bakiye paneli
        JPanel hedefInfoPanel = bilgiPaneliOlustur();
        hedefBakiyeLabel = new JLabel("Bakiye: - TL");
        hedefBakiyeLabel.setFont(BankaTema.FONT_NORMAL);
        hedefBakiyeLabel.setForeground(BankaTema.SUCCESS);
        hedefInfoPanel.add(hedefBakiyeLabel);
        cardPanel.add(hedefInfoPanel);

        cardPanel.add(Box.createVerticalStrut(15));

        // === MİKTAR ===
        miktarField = BankaTema.textFieldOlustur(15);
        cardPanel.add(BankaTema.formSatiriOlustur("Transfer Miktarı:", miktarField));

        // Orta wrapper
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.setBackground(BankaTema.BACKGROUND);
        centerWrapper.add(cardPanel);
        add(centerWrapper, BorderLayout.CENTER);

        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        buttonPanel.setBackground(BankaTema.BACKGROUND);

        JButton transferBtn = BankaTema.butonOlustur("🔄 Transfer Yap", BankaTema.PRIMARY);
        JButton iptalBtn = BankaTema.butonOlustur("✗ İptal", BankaTema.DANGER);

        transferBtn.addActionListener(e -> transferYap());
        iptalBtn.addActionListener(e -> dispose());

        buttonPanel.add(transferBtn);
        buttonPanel.add(iptalBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listener'lar
        kaynakMusteriComboBox.addActionListener(e -> kaynakHesaplariGuncelle());
        kaynakHesapComboBox.addActionListener(e -> bakiyeleriGuncelle());
        hedefHesapComboBox.addActionListener(e -> bakiyeleriGuncelle());

        // İlk yükleme
        kaynakHesaplariGuncelle();
    }

    private JPanel bilgiPaneliOlustur() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BankaTema.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        return panel;
    }

    private void kaynakHesaplariGuncelle() {
        Musteri secilen = (Musteri) kaynakMusteriComboBox.getSelectedItem();
        kaynakHesapComboBox.removeAllItems();

        if (secilen != null) {
            for (Hesap h : secilen.getHesaplar()) {
                kaynakHesapComboBox.addItem(h);
            }
        }
        bakiyeleriGuncelle();
    }

    private void bakiyeleriGuncelle() {
        Hesap kaynak = (Hesap) kaynakHesapComboBox.getSelectedItem();
        Hesap hedef = (Hesap) hedefHesapComboBox.getSelectedItem();

        if (kaynak != null) {
            kaynakBakiyeLabel.setText("Bakiye: " + String.format("%.2f", kaynak.getBakiye()) + " TL");
        } else {
            kaynakBakiyeLabel.setText("Bakiye: - TL");
        }

        if (hedef != null) {
            hedefBakiyeLabel.setText("Bakiye: " + String.format("%.2f", hedef.getBakiye()) + " TL");
        } else {
            hedefBakiyeLabel.setText("Bakiye: - TL");
        }
    }

    private void transferYap() {
        Hesap kaynak = (Hesap) kaynakHesapComboBox.getSelectedItem();
        Hesap hedef = (Hesap) hedefHesapComboBox.getSelectedItem();
        String miktarStr = miktarField.getText().trim();

        if (kaynak == null || hedef == null) {
            BankaTema.uyariMesaji(this, "Lütfen kaynak ve hedef hesapları seçin!");
            return;
        }

        if (kaynak.equals(hedef)) {
            BankaTema.uyariMesaji(this, "Kaynak ve hedef hesap aynı olamaz!");
            return;
        }

        if (miktarStr.isEmpty()) {
            BankaTema.uyariMesaji(this, "Lütfen transfer miktarı girin!");
            return;
        }

        try {
            double miktar = Double.parseDouble(miktarStr);

            if (miktar <= 0) {
                BankaTema.hataMesaji(this, "Miktar pozitif bir değer olmalıdır!");
                return;
            }

            double kaynakOnceki = kaynak.getBakiye();
            double hedefOnceki = hedef.getBakiye();

            boolean basarili = kaynak.transfer(hedef, miktar);

            if (basarili) {
                BankaTema.basariMesaji(this,
                        "Transfer başarılı!\n\n" +
                                "Transfer Miktarı: " + String.format("%.2f", miktar) + " TL\n\n" +
                                "Kaynak Hesap:\n" +
                                "  Önceki: " + String.format("%.2f", kaynakOnceki) + " TL\n" +
                                "  Sonra: " + String.format("%.2f", kaynak.getBakiye()) + " TL\n\n" +
                                "Hedef Hesap:\n" +
                                "  Önceki: " + String.format("%.2f", hedefOnceki) + " TL\n" +
                                "  Sonra: " + String.format("%.2f", hedef.getBakiye()) + " TL");
                dispose();
            } else {
                BankaTema.hataMesaji(this, "Transfer başarısız!\nYetersiz bakiye.");
            }

        } catch (NumberFormatException ex) {
            BankaTema.hataMesaji(this, "Miktar sayısal bir değer olmalıdır!");
        }
    }
}
