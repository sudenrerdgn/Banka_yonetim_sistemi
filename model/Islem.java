package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Islem {
    private static int sayac = 1;
    private int islemId;
    private String tur;
    private double miktar;
    private LocalDateTime tarih;

    public Islem(String tur, double miktar) {
        this.islemId = sayac++;
        this.tur = tur;
        this.miktar = miktar;
        this.tarih = LocalDateTime.now();
    }

    public int getIslemId() {
        return islemId;
    }

    public String getTur() {
        return tur;
    }

    public double getMiktar() {
        return miktar;
    }

    public LocalDateTime getTarih() {
        return tarih;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return "[#" + islemId + "] " + tur + " | " + miktar + " TL | " + tarih.format(formatter);
    }
}
