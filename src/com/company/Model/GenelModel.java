package com.company.Model;

/*
 *Created by Berkay KADAMLI
 */
public class GenelModel {
    private int id;
    private String adsoyad;
    private String bilgisayar;
    private String laptop;
    private String sistem;
    private String monitor_1;
    private String monitor_2;
    private String mouse;
    private String klavye;
    private String microsoft_office;
    private String meslek;

    public GenelModel(int id, String adsoyad, String bilgisayar, String laptop, String sistem, String monitor_1, String monitor_2, String mouse, String klavye, String microsoft_office, String meslek) {
        this.id = id;
        this.adsoyad = adsoyad;
        this.bilgisayar = bilgisayar;
        this.laptop = laptop;
        this.sistem = sistem;
        this.monitor_1 = monitor_1;
        this.monitor_2 = monitor_2;
        this.mouse = mouse;
        this.klavye = klavye;
        this.microsoft_office = microsoft_office;
        this.meslek = meslek;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdsoyad() {
        return adsoyad;
    }

    public void setAdsoyad(String adsoyad) {
        this.adsoyad = adsoyad;
    }

    public String getBilgisayar() {
        return bilgisayar;
    }

    public void setBilgisayar(String bilgisayar) {
        this.bilgisayar = bilgisayar;
    }

    public String getLaptop() {
        return laptop;
    }

    public void setLaptop(String laptop) {
        this.laptop = laptop;
    }

    public String getSistem() {
        return sistem;
    }

    public void setSistem(String sistem) {
        this.sistem = sistem;
    }

    public String getMonitor_1() {
        return monitor_1;
    }

    public void setMonitor_1(String monitor_1) {
        this.monitor_1 = monitor_1;
    }

    public String getMonitor_2() {
        return monitor_2;
    }

    public void setMonitor_2(String monitor_2) {
        this.monitor_2 = monitor_2;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public String getKlavye() {
        return klavye;
    }

    public void setKlavye(String klavye) {
        this.klavye = klavye;
    }

    public String getMicrosoft_office() {
        return microsoft_office;
    }

    public void setMicrosoft_office(String microsoft_office) {
        this.microsoft_office = microsoft_office;
    }

    public String getMeslek() {
        return meslek;
    }

    public void setMeslek(String meslek) {
        this.meslek = meslek;
    }
}
