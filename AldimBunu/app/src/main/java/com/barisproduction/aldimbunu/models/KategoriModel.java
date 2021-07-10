package com.barisproduction.aldimbunu.models;

public class KategoriModel {

    String img_url;
    String isim;
    String type;

    public KategoriModel() {
    }

    public KategoriModel(String img_url, String isim, String type) {
        this.img_url = img_url;
        this.isim = isim;
        this.type = type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
