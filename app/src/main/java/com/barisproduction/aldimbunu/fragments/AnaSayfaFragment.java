package com.barisproduction.aldimbunu.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.barisproduction.aldimbunu.R;
import com.barisproduction.aldimbunu.activities.ShowAllActivity;
import com.barisproduction.aldimbunu.activities.ShowAllActivity;
import com.barisproduction.aldimbunu.adapters.KategoriAdapter;
import com.barisproduction.aldimbunu.adapters.PopulerUrunAdapter;
import com.barisproduction.aldimbunu.adapters.PopulerUrunAdapter;
import com.barisproduction.aldimbunu.adapters.YeniUrunAdapter;
import com.barisproduction.aldimbunu.models.KategoriModel;
import com.barisproduction.aldimbunu.models.PopulerUrunModel;
import com.barisproduction.aldimbunu.models.YeniUrunModel;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AnaSayfaFragment extends Fragment {

    TextView kategoriTumunuGor, yeniTumunuGor, populerTumunuGor;

    LinearLayout linearLayout;
    ProgressDialog progressDialog;
    RecyclerView kategoriRecyclerview, yeniUrunRecyclerview, populerUrunRecyclerview;

    //Kategori Recyclerview
    KategoriAdapter kategoriAdapter;
    List<KategoriModel> kategoriModelList;

    //Yeni Ürün Recyclerview
    YeniUrunAdapter yeniUrunAdapter;
    List<YeniUrunModel> yeniUrunModelList;

    //Popüler Ürün Recyclerview
    PopulerUrunAdapter popularUrunAdapter;
    List<PopulerUrunModel> popularUrunModelList;

    FirebaseFirestore db ;

    public AnaSayfaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View root = inflater.inflate(R.layout.fragment_ana_sayfa, container, false);

         //Tümünü Gör
        kategoriTumunuGor = root.findViewById(R.id.kategori_tumunu_gor);
        yeniTumunuGor = root.findViewById(R.id.yeniUrun_tumunu_gor);
        populerTumunuGor = root.findViewById(R.id.populer_tumunu_gor);

        kategoriTumunuGor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        yeniTumunuGor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        populerTumunuGor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

         progressDialog = new ProgressDialog(getActivity());

         kategoriRecyclerview = root.findViewById(R.id.kategori_rec);

         yeniUrunRecyclerview = root.findViewById(R.id.yeni_urun_rec);

        populerUrunRecyclerview = root.findViewById(R.id.populer_rec);

        db = FirebaseFirestore.getInstance();

        linearLayout = root.findViewById(R.id.anasayfa_layout);
        linearLayout.setVisibility(View.GONE);


         //image slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner1,"Ayakkabılarda %50'ye Varan İndirim", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2,"Efsane İndirimleri Kaçırma", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3,"Yılbaşına Özel %70 İndirim", ScaleTypes.CENTER_CROP));


        imageSlider.setImageList(slideModels);

        progressDialog.setMessage("Yükleniyor...");
        progressDialog.setTitle("Hoşgeldiniz!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //Kategori
        kategoriRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        kategoriModelList = new ArrayList<>();
        kategoriAdapter = new KategoriAdapter(getContext(),kategoriModelList);
        kategoriRecyclerview.setAdapter(kategoriAdapter);

        db.collection("Koleksiyon")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                KategoriModel kategoriModel = document.toObject(KategoriModel.class);
                                kategoriModelList.add(kategoriModel);
                                kategoriAdapter.notifyDataSetChanged();

                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();

                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Yeni Ürün
        yeniUrunRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        yeniUrunModelList = new ArrayList<>();
        yeniUrunAdapter = new YeniUrunAdapter(getContext(),yeniUrunModelList);
        yeniUrunRecyclerview.setAdapter(yeniUrunAdapter);

        db.collection("YeniUrun")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                YeniUrunModel yeniUrunModel = document.toObject(YeniUrunModel.class);
                                yeniUrunModelList.add(yeniUrunModel);
                                yeniUrunAdapter.notifyDataSetChanged();

                            }
                        } else {

                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        //Popüler Ürün
        populerUrunRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularUrunModelList = new ArrayList<>();
        popularUrunAdapter = new PopulerUrunAdapter(getContext(),popularUrunModelList);
        populerUrunRecyclerview.setAdapter(popularUrunAdapter);

        db.collection("PopulerUrun")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                PopulerUrunModel populerUrunModel = document.toObject(PopulerUrunModel.class);
                                popularUrunModelList.add(populerUrunModel);
                                popularUrunAdapter.notifyDataSetChanged();

                            }
                        } else {

                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

         return root;
    }
}