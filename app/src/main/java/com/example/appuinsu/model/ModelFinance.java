package com.example.appuinsu.model;

public class ModelFinance {
    private String id, nama, keterangan, nominal, jenis;

    public ModelFinance(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public ModelFinance(String id, String nama, String keterangan, String nominal, String jenis){
        this.id = id;
        this.nama = nama;
        this.keterangan = keterangan;
        this.nominal = nominal;
        this.jenis = jenis;
    }
}
