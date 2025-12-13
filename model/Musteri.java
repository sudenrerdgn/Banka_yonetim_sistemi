package model;

import java.util.ArrayList;
import java.util.List;

public class Musteri implements HesapSahibi {
    private String adSoyad;
    private String tc;
    private String telefon;
    private String email;
    private List<Hesap> hesaplar;
    private Sube sube;

    // Düzeltilmiş constructor - gereksiz hesaplar parametresi kaldırıldı
    public Musteri(String adSoyad, String tc, String telefon, String email, Sube sube) {
        this.adSoyad = adSoyad;
        this.tc = tc;
        this.telefon = telefon;
        this.email = email;
        this.hesaplar = new ArrayList<>();
        this.sube = sube;
    }

    // Şube atama metodu (sonradan şube değiştirmek için)
    public void setSube(Sube sube) {
        this.sube = sube;
    }

    // Getter metotları
    public Sube getSube() {
        return this.sube;
    }

    public String getAdSoyad() {
        return this.adSoyad;
    }

    public String getTc() {
        return this.tc;
    }

    public String getTelefon() {
        return this.telefon;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public List<Hesap> getHesaplar() {
        return this.hesaplar;
    }

    // Hesap ekleme
    @Override
    public void hesapEkle(Hesap hesap) {
        hesaplar.add(hesap);
    }

    @Override
    public String toString() {
        return "Müşteri: " + adSoyad +
                "\nTC: " + tc +
                "\nTel: " + telefon +
                "\nE-mail: " + email +
                "\nŞube: " + (sube != null ? sube.getSubeAdi() : "Yok");
    }

    @Override
    public void hesaplariListele() {
        if (hesaplar.isEmpty()) {
            System.out.println("Bu müşteriye ait hesap bulunmuyor.");
        } else {
            System.out.println("\n" + adSoyad + " adlı müşterinin hesapları:");
            for (Hesap h : hesaplar) {
                System.out.println("  - " + h);
            }
        }
    }

    @Override
    public double toplamBakiyeGoster() {
        double toplam = 0;
        for (Hesap h : hesaplar) {
            toplam += h.getBakiye();
        }
        return toplam;
    }
}
