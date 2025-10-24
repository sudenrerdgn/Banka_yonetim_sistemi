import java.util.ArrayList;
import java.util.List;

public abstract class Hesap implements HesapIslemleri { // diğer hesap türlerinin temel sınıfıdır
    
    protected int hesapNo;
    protected double bakiye;
    protected Musteri musteri;
    protected List<Islem> islemler;

    public Hesap(int hesapNo, Musteri musteri) {
        this.hesapNo = hesapNo;
        this.musteri = musteri;
        this.bakiye=0; // baslangıc hesap bakiyesi 0 tanımlanır
        this.islemler=new ArrayList<>();
    }
    
    // getter metotlarımız

    public int getHesapNo() {
        return hesapNo;
    }

    public double getBakiye() {
        return bakiye;
    }

    public Musteri getMusteri() {
        return musteri;
    }

    public Sube getSube()
    {
        return (musteri != null) ? musteri.getSube() : null;
    }

    // para yatırma metodu

    public void paraYatir(double miktar)
    {
        if(miktar<=0) // negatifse yatırılamaz
        {
            System.out.println("\ngecersiz miktar..");
            return;
        }
        bakiye +=miktar; // bakiyeye eklenir
        islemler.add(new Islem("para yatirma", miktar));
        System.out.println(miktar+" bakiyeye eklendi işlem tamamlandi. \nİşlem sonu bakiye: "+bakiye); // onay mesajı
    }


    // para çekme metodu

    public void paraCek(double miktar)
    {
        if(miktar <= 0)
        {
            System.out.println("geçersiz miktar..");
            return;
        }
        else if(bakiye < miktar)
        {
            System.out.println("yetersiz bakiye..");
            return;
        }
        else 
        {
            bakiye-=miktar; // bakiye azaltıldı
            islemler.add(new Islem("para cekme", miktar));
            System.out.println(miktar+" tl para cekme isleminiz gerceklesti.");
            System.out.println("İşlem sonu bakiye: "+bakiye);
        }
    }


    // transfer metodumuz

    public void transfer(Hesap hedef,double miktar)
    {
        if(hedef==null)
        {
            System.out.println("hedef hesap bulunamadi.");
            return;
        }
        else if(miktar<=0)
        {
            System.out.println("geçersiz miktar"); // 
            return;
        }
        else if(bakiye < miktar)
        {
            System.out.println("yetersiz bakiye..");
            return;
        }

        else 
        {
            
            bakiye-=miktar; // kaynak hesaptan düş
            islemler.add(new Islem("transfer gonderme", -miktar)); 
            hedef.bakiye+=miktar; // hedef hesaba ekle
            hedef.islemler.add(new Islem("transfer alma", miktar));

            System.out.println(miktar+"tl transfer edildi");
        }
    }

    // hesap özeti metodumuz

    public void hesapOzeti()
    {
        System.out.println("\n=====Hesap Özeti=====");
        System.err.println("Hesap no: "+hesapNo);
        System.out.println("Müşteri: " + musteri.getAdSoyad());
        System.out.println("Bakiye: " + bakiye + " TL");
        System.out.println("\n--- İşlem Geçmişi ---");
        if(islemler.isEmpty())  
        {
            System.out.println("Henüz işlem yok.");
        }
        else 
        {
            for(Islem islem: islemler) // işlemler vars atüm listeyi yazdır
            {
                System.out.println(islem); 
            }
        }
    }

    // her hesap türüne özel soyut metodumuz

    public abstract void hesapBilgisi();

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + " - No: " + hesapNo + " | Bakiye: " + bakiye;
    }
    

    
}
