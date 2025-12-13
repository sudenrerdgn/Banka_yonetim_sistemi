package app;

import java.util.*;
import model.*;

public class BankaUygulamasi {
    private static Scanner scan = new Scanner(System.in);
    private static List<Musteri> musteriler = new ArrayList<>();
    private static List<Sube> subeler = new ArrayList<>();

    public static void main(String[] args) {
        // Varsayılan şube oluştur
        subeler.add(new Sube(1, "Merkez Şube"));

        int secim;
        do {
            System.out.println("\n=============================");
            System.out.println("   BANKA YÖNETİM SİSTEMİ");
            System.out.println("=============================");
            System.out.println("1.  Müşteri Ekle");
            System.out.println("2.  Hesap Aç");
            System.out.println("3.  Para Yatır");
            System.out.println("4.  Para Çek");
            System.out.println("5.  Transfer Yap");
            System.out.println("6.  Faiz Hesapla (Vadeli)");
            System.out.println("7.  Hesap Özeti Görüntüle");
            System.out.println("8.  Şube Ekle");
            System.out.println("9.  Şubeleri Listele");
            System.out.println("10. Müşteri Hesapları Listele");
            System.out.println("0.  Çıkış");
            System.out.println("-----------------------------");
            System.out.print("Seçiminiz: ");

            secim = scan.nextInt();

            switch (secim) {
                case 1 -> musteriEkle();
                case 2 -> hesapAc();
                case 3 -> paraYatir();
                case 4 -> paraCek();
                case 5 -> transferYap();
                case 6 -> faizHesapla();
                case 7 -> hesapOzeti();
                case 8 -> subeEkle();
                case 9 -> subeListele();
                case 10 -> musteriHesaplariListele();
                case 0 -> System.out.println("\nProgramdan çıkılıyor. Güle güle!");
                default -> System.out.println("\nGeçersiz seçim! Tekrar deneyin.");
            }
        } while (secim != 0);

        scan.close();
    }

    // Müşteri seçme
    public static Musteri musteriSec() {
        if (musteriler.isEmpty()) {
            System.out.println("\nHenüz kayıtlı müşteri yok!");
            return null;
        }

        System.out.println("\n--- Müşteri Listesi ---");
        for (int i = 0; i < musteriler.size(); i++) {
            System.out.println((i + 1) + ". " + musteriler.get(i).getAdSoyad());
        }

        System.out.print("Seçiminiz: ");
        int secim = scan.nextInt();

        if (secim < 1 || secim > musteriler.size()) {
            System.out.println("Geçersiz seçim!");
            return null;
        }
        return musteriler.get(secim - 1);
    }

    // Hesap seçme
    public static Hesap hesapSec() {
        Musteri m = musteriSec();
        if (m == null) {
            return null;
        }

        List<Hesap> hesaplar = m.getHesaplar();
        if (hesaplar.isEmpty()) {
            System.out.println("\nBu müşterinin hesabı yok!");
            return null;
        }

        System.out.println("\n--- Hesap Listesi ---");
        for (int i = 0; i < hesaplar.size(); i++) {
            System.out.println((i + 1) + ". " + hesaplar.get(i));
        }

        System.out.print("Seçiminiz: ");
        int secim = scan.nextInt();

        if (secim < 1 || secim > hesaplar.size()) {
            System.out.println("Geçersiz seçim!");
            return null;
        }
        return hesaplar.get(secim - 1);
    }

    // Müşteri ekleme - DÜZELTİLDİ
    public static void musteriEkle() {
        scan.nextLine(); // Buffer temizle

        System.out.print("Ad-Soyad: ");
        String adSoyad = scan.nextLine();

        System.out.print("TC Kimlik: ");
        String tc = scan.nextLine();

        System.out.print("Telefon: ");
        String tel = scan.nextLine();

        System.out.print("E-posta: ");
        String email = scan.nextLine();

        // Şube seçimi
        System.out.println("\n--- Şube Seçimi ---");
        for (int i = 0; i < subeler.size(); i++) {
            System.out.println((i + 1) + ". " + subeler.get(i).getSubeAdi());
        }

        System.out.print("Seçiminiz: ");
        int subesec = scan.nextInt();

        Sube secilenSube;
        if (subesec >= 1 && subesec <= subeler.size()) {
            secilenSube = subeler.get(subesec - 1);
        } else {
            System.out.println("Geçersiz seçim! Varsayılan şube atandı.");
            secilenSube = subeler.get(0);
        }

        // Müşteri oluştur - şube bilgisiyle birlikte
        Musteri m = new Musteri(adSoyad, tc, tel, email, secilenSube);

        // Şubeye ekle
        secilenSube.musteriEkle(m);

        // Ana listeye ekle - SADECE BURADA EKLENİYOR
        musteriler.add(m);

        System.out.println("\n✓ Müşteri başarıyla eklendi!");
        System.out.println("  Şube: " + secilenSube.getSubeAdi());
    }

