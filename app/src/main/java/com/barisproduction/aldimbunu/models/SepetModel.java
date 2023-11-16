package com.barisproduction.aldimbunu.models;

public class SepetModel {

    String eklenmeSaat;
    String eklenmeTarih;
    String toplamAdet;
    String urunIsim;
    String urunFiyat;
    String sepetUrunId;
    int toplamFiyat;

    public SepetModel() {
    }

    public SepetModel(String eklenmeSaat, String eklenmeTarih, String toplamAdet, String urunIsim, String urunFiyat, int toplamFiyat) {
        this.eklenmeSaat = eklenmeSaat;
        this.eklenmeTarih = eklenmeTarih;
        this.toplamAdet = toplamAdet;
        this.urunIsim = urunIsim;
        this.urunFiyat = urunFiyat;
        this.toplamFiyat = toplamFiyat;
    }

    public String getSepetUrunId() {
        return sepetUrunId;
    }

    public void setSepetUrunId(String sepetUrunId) {
        this.sepetUrunId = sepetUrunId;
    }

    public String getEklenmeSaat() {
        return eklenmeSaat;
    }

    public void setEklenmeSaat(String eklenmeSaat) {
        this.eklenmeSaat = eklenmeSaat;
    }

    public String getEklenmeTarih() {
        return eklenmeTarih;
    }

    public void setEklenmeTarih(String eklenmeTarih) {
        this.eklenmeTarih = eklenmeTarih;
    }

    public String getToplamAdet() {
        return toplamAdet;
    }

    public void setToplamAdet(String toplamAdet) {
        this.toplamAdet = toplamAdet;
    }

    public String getUrunIsim() {
        return urunIsim;
    }

    public void setUrunIsim(String urunIsim) {
        this.urunIsim = urunIsim;
    }

    public String getUrunFiyat() {
        return urunFiyat;
    }

    public void setUrunFiyat(String urunFiyat) {
        this.urunFiyat = urunFiyat;
    }

    public int getToplamFiyat() {
        return toplamFiyat;
    }

    public void setToplamFiyat(int toplamFiyat) {
        this.toplamFiyat = toplamFiyat;
    }
}
