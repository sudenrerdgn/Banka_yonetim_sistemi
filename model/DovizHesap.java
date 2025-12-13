package model;

public class DovizHesap extends Hesap {

    private String dovizTipi;

    public DovizHesap(int hesapNo, Musteri musteri, String dovizTipi) {
        super(hesapNo, musteri);
        this.dovizTipi = dovizTipi;
    }

    public String getDovizTipi() {
        return dovizTipi;
    }

    @Override
    public void hesapBilgisi() {
        System.out.println("\n=== Döviz Hesap Bilgisi ===");
        System.out.println("Hesap No: " + hesapNo);
        System.out.println("Müşteri: " + musteri.getAdSoyad());
        System.out.println("Bakiye: " + bakiye + " " + dovizTipi);
        System.out.println("Döviz Tipi: " + dovizTipi);
    }

    @Override
    public void paraYatir(double miktar) {
        if (miktar <= 0) {
            System.out.println("\nGeçersiz miktar!");
            return;
        }
        bakiye += miktar;
        islemler.add(new Islem("Para Yatırma (" + dovizTipi + ")", miktar));
        System.out.println(miktar + " " + dovizTipi + " yatırıldı.");
        System.out.println("İşlem sonrası bakiye: " + bakiye + " " + dovizTipi);
    }

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
        islemler.add(new Islem("Para Çekme (" + dovizTipi + ")", -miktar));
        System.out.println(miktar + " " + dovizTipi + " çekildi.");
        System.out.println("İşlem sonrası bakiye: " + bakiye + " " + dovizTipi);
        return true;
    }

    @Override
    public String toString() {
        return "Döviz Hesap (" + dovizTipi + ") - No: " + hesapNo + " | Bakiye: " + bakiye + " " + dovizTipi;
    }
}
