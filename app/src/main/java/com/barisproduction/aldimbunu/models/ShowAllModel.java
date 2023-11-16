package com.barisproduction.aldimbunu.models;

import java.io.Serializable;

public class ShowAllModel implements Serializable {

    String acıklama;
    String puan;
    String isim;
    String img_url;
    String type;
    int fiyat;

    public ShowAllModel() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFiyat() {
        return fiyat;
    }

    public void setFiyat(int fiyat) {
        this.fiyat = fiyat;
    }

    public ShowAllModel(String acıklama, String puan, String isim, String img_url, String type, int fiyat) {
        this.acıklama = acıklama;
        this.puan = puan;
        this.isim = isim;
        this.img_url = img_url;
        this.type = type;
        this.fiyat = fiyat;
    }
}
