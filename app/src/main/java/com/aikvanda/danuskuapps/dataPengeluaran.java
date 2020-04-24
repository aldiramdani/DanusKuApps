package com.aikvanda.danuskuapps;

/**
 * Created by Satria on 4/5/2018.
 */

public class dataPengeluaran {
    double danakeluar;
    String tanggalkeluar;
    String keterangankeluar;
    private byte[] image;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public dataPengeluaran(int danakeluar, String tanggalkeluar, String keterangankeluar, byte[] image) {
        this.danakeluar = danakeluar;
        this.tanggalkeluar = tanggalkeluar;
        this.keterangankeluar = keterangankeluar;
        this.image = image;
    }

    public double getDanakeluar() {
        return danakeluar;
    }

    public String getTanggalkeluar() {
        return tanggalkeluar;
    }

    public String getKeterangankeluar() {
        return keterangankeluar;
    }

}
