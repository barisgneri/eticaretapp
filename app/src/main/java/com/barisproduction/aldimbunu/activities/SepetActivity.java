package com.barisproduction.aldimbunu.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.barisproduction.aldimbunu.R;
import com.barisproduction.aldimbunu.adapters.SepetAdapter;
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

public class SepetActivity extends AppCompatActivity {

    int toplamSepetFiyatı;
    TextView textView3, isim;
    Toolbar toolbar;
    RecyclerView recyclerView;
    List<SepetModel> sepetModelList;
    SepetAdapter sepetAdapter;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    Button satınAl;

    //Yeni Ürün
    YeniUrunModel yeniUrunModel = null;

    //Popüler Ürün
    PopulerUrunModel populerUrunModel = null;

    //Tüm Ürünler
    ShowAllModel showAllModel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sepet);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        textView3 = findViewById(R.id.textView3);
        toolbar = findViewById(R.id.sepet_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //SepetAdaapterden veriyi alma
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver,new IntentFilter("ToplamFiyat"));




        recyclerView = findViewById(R.id.sepet_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sepetModelList = new ArrayList<>();
        sepetAdapter = new SepetAdapter(this,sepetModelList);
        recyclerView.setAdapter(sepetAdapter);
        satınAl = findViewById(R.id.satın_al);

        satınAl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sepetModelList.isEmpty()){
                    Toast.makeText(SepetActivity.this, "Sepetiniz Boş", Toast.LENGTH_SHORT).show();
                }else{
                Intent intent = new Intent(SepetActivity.this,AdresActivity.class);
                startActivity(intent);}
            }
        });


        firestore.collection("Kullanici").document(auth.getCurrentUser().getEmail())
                .collection("SepettekiUrunler").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    for (DocumentSnapshot doc :task.getResult().getDocuments()){

                        String sepetUrunId = doc.getId();


                        SepetModel sepetModel = doc.toObject(SepetModel.class);

                        sepetModel.setSepetUrunId(doc.getId());
                        sepetModelList.add(sepetModel);
                        sepetAdapter.notifyDataSetChanged();

                    }
                }

            }
        });

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int totalBill = intent.getIntExtra("toplamFiyat",0);

            textView3.setText("Toplam Fiyat: "+totalBill+"₺");


        }
    };
}