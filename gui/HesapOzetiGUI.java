package gui;

import app.BankaUygulamasi;
import model.Hesap;
import model.Islem;
import model.Musteri;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HesapOzetiGUI extends JFrame {

    private JComboBox<Musteri> musteriComboBox;
    private JComboBox<Hesap> hesapComboBox;
    private JLabel hesapNoLabel;
    private JLabel hesapTuruLabel;
    private JLabel bakiyeLabel;
    private JTable islemTablosu;
    private DefaultTableModel tableModel;

    public HesapOzetiGUI() {
        BankaTema.pencereAyarla(this, "Hesap Özeti", 750, 620);
        setLayout(new BorderLayout(10, 10));

        // Başlık
        add(BankaTema.baslikOlustur("📊 Hesap Özeti"), BorderLayout.NORTH);

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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Üst panel - Seçim ve bilgiler
        JPanel topPanel = BankaTema.kartPanelOlustur();
        topPanel.setLayout(new BorderLayout(10, 10));

        // Seçim paneli
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        selectionPanel.setBackground(BankaTema.CARD_BG);

        musteriComboBox = BankaTema.comboBoxOlustur(musteriler.toArray(new Musteri[0]));
        hesapComboBox = new JComboBox<>();
        hesapComboBox.setFont(BankaTema.FONT_NORMAL);
        hesapComboBox.setPreferredSize(new Dimension(250, 35));

        selectionPanel.add(BankaTema.labelOlustur("Müşteri:"));
        selectionPanel.add(musteriComboBox);
        selectionPanel.add(Box.createHorizontalStrut(10));
        selectionPanel.add(BankaTema.labelOlustur("Hesap:"));
        selectionPanel.add(hesapComboBox);

        topPanel.add(selectionPanel, BorderLayout.NORTH);

        // Bilgi paneli
        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        infoPanel.setBackground(BankaTema.CARD_BG);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        hesapNoLabel = bilgiKutusuOlustur("Hesap No", "-");
        hesapTuruLabel = bilgiKutusuOlustur("Hesap Türü", "-");
        bakiyeLabel = bilgiKutusuOlustur("Bakiye", "- TL");

        infoPanel.add(hesapNoLabel.getParent());
        infoPanel.add(hesapTuruLabel.getParent());
        infoPanel.add(bakiyeLabel.getParent());

        topPanel.add(infoPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Tablo - İşlem geçmişi
        String[] kolonlar = { "#", "İşlem Türü", "Miktar", "Tarih" };
        tableModel = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        islemTablosu = new JTable(tableModel);
        BankaTema.tabloStilUygula(islemTablosu);

        // Kolon genişlikleri
        islemTablosu.getColumnModel().getColumn(0).setPreferredWidth(50);
        islemTablosu.getColumnModel().getColumn(1).setPreferredWidth(150);
        islemTablosu.getColumnModel().getColumn(2).setPreferredWidth(100);
        islemTablosu.getColumnModel().getColumn(3).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(islemTablosu);
        scrollPane.setBorder(BorderFactory.createLineBorder(BankaTema.BORDER_COLOR));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(BankaTema.BACKGROUND);

        JLabel tableTitle = BankaTema.altBaslikOlustur("📋 İşlem Geçmişi");
        tableTitle.setHorizontalAlignment(SwingConstants.LEFT);
        tablePanel.add(tableTitle, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(tablePanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Alt panel - Butonlar
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BankaTema.BACKGROUND);

        JButton kapatBtn = BankaTema.butonOlustur("Kapat", BankaTema.PRIMARY);
        kapatBtn.addActionListener(e -> dispose());
        buttonPanel.add(kapatBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Listener'lar
        musteriComboBox.addActionListener(e -> hesaplariGuncelle());
        hesapComboBox.addActionListener(e -> ozetGoster());

        // İlk yükleme
        hesaplariGuncelle();
    }

    private JLabel bilgiKutusuOlustur(String baslik, String deger) {
        JPanel kutu = new JPanel();
        kutu.setLayout(new BoxLayout(kutu, BoxLayout.Y_AXIS));
        kutu.setBackground(new Color(245, 247, 250));
        kutu.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BankaTema.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        JLabel baslikLabel = new JLabel(baslik);
        baslikLabel.setFont(BankaTema.FONT_SMALL);
        baslikLabel.setForeground(BankaTema.TEXT_SECONDARY);
        baslikLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel degerLabel = new JLabel(deger);
        degerLabel.setFont(BankaTema.FONT_SUBTITLE);
        degerLabel.setForeground(BankaTema.PRIMARY);
        degerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        kutu.add(baslikLabel);
        kutu.add(Box.createVerticalStrut(5));
        kutu.add(degerLabel);

        return degerLabel;
    }

    private void hesaplariGuncelle() {
        Musteri secilen = (Musteri) musteriComboBox.getSelectedItem();
        hesapComboBox.removeAllItems();

        if (secilen != null) {
            for (Hesap h : secilen.getHesaplar()) {
                hesapComboBox.addItem(h);
            }
        }
        ozetGoster();
    }

    private void ozetGoster() {
        Hesap secilen = (Hesap) hesapComboBox.getSelectedItem();
        tableModel.setRowCount(0);

        if (secilen != null) {
            hesapNoLabel.setText(String.valueOf(secilen.getHesapNo()));
            hesapTuruLabel.setText(secilen.getClass().getSimpleName());
            bakiyeLabel.setText(String.format("%.2f TL", secilen.getBakiye()));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

            for (Islem islem : secilen.getIslemler()) {
                Object[] row = {
                        islem.getIslemId(),
                        islem.getTur(),
                        String.format("%.2f TL", islem.getMiktar()),
                        islem.getTarih().format(formatter)
                };
                tableModel.addRow(row);
            }

            if (secilen.getIslemler().isEmpty()) {
                tableModel.addRow(new Object[] { "-", "Henüz işlem yok", "-", "-" });
            }
        } else {
            hesapNoLabel.setText("-");
            hesapTuruLabel.setText("-");
            bakiyeLabel.setText("- TL");
        }
    }
}
