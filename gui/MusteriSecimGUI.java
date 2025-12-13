package gui;

import app.BankaUygulamasi;
import model.Musteri;

import javax.swing.*;
import java.util.List;

/**
 * Müşteri seçimi için yardımcı sınıf
 */
public class MusteriSecim {

    /**
     * Dialog ile müşteri seçimi yapar
     * @return Seçilen müşteri veya null
     */
    public static Musteri sec() {
        List<Musteri> musteriler = BankaUygulamasi.getMusteriler();
        
        if (musteriler.isEmpty()) {
            BankaTema.uyariMesaji(null, "Kayıtlı müşteri bulunamadı!");
            return null;
        }

        Object[] liste = musteriler.toArray();
        
        return (Musteri) JOptionPane.showInputDialog(
            null,
            "Müşteri seçin:",
            "Müşteri Seçimi",
            JOptionPane.PLAIN_MESSAGE,
            null,
            liste,
            liste[0]
        );
    }
}
