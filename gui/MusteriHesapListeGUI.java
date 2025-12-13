package gui;

import app.BankaUygulamasi;
import model.Hesap;
import model.Musteri;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MusteriHesapListeGUI extends JFrame {

    private JComboBox<Musteri> musteriComboBox;
    private JLabel musteriAdLabel;
    private JLabel tcLabel;
    private JLabel subeLabel;
    private JLabel toplamBakiyeLabel;
    private JTable hesapTablosu;
    private DefaultTableModel tableModel;

    public MusteriHesapListeGUI() {
        BankaTema.pencereAyarla(this, "Müşteri Hesapları", 750, 620);
        setLayout(new BorderLayout(10, 10));

        // Başlık
        add(BankaTema.baslikOlustur("📑 Müşteri Hesapları"), BorderLayout.NORTH);

        // Müşteri kontrolü
        List<Musteri> musteriler = BankaUygulamasi.getMusteriler();
        if (musteriler.isEmpty()) {
            BankaTema.hataMesaji(this, "Kayıtlı müşteri bulunamadı!");
            dispose();
            return;
        }

        // Ana Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BankaTema.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        // Üst panel - Müşteri seçimi ve bilgileri
        JPanel topPanel = BankaTema.kartPanelOlustur();
        topPanel.setLayout(new BorderLayout(10, 15));

        // Seçim
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        selectionPanel.setBackground(BankaTema.CARD_BG);

        musteriComboBox = BankaTema.comboBoxOlustur(musteriler.toArray(new Musteri[0]));
        musteriComboBox.setPreferredSize(new Dimension(300, 35));

        selectionPanel.add(BankaTema.labelOlustur("Müşteri Seçin:"));
        selectionPanel.add(musteriComboBox);
        topPanel.add(selectionPanel, BorderLayout.NORTH);

        // Müşteri bilgi kartları
        JPanel infoCardsPanel = new JPanel(new GridLayout(2, 2, 15, 10));
        infoCardsPanel.setBackground(BankaTema.CARD_BG);

        musteriAdLabel = bilgiKartiOlustur("👤 Ad Soyad", "-", infoCardsPanel);
        tcLabel = bilgiKartiOlustur("🆔 TC Kimlik", "-", infoCardsPanel);
        subeLabel = bilgiKartiOlustur("🏢 Şube", "-", infoCardsPanel);
        toplamBakiyeLabel = bilgiKartiOlustur("💰 Toplam Bakiye", "- TL", infoCardsPanel);

        topPanel.add(infoCardsPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Hesaplar tablosu
        String[] kolonlar = { "Hesap No", "Hesap Türü", "Bakiye", "Detay" };
        tableModel = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        hesapTablosu = new JTable(tableModel);
        BankaTema.tabloStilUygula(hesapTablosu);

        // Kolon genişlikleri
        hesapTablosu.getColumnModel().getColumn(0).setPreferredWidth(80);
        hesapTablosu.getColumnModel().getColumn(1).setPreferredWidth(120);
        hesapTablosu.getColumnModel().getColumn(2).setPreferredWidth(120);
        hesapTablosu.getColumnModel().getColumn(3).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(hesapTablosu);
        scrollPane.setBorder(BorderFactory.createLineBorder(BankaTema.BORDER_COLOR));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BankaTema.BACKGROUND);

        JLabel tableTitle = BankaTema.altBaslikOlustur("📋 Hesap Listesi");
        tableTitle.setHorizontalAlignment(SwingConstants.LEFT);
        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BankaTema.BACKGROUND);

        JButton yeniHesapBtn = BankaTema.butonOlustur("+ Yeni Hesap", BankaTema.SUCCESS);
        JButton kapatBtn = BankaTema.butonOlustur("Kapat", BankaTema.PRIMARY);

        yeniHesapBtn.addActionListener(e -> new HesapAcGUI().setVisible(true));
        kapatBtn.addActionListener(e -> dispose());

        buttonPanel.add(yeniHesapBtn);
        buttonPanel.add(kapatBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listener
        musteriComboBox.addActionListener(e -> musteriBilgileriniGoster());

        // İlk yükleme
        musteriBilgileriniGoster();
    }

    private JLabel bilgiKartiOlustur(String baslik, String deger, JPanel parent) {
        JPanel kart = new JPanel();
        kart.setLayout(new BoxLayout(kart, BoxLayout.Y_AXIS));
        kart.setBackground(new Color(245, 247, 250));
        kart.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BankaTema.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));

        JLabel baslikLabel = new JLabel(baslik);
        baslikLabel.setFont(BankaTema.FONT_SMALL);
        baslikLabel.setForeground(BankaTema.TEXT_SECONDARY);

        JLabel degerLabel = new JLabel(deger);
        degerLabel.setFont(BankaTema.FONT_SUBTITLE);
        degerLabel.setForeground(BankaTema.PRIMARY);

        kart.add(baslikLabel);
        kart.add(Box.createVerticalStrut(3));
        kart.add(degerLabel);

        parent.add(kart);
        return degerLabel;
    }

    private void musteriBilgileriniGoster() {
        Musteri secilen = (Musteri) musteriComboBox.getSelectedItem();
        tableModel.setRowCount(0);

        if (secilen != null) {
            // Müşteri bilgileri
            musteriAdLabel.setText(secilen.getAdSoyad());
            tcLabel.setText(secilen.getTc());
            subeLabel.setText(secilen.getSube() != null ? secilen.getSube().getSubeAdi() : "-");
            toplamBakiyeLabel.setText(String.format("%.2f TL", secilen.toplamBakiyeGoster()));

            // Hesaplar
            List<Hesap> hesaplar = secilen.getHesaplar();

            if (hesaplar.isEmpty()) {
                tableModel.addRow(new Object[] { "-", "Hesap bulunamadı", "-", "-" });
            } else {
                for (Hesap h : hesaplar) {
                    String detay = "";
                    if (h instanceof model.VadesizHesap v) {
                        detay = "Limit: " + String.format("%.2f", v.getLimit()) + " TL";
                    } else if (h instanceof model.VadeliHesap v) {
                        detay = "Faiz: %" + String.format("%.2f", v.getFaizOrani());
                    } else if (h instanceof model.DovizHesap d) {
                        detay = "Döviz: " + d.getDovizTipi();
                    }

                    Object[] row = {
                            h.getHesapNo(),
                            h.getClass().getSimpleName().replace("Hesap", " Hesap"),
                            String.format("%.2f TL", h.getBakiye()),
                            detay
                    };
                    tableModel.addRow(row);
                }
            }
        } else {
            musteriAdLabel.setText("-");
            tcLabel.setText("-");
            subeLabel.setText("-");
            toplamBakiyeLabel.setText("- TL");
        }
    }
}
