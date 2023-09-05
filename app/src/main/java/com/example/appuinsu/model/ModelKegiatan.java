package com.example.appuinsu.model;

public class ModelKegiatan {
    private int id;
    private String nama, waktu, tempat, deskripsi, foto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public ModelKegiatan(int id, String nama, String waktu, String tempat, String deskripsi, String foto){
        this.id = id;
        this.nama = nama;
        this.waktu = waktu;
        this.tempat = tempat;
        this.deskripsi = deskripsi;
        this.foto = foto;
    }
}