    // Hesap açma
    public static void hesapAc() {
        Musteri m = musteriSec();
        if (m == null) {
            return;
        }

        System.out.println("\n--- Hesap Türü Seçin ---");
        System.out.println("1. Vadesiz Hesap");
        System.out.println("2. Vadeli Hesap");
        System.out.println("3. Döviz Hesap");
        System.out.print("Seçiminiz: ");
        int tur = scan.nextInt();

        System.out.print("Hesap Numarası: ");
        int no = scan.nextInt();

        Hesap hesap = null;

        switch (tur) {
            case 1 -> {
                System.out.print("Limit (TL): ");
                double limit = scan.nextDouble();
                hesap = new VadesizHesap(no, m, limit);
            }
            case 2 -> {
                System.out.print("Faiz Oranı (%): ");
                double faiz = scan.nextDouble();
                hesap = new VadeliHesap(no, m, faiz);
            }
            case 3 -> {
                System.out.print("Döviz Tipi (USD, EUR vb.): ");
                String tip = scan.next();
                hesap = new DovizHesap(no, m, tip);
            }
            default -> System.out.println("Geçersiz tür!");
        }

        if (hesap != null) {
            m.hesapEkle(hesap);
            System.out.println("\n✓ Hesap başarıyla oluşturuldu!");
            hesap.hesapBilgisi();
        }
    }

    // Para yatırma
    public static void paraYatir() {
        Hesap h = hesapSec();
        if (h == null) {
            return;
        }

        System.out.print("Yatırılacak miktar: ");
        double miktar = scan.nextDouble();
        h.paraYatir(miktar);
    }

    // Para çekme
    public static void paraCek() {
        Hesap h = hesapSec();
        if (h == null) {
            return;
        }

        System.out.print("Çekilecek miktar: ");
        double miktar = scan.nextDouble();
        h.paraCek(miktar);
    }

    // Transfer yapma
    public static void transferYap() {
        System.out.println("\n--- Kaynak Hesap ---");
        Hesap kaynak = hesapSec();
        if (kaynak == null) {
            return;
        }

        System.out.println("\n--- Hedef Hesap ---");
        Hesap hedef = hesapSec();
        if (hedef == null) {
            return;
        }

        if (kaynak == hedef) {
            System.out.println("Aynı hesaba transfer yapılamaz!");
            return;
        }

        System.out.print("Transfer miktarı: ");
        double miktar = scan.nextDouble();
        kaynak.transfer(hedef, miktar);
    }

    // Faiz hesaplama (vadeli hesaplar için)
    public static void faizHesapla() {
        Hesap h = hesapSec();
        if (h == null) {
            return;
        }

        if (h instanceof VadeliHesap vadeli) {
            vadeli.faizEkle();
        } else {
            System.out.println("Bu işlem sadece vadeli hesaplar için geçerlidir!");
        }
    }

    // Hesap özeti
    public static void hesapOzeti() {
        Hesap h = hesapSec();
        if (h != null) {
            h.hesapOzeti();
        }
    }

    // Şube ekleme
    public static void subeEkle() {
        System.out.print("Şube Kodu: ");
        int kod = scan.nextInt();
        scan.nextLine(); // Buffer temizle

        System.out.print("Şube Adı: ");
        String ad = scan.nextLine();

        subeler.add(new Sube(kod, ad));
        System.out.println("\n✓ Şube başarıyla eklendi!");
    }

    // Şube listeleme
    public static void subeListele() {
        System.out.println("\n===== Şubeler =====");
        if (subeler.isEmpty()) {
            System.out.println("Kayıtlı şube yok.");
        } else {
            for (Sube s : subeler) {
                System.out.println(s);
            }
        }
    }

    // Müşteri hesapları listeleme
    public static void musteriHesaplariListele() {
        Musteri m = musteriSec();
        if (m == null) {
            return;
        }

        System.out.println("\n===== " + m.getAdSoyad() + " - Hesaplar =====");
        m.hesaplariListele();
        System.out.println("\nToplam Bakiye: " + m.toplamBakiyeGoster() + " TL");
    }

    // Getter metotları
    public static List<Musteri> getMusteriler() {
        return musteriler;
    }

    public static List<Sube> getSubeler() {
        return subeler;
    }

    public static Scanner getScan() {
        return scan;
    }
}
