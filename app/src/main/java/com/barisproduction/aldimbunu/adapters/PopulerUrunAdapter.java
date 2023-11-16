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
import androidx.viewpager.widget.ViewPager;

import com.barisproduction.aldimbunu.R;
import com.barisproduction.aldimbunu.activities.DetayActivity;
import com.barisproduction.aldimbunu.models.PopulerUrunModel;
import com.barisproduction.aldimbunu.models.YeniUrunModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class PopulerUrunAdapter extends RecyclerView.Adapter<PopulerUrunAdapter.ViewHolder> {

    private Context context;
    private List<PopulerUrunModel> populerUrunModelList;

    public PopulerUrunAdapter(Context context, List<PopulerUrunModel> populerUrunModelList) {
        this.context = context;
        this.populerUrunModelList = populerUrunModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_urun,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PopulerUrunAdapter.ViewHolder holder, int position) {

        Glide.with(context).load(populerUrunModelList.get(position).getImg_url()).into(holder.popularResim);
        holder.popularIsım.setText(populerUrunModelList.get(position).getIsim());
        holder.popularFiyat.setText(String.valueOf(String.valueOf(populerUrunModelList.get(position).getFiyat())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetayActivity.class);
                intent.putExtra("detay",populerUrunModelList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return populerUrunModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView popularResim;
        TextView popularIsım, popularFiyat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popularResim = itemView.findViewById(R.id.populer_urun_img);
            popularFiyat = itemView.findViewById(R.id.populer_fiyat);
            popularIsım = itemView.findViewById(R.id.populer_urun_isim);
        }
    }
}
