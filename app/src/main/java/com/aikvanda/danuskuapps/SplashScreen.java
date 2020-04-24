package com.aikvanda.danuskuapps;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    private static int splashInterval = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent i = new Intent(SplashScreen.this, DashboardMenu.class);
                startActivity(i); // menghubungkan activity splashscren ke main activity dengan intent
                this.finish();//jeda selesai Splashscreen
            }

            private void finish() {
                // TODO Auto-generated method stub
            }
        }, splashInterval);
    };
}
