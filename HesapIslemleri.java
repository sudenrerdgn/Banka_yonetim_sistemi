public interface HesapIslemleri {
    void paraYatir(double miktar);
    void paraCek(double miktar);
    void transfer(Hesap hedef,double miktar);
    void hesapOzeti();
}
