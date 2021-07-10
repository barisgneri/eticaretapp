package com.barisproduction.aldimbunu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barisproduction.aldimbunu.R;
import com.barisproduction.aldimbunu.models.AdresModel;

import java.util.List;
import java.util.zip.Inflater;

public class AdresAdapter extends RecyclerView.Adapter<AdresAdapter.ViewHolder> {

    Context context;
    List<AdresModel> adresModelList;
    SecilenAdres secilenAdres;

    private RadioButton secilenRadioButton;

    public AdresAdapter(Context context, List<AdresModel> adresModelList, SecilenAdres secilenAdres) {
        this.context = context;
        this.adresModelList = adresModelList;
        this.secilenAdres = secilenAdres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adres_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdresAdapter.ViewHolder holder, int position) {

        holder.adres.setText(adresModelList.get(position).getKullaniciAdres());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AdresModel adres:adresModelList){
                    adres.setSecilen(false);
                }
                adresModelList.get(position).setSecilen(true);
                if (secilenRadioButton!=null){
                    secilenRadioButton.setChecked(false);
                }
                secilenRadioButton = (RadioButton) v;
                secilenRadioButton.setChecked(true);
                secilenAdres.SetAdres(adresModelList.get(position).getKullaniciAdres());
            }
        });

    }

    @Override
    public int getItemCount() {
        return adresModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView adres;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            adres = itemView.findViewById(R.id.adres_ekle);
            radioButton = itemView.findViewById(R.id.adres_secim);
        }
    }

    public interface SecilenAdres{
        void SetAdres(String adres);
    }
}
