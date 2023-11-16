package com.barisproduction.aldimbunu.models;

public class AdresModel {

    String kullaniciAdres;
    boolean secilen;

    public AdresModel() {
    }

    public String getKullaniciAdres() {
        return kullaniciAdres;
    }

    public void setKullaniciAdres(String kullaniciAdres) {
        this.kullaniciAdres = kullaniciAdres;
    }

    public boolean isSecilen() {
        return secilen;
    }

    public void setSecilen(boolean secilen) {
        this.secilen = secilen;
    }
}
