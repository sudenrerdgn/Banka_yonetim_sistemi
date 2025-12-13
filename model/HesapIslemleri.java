package model;

public interface HesapIslemleri {
    void paraYatir(double miktar);

    boolean paraCek(double miktar);

    boolean transfer(Hesap hedef, double miktar);

    void hesapOzeti();
}
