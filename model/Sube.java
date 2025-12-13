package model;

import java.util.ArrayList;
import java.util.List;

public class Sube {
    private int subeKodu;
    private String subeAdi;
    private List<Musteri> musteriler;

    public Sube(int subeKodu, String subeAdi) {
        this.subeKodu = subeKodu;
        this.subeAdi = subeAdi;
        this.musteriler = new ArrayList<>();
    }

    // Müşteri ekleme - sadece şube listesine ekler
    // Ana listeye ekleme işlemi BankaUygulamasi'nda yapılmalı
    public void musteriEkle(Musteri musteri) {
        if (!musteriler.contains(musteri)) {
            musteriler.add(musteri);
        }
    }

    // Müşteri çıkarma
    public void musteriCikar(Musteri musteri) {
        musteriler.remove(musteri);
    }

    // Şube bilgilerini yazdırma
    public void subeBilgisi() {
        System.out.println("\n=== Şube Bilgisi ===");
        System.out.println("Şube Kodu: " + subeKodu);
        System.out.println("Şube Adı: " + subeAdi);
        System.out.println("Müşteri Sayısı: " + musteriler.size());
    }

    // Getter metotları
    public int getSubeKodu() {
        return subeKodu;
    }

    public String getSubeAdi() {
        return subeAdi;
    }

    public List<Musteri> getMusteriler() {
        return musteriler;
    }

    @Override
    public String toString() {
        return "Şube [" + subeKodu + "] - " + subeAdi + " (" + musteriler.size() + " müşteri)";
    }
}
