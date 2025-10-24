import java.util.List;

public interface HesapSahibi {
    void hesapEkle(Hesap hesap);
    List<Hesap> getHesaplar();
    void hesaplariListele();
    double toplamBakiyeGoster();
}
