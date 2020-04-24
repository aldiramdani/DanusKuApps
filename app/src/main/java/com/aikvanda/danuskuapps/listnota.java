package com.aikvanda.danuskuapps;

/**
 * Created by Satria on 4/5/2018.
 */

public class listnota {

    private String tanggal;
    private String jumlahnota;
    private String keterangannota;
    private byte[] image;

    public listnota(String tanggal, String jumlahnota, String keterangannota, byte[] image) {
        this.tanggal = tanggal;
        this.jumlahnota = jumlahnota;
        this.keterangannota = keterangannota;
        this.image = image;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJumlahnota() {
        return jumlahnota;
    }

    public String getKeterangannota() {
        return keterangannota;
    }

    public byte[] getImage() {
        return image;
    }
}
