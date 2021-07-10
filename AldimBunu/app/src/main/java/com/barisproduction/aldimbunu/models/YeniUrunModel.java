package com.barisproduction.aldimbunu.models;

import java.io.Serializable;

public class YeniUrunModel implements Serializable {

    String acıklama;
    String puan;
    String isim;
    String img_url;
    int fiyat;

    public YeniUrunModel() {
    }

    public YeniUrunModel(String acıklama, String puan, String isim, String img_url, int fiyat) {
        this.acıklama = acıklama;
        this.puan = puan;
        this.isim = isim;
        this.img_url = img_url;
        this.fiyat = fiyat;
    }

    public String getAcıklama() {
        return acıklama;
    }

    public void setAcıklama(String acıklama) {
        this.acıklama = acıklama;
    }

    public String getPuan() {
        return puan;
    }

    public void setPuan(String puan) {
        this.puan = puan;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getFiyat() {
        return fiyat;
    }

    public void setFiyat(int fiyat) {
        this.fiyat = fiyat;
    }
}
