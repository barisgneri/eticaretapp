package com.barisproduction.aldimbunu.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.barisproduction.aldimbunu.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdresEkleActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText isim,adres,sehir,postaKodu,telNumara;
    Button adresEkle;


    FirebaseAuth auth;
    FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adres_ekle);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        toolbar = findViewById(R.id.adres_ekle_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        isim = findViewById(R.id.isim_ekle);
        adres = findViewById(R.id.adres_ekle);
        sehir = findViewById(R.id.sehir_ekle);
        postaKodu = findViewById(R.id.posta_ekle);
        telNumara = findViewById(R.id.numara_ekle);
        adresEkle = findViewById(R.id.BTNadres_ekle);

        adresEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kullaniciIsim = isim.getText().toString();
                String kullaniciAdres = adres.getText().toString();
                String kullaniciSehir = sehir.getText().toString();
                String kullaniciPosta = postaKodu.getText().toString();
                String kullaniciNumara = telNumara.getText().toString();

                String sonAdres = "";

                if (!kullaniciIsim.isEmpty()){
                    sonAdres+=kullaniciIsim+", ";
                }
                if (!kullaniciAdres.isEmpty()){
                    sonAdres+=kullaniciAdres+", ";
                }
                if (!kullaniciNumara.isEmpty()){
                    sonAdres+=kullaniciNumara+", ";
                }
                if (!kullaniciPosta.isEmpty()){
                    sonAdres+=kullaniciPosta+", ";
                }
                if (!kullaniciSehir.isEmpty()){
                    sonAdres+=kullaniciSehir+".";
                }
                if (!kullaniciIsim.isEmpty() && !kullaniciAdres.isEmpty() && !kullaniciNumara.isEmpty() && !kullaniciPosta.isEmpty() && !kullaniciSehir.isEmpty()){

                    Map<String,String> map = new HashMap<>();
                    map.put("kullaniciAdres",sonAdres);
                    firestore.collection("Kullanici").document(auth.getCurrentUser().getEmail())
                            .collection("Adres").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AdresEkleActivity.this, "Adres Kaydedildi.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AdresEkleActivity.this,AdresActivity.class));
                                finish();
                            }
                        }
                    });
                }else{
                    Toast.makeText(AdresEkleActivity.this, "Tüm Alanları Doldurmalısınız ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}