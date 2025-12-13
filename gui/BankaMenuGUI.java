package gui;

import javax.swing.*;
import java.awt.*;

public class BankaMenuGUI extends JFrame {

    public BankaMenuGUI() {
        BankaTema.pencereAyarla(this, "Banka Yönetim Sistemi", 850, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === SOL PANEL (Sidebar) ===
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(BankaTema.CARD_BG);
        sidebar.setPreferredSize(new Dimension(300, 0));
        sidebar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, BankaTema.BORDER_COLOR));

        // Logo/Başlık
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(BankaTema.PRIMARY);
        logoPanel.setPreferredSize(new Dimension(300, 80));
        logoPanel.setMaximumSize(new Dimension(300, 80));

        JLabel logoLabel = new JLabel("==BANKA SİSTEMİ==");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logoLabel.setForeground(Color.WHITE);
        logoPanel.add(logoLabel);
        sidebar.add(logoPanel);

        // Menü Butonları
        JButton musteriBtn = BankaTema.menuButonuOlustur("Müşteri Ekle", "");
        JButton hesapAcBtn = BankaTema.menuButonuOlustur("Hesap Aç", "");
        JButton paraYatirBtn = BankaTema.menuButonuOlustur("Para Yatır", "");
        JButton paraCekBtn = BankaTema.menuButonuOlustur("Para Çek", "");
        JButton transferBtn = BankaTema.menuButonuOlustur("Transfer Yap", "");
        JButton faizBtn = BankaTema.menuButonuOlustur("Faiz Hesapla", "");
        JButton hesapOzetiBtn = BankaTema.menuButonuOlustur("Hesap Özeti", "");
        JButton subeEkleBtn = BankaTema.menuButonuOlustur("Şube Ekle", "");
        JButton subeListeleBtn = BankaTema.menuButonuOlustur("Şubeleri Listele", "");
        JButton hesapListeleBtn = BankaTema.menuButonuOlustur("Müşteri Hesapları", "");
        JButton cikisBtn = BankaTema.menuButonuOlustur("Çıkış", "");

        // Butonları max genişliğe ayarla
        Dimension maxSize = new Dimension(300, 50);
        musteriBtn.setMaximumSize(maxSize);
        hesapAcBtn.setMaximumSize(maxSize);
        paraYatirBtn.setMaximumSize(maxSize);
        paraCekBtn.setMaximumSize(maxSize);
        transferBtn.setMaximumSize(maxSize);
        faizBtn.setMaximumSize(maxSize);
        hesapOzetiBtn.setMaximumSize(maxSize);
        subeEkleBtn.setMaximumSize(maxSize);
        subeListeleBtn.setMaximumSize(maxSize);
        hesapListeleBtn.setMaximumSize(maxSize);
        cikisBtn.setMaximumSize(maxSize);

        sidebar.add(musteriBtn);
        sidebar.add(hesapAcBtn);
        sidebar.add(paraYatirBtn);
        sidebar.add(paraCekBtn);
        sidebar.add(transferBtn);
        sidebar.add(faizBtn);
        sidebar.add(hesapOzetiBtn);
        sidebar.add(subeEkleBtn);
        sidebar.add(subeListeleBtn);
        sidebar.add(hesapListeleBtn);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(cikisBtn);

        add(sidebar, BorderLayout.WEST);

        // === SAĞ PANEL (Ana içerik) ===
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(BankaTema.BACKGROUND);
        mainContent.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Hoşgeldiniz mesajı
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(BankaTema.BACKGROUND);

        JLabel welcomeTitle = new JLabel("Hoş Geldiniz!");
        welcomeTitle.setFont(new Font("Segoe UI", Font.BOLD, 32));
        welcomeTitle.setForeground(BankaTema.PRIMARY);
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeText = new JLabel("Banka Yönetim Sistemine hoş geldiniz.");
        welcomeText.setFont(BankaTema.FONT_NORMAL);
        welcomeText.setForeground(BankaTema.TEXT_SECONDARY);
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel infoText = new JLabel("Sol menüden işlem seçebilirsiniz.");
        infoText.setFont(BankaTema.FONT_SMALL);
        infoText.setForeground(BankaTema.TEXT_SECONDARY);
        infoText.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomePanel.add(Box.createVerticalGlue());
        welcomePanel.add(welcomeTitle);
        welcomePanel.add(Box.createVerticalStrut(10));
        welcomePanel.add(welcomeText);
        welcomePanel.add(Box.createVerticalStrut(5));
        welcomePanel.add(infoText);
        welcomePanel.add(Box.createVerticalGlue());

        // Bilgi kartları
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardsPanel.setBackground(BankaTema.BACKGROUND);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        cardsPanel.add(bilgiKartiOlustur("", "Hesap İşlemleri", "Para yatır, çek, transfer"));
        cardsPanel.add(bilgiKartiOlustur("", "Müşteri Yönetimi", "Müşteri ve hesap ekle"));
        cardsPanel.add(bilgiKartiOlustur("", "Şube İşlemleri", "Şube ekle ve listele"));

        mainContent.add(welcomePanel, BorderLayout.CENTER);
        mainContent.add(cardsPanel, BorderLayout.SOUTH);

        add(mainContent, BorderLayout.CENTER);

        // === BUTON AKSİYONLARI ===
        musteriBtn.addActionListener(e -> new MusteriEkleGUI().setVisible(true));
        hesapAcBtn.addActionListener(e -> new HesapAcGUI().setVisible(true));
        paraYatirBtn.addActionListener(e -> new ParaYatirGUI().setVisible(true));
        paraCekBtn.addActionListener(e -> new ParaCekGUI().setVisible(true));
        transferBtn.addActionListener(e -> new TransferGUI().setVisible(true));
        faizBtn.addActionListener(e -> new FaizGUI().setVisible(true));
        hesapOzetiBtn.addActionListener(e -> new HesapOzetiGUI().setVisible(true));
        subeEkleBtn.addActionListener(e -> new SubeEkleGUI().setVisible(true));
        subeListeleBtn.addActionListener(e -> new SubeListeleGUI().setVisible(true));
        hesapListeleBtn.addActionListener(e -> new MusteriHesapListeGUI().setVisible(true));

        cikisBtn.addActionListener(e -> {
            int cevap = JOptionPane.showConfirmDialog(this,
                    "Programdan çıkmak istediğinize emin misiniz?",
                    "Çıkış", JOptionPane.YES_NO_OPTION);
            if (cevap == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private JPanel bilgiKartiOlustur(String emoji, String baslik, String aciklama) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BankaTema.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BankaTema.BORDER_COLOR, 1),
                BorderFactory.createEmptyBorder(20, 15, 20, 15)));

        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        emojiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel baslikLabel = new JLabel(baslik);
        baslikLabel.setFont(BankaTema.FONT_SUBTITLE);
        baslikLabel.setForeground(BankaTema.TEXT_PRIMARY);
        baslikLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel aciklamaLabel = new JLabel(aciklama);
        aciklamaLabel.setFont(BankaTema.FONT_SMALL);
        aciklamaLabel.setForeground(BankaTema.TEXT_SECONDARY);
        aciklamaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(emojiLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(baslikLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(aciklamaLabel);

        return card;
    }
}
