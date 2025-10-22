public class VadesizHesap extends Hesap{

    private double limit;

    // yapici metot
    public VadesizHesap(int hesapNo, Musteri musteri,double limit) {
        super(hesapNo, musteri);
        this.limit=limit;
    }

 
    // hesap bilgisi override

    @Override
    public void hesapBilgisi() {
        System.out.println("\n=== Vadesiz Hesap Bilgisi ===");
        System.out.println("Hesap No: " + hesapNo);
        System.out.println("Müşteri: " + musteri.getAdSoyad()); 
        System.out.println("Bakiye: " + bakiye + " TL");
        System.out.println("Limit: " + limit + " TL"); 
    }
    

    // para cekme override

    @Override
    public void paraCek(double miktar)
    {
        if(miktar <=0)
        {
            System.out.println("geçersiz miktar.");
            return;
        }
        else if(bakiye+limit < miktar)
        {
            System.out.println("yetersiz bakiye");
            return;
        }
        else 
        {
            bakiye -=miktar;
            islemler.add(new Islem("para çekme", -miktar));
            System.out.println(miktar+ "tl para çekme işlemi yapildi\nİşlem sonu bakiye: "+bakiye);
        }
    }

    
}
