public class DovizHesap extends Hesap{

    private String dovizTipi;

    //yapıcı metot

    public DovizHesap(int hesapNo, Musteri musteri,String dovizTipi) {
        super(hesapNo, musteri);
        this.dovizTipi=dovizTipi;
    }

    // hesap bilgisi override

    @Override
    public void hesapBilgisi() {
        System.out.println("\n=== Döviz Hesap Bilgisi ===");
        System.out.println("Hesap No: " + hesapNo); 
        System.out.println("Müşteri: " + musteri.getAdSoyad()); 
        System.out.println("Bakiye: " + bakiye + " " + dovizTipi);
    }

    @Override 
    public String toString()
    {
        return "Döviz Hesap (" + dovizTipi + ") - No: " + hesapNo + " | Bakiye: " + bakiye;
    }
    
}
