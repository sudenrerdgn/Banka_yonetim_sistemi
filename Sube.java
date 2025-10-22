import java.util.ArrayList;
import java.util.List;

public class Sube {
    private int subeKodu;
    private String subeAdi;
    private List<Musteri> musteriler; // şubeye kayıtlı müşteriler listesi

    public Sube(int subeKodu, String subeAdi) {
        this.subeKodu = subeKodu;
        this.subeAdi = subeAdi;
        this.musteriler=new ArrayList<>();
    }

    // müşteri ekleme metodumuz
    public void musteriEkle(Musteri musteri)
    {
        musteriler.add(musteri);
    }

    // şube bilgilerini yazdırma
    public void subeBilgisi()
    {
        System.out.println("\n🏦 Şube Kodu: " + subeKodu);
        System.out.println("Şube Adi: " + subeAdi);
        System.out.println("Müşteri Sayisi:: " + musteriler.size());
    }

    // getter metotlarımız

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
    public String toString()
    {
        return "Şube [" + subeKodu + "] - " + subeAdi + " (" + musteriler.size() + " müşteri)";
    }

    

    
}
