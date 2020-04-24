package com.aikvanda.danuskuapps;

/**
 * Created by Aulia Ikvanda Yoren
 * on 13/03/2018.
 */

public class list {

    double dana;
    String tanggal;
    String keterangan;

    public list(double dana, String tanggal, String keterangan) {
        this.dana = dana;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
    }

    public double getDana() {
        return dana;
    }

    public String getTanggal() {

        return tanggal;
    }

    public String getKeterangan() {

        return keterangan;
    }
}
