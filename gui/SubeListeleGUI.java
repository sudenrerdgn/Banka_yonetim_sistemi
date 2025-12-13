package gui;

import app.BankaUygulamasi;
import model.Sube;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SubeListeleGUI extends JFrame {

    public SubeListeleGUI() {
        BankaTema.pencereAyarla(this, "Şube Listesi", 500, 400);
        setLayout(new BorderLayout(10, 10));

        // Başlık
        add(BankaTema.baslikOlustur("📋 Kayıtlı Şubeler"), BorderLayout.NORTH);

        // Şube kontrolü
        List<Sube> subeler = BankaUygulamasi.getSubeler();
        if (subeler.isEmpty()) {
            BankaTema.uyariMesaji(this, "Kayıtlı şube bulunamadı!");
            dispose();
            return;
        }

        // Ana panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BankaTema.BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        // Özet bilgi paneli
        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        summaryPanel.setBackground(new Color(232, 245, 253));
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BankaTema.PRIMARY, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));

        int toplamMusteri = 0;
        for (Sube s : subeler) {
            toplamMusteri += s.getMusteriler().size();
        }

        JLabel subeCountLabel = new JLabel("🏢 Toplam Şube: " + subeler.size());
        subeCountLabel.setFont(BankaTema.FONT_SUBTITLE);
        subeCountLabel.setForeground(BankaTema.PRIMARY_DARK);

        JLabel musteriCountLabel = new JLabel("👥 Toplam Müşteri: " + toplamMusteri);
        musteriCountLabel.setFont(BankaTema.FONT_SUBTITLE);
        musteriCountLabel.setForeground(BankaTema.SUCCESS);

        summaryPanel.add(subeCountLabel);
        summaryPanel.add(musteriCountLabel);
        mainPanel.add(summaryPanel, BorderLayout.NORTH);

        // Tablo
        String[] kolonlar = { "Şube Kodu", "Şube Adı", "Müşteri Sayısı" };
        DefaultTableModel model = new DefaultTableModel(kolonlar, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Sube sube : subeler) {
            Object[] row = {
                    sube.getSubeKodu(),
                    sube.getSubeAdi(),
                    sube.getMusteriler().size()
            };
            model.addRow(row);
        }

        JTable table = new JTable(model);
        BankaTema.tabloStilUygula(table);

        // Kolon genişlikleri
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BankaTema.BORDER_COLOR));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);

        // Buton paneli
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BankaTema.BACKGROUND);

        JButton yeniSubeBtn = BankaTema.butonOlustur("+ Yeni Şube", BankaTema.SUCCESS);
        JButton kapatBtn = BankaTema.butonOlustur("Kapat", BankaTema.PRIMARY);

        yeniSubeBtn.addActionListener(e -> {
            new SubeEkleGUI().setVisible(true);
            dispose();
        });
        kapatBtn.addActionListener(e -> dispose());

        buttonPanel.add(yeniSubeBtn);
        buttonPanel.add(kapatBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
