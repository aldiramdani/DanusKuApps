package com.aikvanda.danuskuapps.About;

/**
 * Created by Satria on 4/25/2018.
 */

public class AboutUs {
    String nama ,job;

    public AboutUs(String nama, String job) {
        this.nama = nama;
        this.job = job;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
