import java.time.LocalDateTime;                


public class Islem {
    private static int sayac=1;
    private int islemId;
    private String tur;
    private double miktar;
    private LocalDateTime tarih;

    public Islem(String tur, double miktar) {
        this.islemId= sayac++;
        this.tur = tur;
        this.miktar = miktar;
        this.tarih=LocalDateTime.now();
    }

    @Override
    public String toString()
    {
        return "[" + islemId + "] " + tur + " | " + miktar + " TL | " + tarih;
    }
    
}
