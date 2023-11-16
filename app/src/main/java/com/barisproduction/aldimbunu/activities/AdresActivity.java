package com.barisproduction.aldimbunu.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.barisproduction.aldimbunu.R;
import com.barisproduction.aldimbunu.adapters.AdresAdapter;
import com.barisproduction.aldimbunu.models.AdresModel;
import com.barisproduction.aldimbunu.models.PopulerUrunModel;
import com.barisproduction.aldimbunu.models.SepetModel;
import com.barisproduction.aldimbunu.models.ShowAllModel;
import com.barisproduction.aldimbunu.models.YeniUrunModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdresActivity extends AppCompatActivity implements  AdresAdapter.SecilenAdres{

    Button ekleAdres;
    RecyclerView recyclerView;
    private List<AdresModel> adresModelList;
    private AdresAdapter adresAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Button adresEkleBtn, odemeBtn;
    Toolbar toolbar;
    String adress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adres);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        toolbar = findViewById(R.id.adres_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // Detay activityden urun fiyat alama
        Object obj = getIntent().getSerializableExtra("item");


        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.adres_recycler);
        adresEkleBtn = findViewById(R.id.adres_ekle_btn);
        odemeBtn = findViewById(R.id.odeme_btn);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adresModelList = new ArrayList<>();
        adresAdapter = new AdresAdapter(getApplicationContext(),adresModelList,this);
        recyclerView.setAdapter(adresAdapter);

        firestore.collection("Kullanici").document(auth.getCurrentUser().getEmail())
                .collection("Adres").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (DocumentSnapshot doc :task.getResult().getDocuments()){



                        AdresModel adresModel = doc.toObject(AdresModel.class);

                        adresModelList.add(adresModel);
                        adresAdapter.notifyDataSetChanged();

                    }
                }

            }
        });

        odemeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double bakiye = 0.0;
                if (obj  instanceof YeniUrunModel){
                    YeniUrunModel yeniUrunModel = (YeniUrunModel) obj;
                    bakiye = yeniUrunModel.getFiyat();
                }
                if (obj  instanceof PopulerUrunModel){
                    PopulerUrunModel populerUrunModel = (PopulerUrunModel) obj;
                    bakiye = populerUrunModel.getFiyat();
                }
                if (obj  instanceof ShowAllModel){
                    ShowAllModel showAllModel = (ShowAllModel) obj;
                    bakiye = showAllModel.getFiyat();
                }

                Intent intent = new Intent(AdresActivity.this,OdemeActivity.class);
                intent.putExtra("adres",adress);
                intent.putExtra("bakiye",bakiye);
                startActivity(intent);


            }
        });

        ekleAdres = findViewById(R.id.adres_ekle_btn);

        ekleAdres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdresActivity.this,AdresEkleActivity.class));
            }
        });

    }

    @Override
    public void SetAdres(String adres) {
        adress = adres;
    }
}