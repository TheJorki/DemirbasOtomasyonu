package com.company.Model;

/*
 *Created by Berkay KADAMLI
 */
public class KisiModel {

    private  String demirbasKayitTarih,stokKayitNo,verilen_malzeme_adi,birim,marka,model,seri_no,durumu,aciklama;

    public KisiModel(String demirbasKayitTarih, String stokKayitNo, String verilen_malzeme_adi, String birim, String marka, String model, String seri_no, String durumu, String aciklama) {
        this.demirbasKayitTarih = demirbasKayitTarih;
        this.stokKayitNo = stokKayitNo;
        this.verilen_malzeme_adi = verilen_malzeme_adi;
        this.birim = birim;
        this.marka = marka;
        this.model = model;
        this.seri_no = seri_no;
        this.durumu = durumu;
        this.aciklama = aciklama;
    }

    public String getDemirbasKayitTarih() {
        return demirbasKayitTarih;
    }

    public void setDemirbasKayitTarih(String demirbasKayitTarih) {
        this.demirbasKayitTarih = demirbasKayitTarih;
    }

    public String getStokKayitNo() {
        return stokKayitNo;
    }

    public void setStokKayitNo(String stokKayitNo) {
        this.stokKayitNo = stokKayitNo;
    }

    public String getVerilen_malzeme_adi() {
        return verilen_malzeme_adi;
    }

    public void setVerilen_malzeme_adi(String verilen_malzeme_adi) {
        this.verilen_malzeme_adi = verilen_malzeme_adi;
    }

    public String getBirim() {
        return birim;
    }

    public void setBirim(String birim) {
        this.birim = birim;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSeri_no() {
        return seri_no;
    }

    public void setSeri_no(String seri_no) {
        this.seri_no = seri_no;
    }

    public String getDurumu() {
        return durumu;
    }

    public void setDurumu(String durumu) {
        this.durumu = durumu;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }
}
