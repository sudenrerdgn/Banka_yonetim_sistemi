import java.util.ArrayList;
import java.util.List;

public class Musteri {
    private String adSoyad;
    private String tc;
    private String telefon;
    private String email;
    private List<Hesap> hesaplar; // musteriye ait hesap listesi
    private Sube sube;  

    public Musteri(String adSoyad, String tc, String telefon, String email, List<Hesap> hesaplar,Sube sube) {
        this.adSoyad = adSoyad;
        this.tc = tc;
        this.telefon = telefon;
        this.email = email;
        this.hesaplar = new ArrayList<>(); // hesap listesi oluşturur
        this.sube=sube;
        if(sube != null){sube.musteriEkle(this);} // müşteri oluşurulunca şubeye eklenir
    }

 
    // getter metotları

    public Sube getSube()
    {
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


    public List<Hesap> getHesaplar() {
        return this.hesaplar;
    }

    // hesap ekleme

    public void hesapEkle(Hesap hesap)
    {
        hesaplar.add(hesap);
    }

     
    @Override 
    public String toString()
    {
        return "Müşteri: "+ adSoyad+"\ntc: "+tc+"\ntel: "+telefon+"\ne-mail: "+email+"Şube: " + (sube != null ? sube.getSubeAdi() : "Yok");
    }
    


    
}
