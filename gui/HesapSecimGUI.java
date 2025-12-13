package gui;

import model.Hesap;
import model.Musteri;

import javax.swing.*;

/**
 * Hesap seçimi için yardımcı sınıf
 */
public class HesapSecim {

    /**
     * Önce müşteri, sonra hesap seçimi yapar
     * @return Seçilen hesap veya null
     */
    public static Hesap sec() {
        // Önce müşteri seç
        Musteri musteri = MusteriSecim.sec();
        if (musteri == null) {
            return null;
        }

        // Hesap kontrolü
        if (musteri.getHesaplar().isEmpty()) {
            BankaTema.uyariMesaji(null, "Bu müşterinin hesabı bulunmuyor!");
            return null;
        }

        // Hesap seç
        Object[] hesaplar = musteri.getHesaplar().toArray();
        
        return (Hesap) JOptionPane.showInputDialog(
            null,
            "Hesap seçin:",
            "Hesap Seçimi",
            JOptionPane.PLAIN_MESSAGE,
            null,
            hesaplar,
            hesaplar[0]
        );
    }

    /**
     * Belirli bir müşterinin hesaplarından seçim yapar
     * @param musteri Müşteri
     * @return Seçilen hesap veya null
     */
    public static Hesap sec(Musteri musteri) {
        if (musteri == null) {
            return null;
        }

        if (musteri.getHesaplar().isEmpty()) {
            BankaTema.uyariMesaji(null, "Bu müşterinin hesabı bulunmuyor!");
            return null;
        }

        Object[] hesaplar = musteri.getHesaplar().toArray();
        
        return (Hesap) JOptionPane.showInputDialog(
            null,
            musteri.getAdSoyad() + " - Hesap seçin:",
            "Hesap Seçimi",
            JOptionPane.PLAIN_MESSAGE,
            null,
            hesaplar,
            hesaplar[0]
        );
    }
}
