import java.util.*;
public class BankaUygulamasi {
    private static Scanner scan=new Scanner(System.in);
    private static List<Musteri> musteriler=new ArrayList<>();
    private static List<Sube> subeler=new ArrayList<>();

    public static void main(String[] args) {
        subeler.add(new Sube(1, "Merkez Şube"));
        int secim;
        do{
            System.out.println("\n=== BANKA YÖNETİM SİSTEMİ ==="); 
            System.out.println("1. Müşteri Ekle"); 
            System.out.println("2. Hesap Aç"); 
            System.out.println("3. Para Yatir"); 
            System.out.println("4. Para Çek");
            System.out.println("5. Transfer Yap"); 
            System.out.println("6. Faiz Hesapla (Vadeli)"); 
            System.out.println("7. Hesap Özeti Görüntüle");
            System.out.println("8. Şube Ekle");
            System.out.println("9. Şubeleri Listele");
            System.out.println("0. exit");
            System.out.print("\nSeçiminiz: "); 
            secim = scan.nextInt(); 


            switch (secim)
            {
                case 1: musteriEkle();
                break;
                
                case 2: hesapAc();
                break;
                
                case 3: paraYatir();
                break;
                
                case 4: paraCek();
                break;
                
                case 5: transferYap();
                break;
                
                case 6: faizHesapla();
                break;
                
                case 7: hesapOzeti();
                break;
                
                case 8: subeEkle();
                break;
                
                case 9: subeListele();
                break;
                
                case 0: 
                {
                    System.out.println("\nProgramdan cikiliyor."); 
                    System.exit(0);
                }
                
                default: System.out.println("gecersiz secim.");
                break;
            }
        }
        while(secim!=0); // seçim 0 olmadikca döngü devam edecek
    }

    // müşteri seçme metodumuz
    private static Musteri musteriSec()
    {
        if(musteriler.isEmpty())
        {
            System.out.println("\nhenüz müşteri yok.\n");
            return null;
        }
        System.out.println("\n\nMüşteriler:\n");
        for(int i=0; i< musteriler.size();i++)
        {
            System.out.println((i+1)+". "+musteriler.get(i).getAdSoyad());
        }

        System.out.println("\nSeçiminiz: ");
        int secim=scan.nextInt();
        if(secim<1 ||secim>musteriler.size())
        {
            return null;
        }
        return musteriler.get(secim-1); // seçilen müşteriye döndürür
    }

    // hesap seçme metodumuz
    private static Hesap hesapSec()
    {
        Musteri m= musteriSec();
        if(m==null)
        {
            return null;
        }
        
        List<Hesap> hesaplar=m.getHesaplar();

        if(hesaplar.isEmpty())
        {
            System.out.println("\nBu müşterinin hesabi yok.");
            return null;
        }

        System.out.println("\nHesaplar:\n");
        for(int i=0; i<hesaplar.size();i++)
        {
            System.out.println((i+1)+". "+hesaplar.get(i));

        }
        System.out.println("\nSeçiminiz: ");
        int secim=scan.nextInt();
        if(secim<1 || secim>hesaplar.size())
        {
            return null;
        }
        return hesaplar.get(secim-1);

    }

    // Müsteri Ekle Metodumuz
    private static void musteriEkle()
    {
        System.out.println("Ad-soyad: ");
        scan.nextLine();
        String adSoyad=scan.nextLine();

        System.out.println("TC Kimlik: ");
        String tc=scan.nextLine();

        System.out.println("Telefon: ");
        String tel=scan.nextLine();

        System.out.println("E-posta: ");
        String email=scan.nextLine();

        System.out.println("Şube seçin:\n");
        for (int i = 0; i < subeler.size(); i++) 
        {
        System.out.println((i+1) + ". " + subeler.get(i).getSubeAdi());
        }

        System.out.print("Seçiminiz (numara): ");
        int subesec = scan.nextInt();
        scan.nextLine();

        Sube secilenSube=null;

        if(subesec>=1 && subesec <=subeler.size())
        {
            secilenSube=subeler.get(subesec-1);
        }
        else 
        {
            System.out.println("geçersiz şube sseçildi, varsayilan şube atanacak.");
            secilenSube=subeler.get(0);
        }

        Musteri m=new Musteri(adSoyad, tc, tel, email, null, null);
        secilenSube.musteriEkle(m);
        musteriler.add(m); // müşteri listesine ekledi
        System.out.println("\nMüşteri basariyla yuklendi..\nŞube:"+secilenSube.getSubeAdi());
    }


