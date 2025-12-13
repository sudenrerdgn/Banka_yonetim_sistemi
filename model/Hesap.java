package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Hesap implements HesapIslemleri {

    protected int hesapNo;
    protected double bakiye;
    protected Musteri musteri;
    protected List<Islem> islemler;

    public Hesap(int hesapNo, Musteri musteri) {
        this.hesapNo = hesapNo;
        this.musteri = musteri;
        this.bakiye = 0;
        this.islemler = new ArrayList<>();
    }

    // Getter metotları
    public int getHesapNo() {
        return hesapNo;
    }

    public double getBakiye() {
        return bakiye;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public Sube getSube() {
        return (musteri != null) ? musteri.getSube() : null;
    }

    public List<Islem> getIslemler() {
        return islemler;
    }

    // Para yatırma metodu
    @Override
    public void paraYatir(double miktar) {
        if (miktar <= 0) {
            System.out.println("\nGeçersiz miktar!");
            return;
        }
        bakiye += miktar;
        islemler.add(new Islem("Para Yatırma", miktar));
        System.out.println(miktar + " TL yatırıldı.");
        System.out.println("İşlem sonrası bakiye: " + bakiye + " TL");
    }

    // Para çekme metodu
    @Override
    public boolean paraCek(double miktar) {
        if (miktar <= 0) {
            System.out.println("Geçersiz miktar!");
            return false;
        }
        if (bakiye < miktar) {
            System.out.println("Yetersiz bakiye!");
            return false;
        }
        bakiye -= miktar;
        islemler.add(new Islem("Para Çekme", -miktar));
        System.out.println(miktar + " TL çekildi.");
        System.out.println("İşlem sonrası bakiye: " + bakiye + " TL");
        return true;
    }

    // Transfer metodu
    @Override
    public boolean transfer(Hesap hedef, double miktar) {
        if (hedef == null) {
            System.out.println("Hedef hesap bulunamadı!");
            return false;
        }
        if (miktar <= 0) {
            System.out.println("Geçersiz miktar!");
            return false;
        }
        if (bakiye < miktar) {
            System.out.println("Yetersiz bakiye!");
            return false;
        }

        bakiye -= miktar;
        islemler.add(new Islem("Transfer Gönderme", -miktar));

        hedef.bakiye += miktar;
        hedef.islemler.add(new Islem("Transfer Alma", miktar));

        System.out.println(miktar + " TL transfer edildi.");
        System.out.println("Kalan bakiye: " + bakiye + " TL");
        return true;
    }

    // Hesap özeti metodu - System.err düzeltildi
    @Override
    public void hesapOzeti() {
        System.out.println("\n===== Hesap Özeti =====");
        System.out.println("Hesap No: " + hesapNo);
        System.out.println("Müşteri: " + musteri.getAdSoyad());
        System.out.println("Bakiye: " + bakiye + " TL");
        System.out.println("\n--- İşlem Geçmişi ---");
        if (islemler.isEmpty()) {
            System.out.println("Henüz işlem yok.");
        } else {
            for (Islem islem : islemler) {
                System.out.println(islem);
            }
        }
    }

    // Her hesap türüne özel soyut metot
    public abstract void hesapBilgisi();

    @Override
    public String toString() {
        return getClass().getSimpleName() + " - No: " + hesapNo + " | Bakiye: " + bakiye + " TL";
    }
}
