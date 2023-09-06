package com.example.appuinsu.model;

public class ModelAbsen {
    private String id, nama, waktu, status;

    public ModelAbsen(){

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

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ModelAbsen(String id, String nama, String waktu, String status){
        this.id = id;
        this.nama = nama;
        this.waktu = waktu;
        this.status = status;
    }
}
