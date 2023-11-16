package com.barisproduction.aldimbunu.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barisproduction.aldimbunu.R;
import com.barisproduction.aldimbunu.activities.DetayActivity;
import com.barisproduction.aldimbunu.models.YeniUrunModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class YeniUrunAdapter extends RecyclerView.Adapter<YeniUrunAdapter.ViewHolder> {

    private Context context;
    private List<YeniUrunModel> list;

    public YeniUrunAdapter(Context context, List<YeniUrunModel> list) {
        this.context = context;
        this.list = list;
    }
    

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.yeni_urun,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull YeniUrunAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.yeniResim);
        holder.yeniIsim.setText(list.get(position).getIsim());
        holder.yeniFiyat.setText(String.valueOf(list.get(position).getFiyat()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetayActivity.class);
                intent.putExtra("detay",list.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView yeniResim;
        TextView yeniIsim, yeniFiyat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            yeniResim = itemView.findViewById(R.id.yeni_img);
            yeniFiyat = itemView.findViewById(R.id.yeni_fiyat);
            yeniIsim = itemView.findViewById(R.id.yeni_isim);
        }
    }
}