    // Hesap açma metodumuz
    private static void hesapAc() // seçilen müşteriye yeni hesap açar
    {
        Musteri m = musteriSec(); 
        if(m == null)
        {
            return; // musteri yoksa işlem duracak
        }

        System.out.println("\n1. Vadesiz Hesap\n2. Vadeli Hesap\n3. Döviz Hesap"); 
        System.out.print("\nSeçiminiz: "); 
        int tur=scan.nextInt();

        System.out.println("Hesap Numarasi: "); 
        int no=scan.nextInt();

        Hesap hesap=null; // boş hesap tanımı

        switch(tur)
        {
            case 1:  // vadesiz hesap
            {
                System.out.println("Limit: ");
                double limit=scan.nextDouble();
                hesap = new VadesizHesap(no,m,limit); // Yeni vadesiz hesap oluşturdu
                break;
            }
            case 2: // vadeli hesap
            {
                System.out.println("Faiz orani (%):");
                double faiz=scan.nextDouble();
                hesap = new VadeliHesap(no , m, faiz); // Yeni bir vadeli hesap oluşturdu
                break;
            }
            case 3: // döviz hesabı
            {
                System.out.println("Döviz tipi (USD, EUR vb.):");
                String tip=scan.next();
                hesap = new DovizHesap(no,m,tip); // Yeni döviz hesabı oluştu
                break;
            }
            default: System.out.println("\nGeçersiz tür.");
            break;
        }
        if(hesap != null)
        {
            m.hesapEkle(hesap); // musteriye eklenir
            System.out.println("\nHesap oluşturuldu");
        }
    }

    // Para yatrıma metodumuz
    private static void paraYatir()
    {
        Hesap h= hesapSec();
        
        if(h==null)
        {
            return; // yoksa çık
        }

        System.out.println("\nYatirilacak miktar: ");
        double miktar=scan.nextDouble();
        h.paraYatir(miktar); // işlemi yapar
    }

    // para çekme metodumuz
    private static void paraCek()
    {
        Hesap h= hesapSec();

        if(h == null)
        {
            return; // yoksa çık
        }

        System.out.println("\nÇekilecek miktar: ");
        double miktar=scan.nextDouble();
        h.paraCek(miktar); // işlemi gerçekleştirir
    }

    // Transfer metodumuz
    private static void transferYap()
    {
        System.out.println("\nKaynak hesap: ");
        Hesap kaynak=hesapSec();
        if(kaynak == null) return; // hesap yoksa çık

        System.out.println("\nHedef hesap: ");
        Hesap hedef=hesapSec();
        if(hedef == null) return;

        System.out.println("Transfer miktari: ");
        double miktar=scan.nextDouble();

        kaynak.transfer(hedef, miktar); // transfer işlemi yapılır
    }

    // Faiz hesaplama metodumuz (vadeli hesaplar için)

    private static void faizHesapla()
    {
        Hesap h=hesapSec(); 
        if(h instanceof VadeliHesap vadeli) // eğer vadeli hesapsa
        {
            vadeli.faizEkle(); 
        }
        else 
        {
            System.out.println("\nbu vadeli hesap değil");
        }
    }

    // hesap özeti metodumuzda kaldık
    private static void hesapOzeti()
    {
        Hesap h=hesapSec(); 
        if(h != null)
        {
            h.hesapOzeti();
        }
    }

    private static void subeEkle()
    {
        System.out.println("Şube kodu (sayi): ");
        int kod=scan.nextInt();
        scan.nextLine(); // bufer temizleme
        System.out.println("Şube Adi: ");
        String ad=scan.nextLine();
        subeler.add(new Sube(kod, ad));
        System.out.println("Şube eklendi..");
    }

    private static void subeListele()
    {
        System.out.println("\n===Şubeler====");
        for(Sube s: subeler)
        {
            System.out.println(s);
        }
    }


}
