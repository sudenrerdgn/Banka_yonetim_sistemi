public class VadeliHesap extends Hesap {

    private double faizOrani;

    public VadeliHesap(int hesapNo, Musteri musteri,double faizOrani) {
        super(hesapNo, musteri);
        this.faizOrani=faizOrani;
    }

    // hesap bilgisi override

    @Override
    public void hesapBilgisi() {
        System.out.println("\n=== Vadeli Hesap Bilgisi ==="); 
        System.out.println("Hesap No: " + hesapNo); 
        System.out.println("Müşteri: " + musteri.getAdSoyad()); 
        System.out.println("Bakiye: " + bakiye + " TL"); 
        System.out.println("Faiz Orani: %" + faizOrani); 
    }

    // faiz ekleme metodu

    public void faizEkle()
    {
        if(faizOrani <= 0)
        {
            System.out.println("geçersiz faiz orani");
            return;
        }
        double kazanc=bakiye*(faizOrani / 100);
        bakiye+=kazanc;

        islemler.add(new Islem("faiz ekleme", kazanc));
        System.out.println(faizOrani+"% faiz eklendi. Yeni bakiye: "+bakiye+" tl");

    }

    // para cekme override

    @Override
    public void paraCek(double miktar)
    {
        System.out.println("Vadeli hesaplarda vade dolmadan para çekilmez.");
    }
    
}
