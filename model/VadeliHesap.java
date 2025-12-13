package model;

public class VadeliHesap extends Hesap {

    private double faizOrani;

    public VadeliHesap(int hesapNo, Musteri musteri, double faizOrani) {
        super(hesapNo, musteri);
        this.faizOrani = faizOrani;
    }

    public double getFaizOrani() {
        return faizOrani;
    }

    @Override
    public void hesapBilgisi() {
        System.out.println("\n=== Vadeli Hesap Bilgisi ===");
        System.out.println("Hesap No: " + hesapNo);
        System.out.println("Müşteri: " + musteri.getAdSoyad());
        System.out.println("Bakiye: " + bakiye + " TL");
        System.out.println("Faiz Oranı: %" + faizOrani);
    }

    // Faiz ekleme metodu
    public void faizEkle() {
        if (bakiye <= 0) {
            System.out.println("Bakiye 0 veya negatif, faiz uygulanamaz!");
            return;
        }
        if (faizOrani <= 0) {
            System.out.println("Geçersiz faiz oranı!");
            return;
        }
        double kazanc = bakiye * (faizOrani / 100);
        bakiye += kazanc;
        islemler.add(new Islem("Faiz Ekleme", kazanc));
        System.out.println("%" + faizOrani + " faiz eklendi. Kazanç: " + kazanc + " TL");
        System.out.println("Yeni bakiye: " + bakiye + " TL");
    }

    // Vadeli hesapta para çekme engellendi
    @Override
    public boolean paraCek(double miktar) {
        System.out.println("Vadeli hesaplarda vade dolmadan para çekilemez!");
        return false;
    }

    @Override
    public String toString() {
        return "Vadeli Hesap - No: " + hesapNo + " | Bakiye: " + bakiye + " TL | Faiz: %" + faizOrani;
    }
}
