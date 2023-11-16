package com.barisproduction.aldimbunu.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import com.barisproduction.aldimbunu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class OdemeActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView araToplam,indirim,kargo,toplam, odemeAdres;
    Button satinAl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_odeme);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        toolbar = findViewById(R.id.odeme_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        double bakiye = 0.0;
        bakiye = getIntent().getDoubleExtra("bakiye",0.0);

        String adres;
        adres = getIntent().getStringExtra("adres");



        araToplam = findViewById(R.id.ara_toplam);
        indirim = findViewById(R.id.textView17);
        kargo = findViewById(R.id.textView18);
        toplam = findViewById(R.id.son_fiyat);
        satinAl = findViewById(R.id.ode_btn);
        odemeAdres = findViewById(R.id.odeme_adres);

        araToplam.setText(bakiye+"₺");
        toplam.setText(bakiye+"₺");
        odemeAdres.setText(adres);

        satinAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OdemeActivity.this, "Uygulama Test Aşamasında Olduğu İçin Sipariş Alamıyoruz!", Toast.LENGTH_LONG).show();
            }
        });

    }
}