package com.aikvanda.danuskuapps.Panitia;

/**
 * Created by Aulia Ikvanda Yoren
 * on 13/03/2018.
 */

public class data {

    int id;
    String nama, kelas, divisi, jadwal;

    public data(String nama,String kelas, String divisi,String jadwal) {
        this.nama = nama;
        this.kelas = kelas;
        this.divisi = divisi;
        this.jadwal = jadwal;
    }

    public String getNama() {

        return nama;
    }


    public String getKelas() {

        return kelas;
    }

    public String getDivisi() {

        return divisi;
    }

    public String getJadwal() {

        return jadwal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
