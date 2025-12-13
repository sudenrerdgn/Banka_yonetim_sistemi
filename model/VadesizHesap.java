package model;

public class VadesizHesap extends Hesap {

    private double limit;

    public VadesizHesap(int hesapNo, Musteri musteri, double limit) {
        super(hesapNo, musteri);
        this.limit = limit;
    }

    public double getLimit() {
        return limit;
    }

    @Override
    public void hesapBilgisi() {
        System.out.println("\n=== Vadesiz Hesap Bilgisi ===");
        System.out.println("Hesap No: " + hesapNo);
        System.out.println("Müşteri: " + musteri.getAdSoyad());
        System.out.println("Bakiye: " + bakiye + " TL");
        System.out.println("Limit: " + limit + " TL");
    }

    // Limitli para çekme (bakiye + limit kadar çekilebilir)
    @Override
    public boolean paraCek(double miktar) {
        if (miktar <= 0) {
            System.out.println("Geçersiz miktar!");
            return false;
        }
        if (bakiye + limit < miktar) {
            System.out.println("Yetersiz bakiye! (Limit dahil max: " + (bakiye + limit) + " TL)");
            return false;
        }
        bakiye -= miktar;
        islemler.add(new Islem("Para Çekme", -miktar));
        System.out.println(miktar + " TL çekildi.");
        System.out.println("İşlem sonrası bakiye: " + bakiye + " TL");
        return true;
    }

    @Override
    public String toString() {
        return "Vadesiz Hesap - No: " + hesapNo + " | Bakiye: " + bakiye + " TL | Limit: " + limit + " TL";
    }
}
