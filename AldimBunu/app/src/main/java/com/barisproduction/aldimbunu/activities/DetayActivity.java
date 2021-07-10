package com.barisproduction.aldimbunu.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;


import com.barisproduction.aldimbunu.R;
import com.barisproduction.aldimbunu.models.PopulerUrunModel;
import com.barisproduction.aldimbunu.models.ShowAllModel;
import com.barisproduction.aldimbunu.models.YeniUrunModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetayActivity extends AppCompatActivity {

    ImageView detayImg;
    TextView puan,isim,acıklama,fiyat,adet;
    Button sepeteEkle,satınAl;
    ImageView adetArttır, adetAzalt;

    Toolbar toolbar;

    int toplamAdet = 1;
    int toplamFiyat = 0;

    //Yeni Ürün
    YeniUrunModel yeniUrunModel = null;

    //Popüler Ürün
    PopulerUrunModel populerUrunModel = null;

    //Tüm Ürünler
    ShowAllModel showAllModel = null;

    FirebaseAuth auth;
    private FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        final Object obj = getIntent().getSerializableExtra("detay");

        if (obj instanceof YeniUrunModel){
            yeniUrunModel = (YeniUrunModel) obj;
        }else if (obj instanceof PopulerUrunModel) {
            populerUrunModel = (PopulerUrunModel) obj;
        }else if (obj instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) obj;
        }


        toolbar = findViewById(R.id.detay_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        detayImg = findViewById(R.id.detay_img);
        adet = findViewById(R.id.adet);
        puan = findViewById(R.id.puan);
        isim = findViewById(R.id.detay_isim);
        acıklama = findViewById(R.id.detay_acıklama);
        fiyat = findViewById(R.id.detay_fiyat);
        satınAl = findViewById(R.id.satın_al);
        sepeteEkle = findViewById(R.id.sepet_ekle);
        adetArttır = findViewById(R.id.adet_arttır);
        adetAzalt = findViewById(R.id.adet_azalt);


        //Yeni Ürün
        if (yeniUrunModel != null){
            Glide.with(getApplicationContext()).load(yeniUrunModel.getImg_url()).into(detayImg);
            isim.setText(yeniUrunModel.getIsim());
            acıklama.setText(yeniUrunModel.getAcıklama());
            puan.setText(yeniUrunModel.getPuan());
            fiyat.setText(String.valueOf(yeniUrunModel.getFiyat()));

            toplamFiyat = yeniUrunModel.getFiyat() * toplamAdet;

            toolbar.setTitle(yeniUrunModel.getIsim());

        }
        //Popüler Ürün
        if (populerUrunModel != null){
            Glide.with(getApplicationContext()).load(populerUrunModel.getImg_url()).into(detayImg);
            isim.setText(populerUrunModel.getIsim());
            acıklama.setText(populerUrunModel.getAcıklama());
            puan.setText(populerUrunModel.getPuan());
            fiyat.setText(String.valueOf(populerUrunModel.getFiyat()));

            toplamFiyat = populerUrunModel.getFiyat() * toplamAdet;

            toolbar.setTitle(populerUrunModel.getIsim());

        }

        //Tüm Ürünler
        if (showAllModel != null){
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detayImg);
            isim.setText(showAllModel.getIsim());
            acıklama.setText(showAllModel.getAcıklama());
            puan.setText(showAllModel.getPuan());
            fiyat.setText(String.valueOf(showAllModel.getFiyat()));

            toplamFiyat = showAllModel.getFiyat() * toplamAdet;

            toolbar.setTitle(showAllModel.getIsim());
        }

        //Şimdi Satın AL
        satınAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetayActivity.this,AdresActivity.class);
                if (yeniUrunModel != null){
                    intent.putExtra("item",yeniUrunModel);
                } if (populerUrunModel != null){
                    intent.putExtra("item",populerUrunModel);
                } if (showAllModel != null){
                    intent.putExtra("item",showAllModel);
                }
                startActivity(intent);
            }
        });


        //Sepete Ekle
        sepeteEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sepeteEkle();
            }
        });


        adetArttır.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (toplamAdet<10){
                    toplamAdet++;
                    adet.setText(String.valueOf(toplamAdet));

                    if (yeniUrunModel != null){
                        toplamFiyat = yeniUrunModel.getFiyat() * toplamAdet;
                    }
                    if (populerUrunModel != null){
                        toplamFiyat = populerUrunModel.getFiyat() * toplamAdet;
                    }
                    if (showAllModel != null){
                        toplamFiyat = showAllModel.getFiyat() * toplamAdet;
                    }
                }
            }
        });

        adetAzalt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (toplamAdet>1){
                    toplamAdet--;
                    adet.setText(String.valueOf(toplamAdet));


                }
            }
        });

    }

    private void sepeteEkle() {

        String kaydetSimdikiSaat, kaydetSimdikiTarih;

        Calendar cagırTarih = Calendar.getInstance();

        SimpleDateFormat simdikiTarih = new SimpleDateFormat("dd, MM, yyyy");
        kaydetSimdikiTarih = simdikiTarih.format(cagırTarih.getTime());

        SimpleDateFormat simdikiSaat = new SimpleDateFormat("HH:mm:ss a");
        kaydetSimdikiSaat = simdikiSaat.format(cagırTarih.getTime());

        final HashMap<String,Object> sepetMap = new HashMap<>();

        sepetMap.put("urunIsim",isim.getText().toString());
        sepetMap.put("urunFiyat",fiyat.getText().toString());
        sepetMap.put("eklenmeTarih",kaydetSimdikiTarih);
        sepetMap.put("eklenmeSaat",kaydetSimdikiSaat);
        sepetMap.put("toplamAdet",adet.getText().toString());
        sepetMap.put("toplamFiyat",toplamFiyat);

        firestore.collection("Kullanici").document(auth.getCurrentUser().getEmail())
                .collection("SepettekiUrunler").add(sepetMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetayActivity.this, "Sepete Eklendi!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}