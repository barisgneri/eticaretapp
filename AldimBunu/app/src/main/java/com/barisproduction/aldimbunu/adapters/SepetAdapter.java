package com.barisproduction.aldimbunu.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.barisproduction.aldimbunu.R;
import com.barisproduction.aldimbunu.activities.AdresActivity;
import com.barisproduction.aldimbunu.activities.DetayActivity;
import com.barisproduction.aldimbunu.activities.OdemeActivity;
import com.barisproduction.aldimbunu.activities.SepetActivity;
import com.barisproduction.aldimbunu.models.SepetModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.core.ListenerRegistrationImpl;

import org.w3c.dom.Text;

import java.util.List;

public class SepetAdapter extends RecyclerView.Adapter<SepetAdapter.ViewHolder> {

    Context context;
    List<SepetModel> list;

    FirebaseFirestore firestore;
    FirebaseAuth auth;


    int toplamFiyat = 0;

    public SepetAdapter(Context context, List<SepetModel> list) {
        this.context = context;
        this.list = list;

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public SepetAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sepet_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SepetAdapter.ViewHolder holder, int position) {

        holder.tarih.setText(list.get(position).getEklenmeTarih());
        holder.isim.setText(list.get(position).getUrunIsim());
        holder.saat.setText(list.get(position).getEklenmeSaat());
        holder.fiyat.setText(list.get(position).getUrunFiyat()+"₺");
        holder.toplamFiyat.setText(String.valueOf(list.get(position).getToplamFiyat())+"₺");
        holder.toplamAdet.setText(list.get(position).getToplamAdet());


        //toplam fiyat verisini sepet activitye gönderme
        toplamFiyat = toplamFiyat + list.get(position).getToplamFiyat();
        Intent intent = new Intent("ToplamFiyat");
        intent.putExtra("toplamFiyat", toplamFiyat);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);



        holder.sil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firestore.collection("Kullanici").document(auth.getCurrentUser().getEmail())
                        .collection("SepettekiUrunler")
                        .document(list.get(position).getSepetUrunId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    list.remove(list.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Ürün Sepetten Çıkarıldı", Toast.LENGTH_SHORT).show();

                                }else {
                                    Toast.makeText(context, "Hata Oluştu"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView isim,fiyat,tarih,saat,toplamAdet,toplamFiyat;
        ImageView sil;
        Button satinAl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            satinAl = itemView.findViewById(R.id.satın_al);
            isim = itemView.findViewById(R.id.urun_isim);
            fiyat = itemView.findViewById(R.id.urun_fiyat);
            tarih = itemView.findViewById(R.id.kayit_tarihi);
            saat = itemView.findViewById(R.id.kayit_saati);
            toplamAdet = itemView.findViewById(R.id.toplam_adet);
            toplamFiyat = itemView.findViewById(R.id.toplam_fiyat);
            sil = itemView.findViewById(R.id.sil);
        }
    }
}
