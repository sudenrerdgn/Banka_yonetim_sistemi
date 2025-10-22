🏦 Banka Yönetim Sistemi (Java OOP – Konsol Uygulaması)


📖 Proje Tanıtımı

Bu proje, Nesne Yönelimli Programlama (OOP) ilkelerine uygun olarak geliştirilmiş basit bir banka yönetim sistemidir.
Konsol tabanlı bu uygulamada kullanıcılar şube, müşteri ve hesap işlemlerini menü üzerinden gerçekleştirebilir.
Proje Java dilinde yazılmış olup sınıf, nesne, kapsülleme, kalıtım, çok biçimlilik gibi temel OOP kavramlarını içermektedir.


⚙️ Özellikler

🏢 Şube Yönetimi
-Yeni şube ekleme
-Mevcut şubeleri listeleme

👤 Müşteri Yönetimi
-Yeni müşteri ekleme
-Müşteri bilgilerini listeleme

💳 Hesap Yönetimi
-Müşteri adına vadesiz veya vadeli hesap açma
-Hesap türüne göre farklı işlem özellikleri
-Hesaplar arası para aktarımı

💰 İşlem Özellikleri
-Para yatırma
-Para çekme
-Hesap bakiyesi görüntüleme
-İşlem geçmişi (tarih, miktar, tür kaydı)
-Vadeli hesaplarda faiz hesaplama


🧩 Kullanılan Sınıflar

BankaUygulamasi:	Ana sınıf. Menüleri yönetir ve kullanıcı etkileşimini sağlar.
Musteri:	Müşteri bilgilerini tutar (ad, TC, iletişim vb.)
Hesap:	Tüm hesaplar için ortak özellikleri tanımlar (bakiye, işlemler, tarih vb.)
VadesizHesap:	Para yatırma ve çekme işlemlerinin yapılabildiği temel hesap türüdür.
VadeliHesap:	Vadesi dolduğunda faiz kazancı sağlayan hesap türüdür.
DovizHesap:  USD, EUR gibi yabancı para birimleriyle işlem yapılır.
Islem:	Her para yatırma/çekme veya faiz işlem kaydını temsil eder.
Sube:	Banka şubesi bilgisini tutar ve şubeye bağlı müşterileri listeler.


🕒 Örnek Konsol Görünümü
=== BANKA YÖNETİM SİSTEMİ ===
1. Müşteri Ekle
2. Hesap Aç
3. Para Yatır
4. Para Çek
5. Transfer Yap
6. Faiz Hesapla (Vadeli)
7. Hesap Özeti Görüntüle
8. Şube Ekle
9. Şubeleri Listele
0. exit
Seçiminiz: _



💻 Geliştirici

Ad: Sudenur Erdoğan
Ders: Nesne Yönelimli Programlama
Proje: Banka Yönetim Sistemi
