package com.barisproduction.aldimbunu.adapters;

import android.content.Context;
import android.content.Intent;
import android.print.PageRange;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.barisproduction.aldimbunu.R;
import com.barisproduction.aldimbunu.activities.ShowAllActivity;
import com.barisproduction.aldimbunu.models.KategoriModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.VievHolder> {

    private Context context;
    private List<KategoriModel> list;

    public KategoriAdapter(Context context, List<KategoriModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VievHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VievHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.kategori_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriAdapter.VievHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImg_url()).into(holder.kategoriImg);
        holder.kategoriName.setText(list.get(position).getIsim());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowAllActivity.class);
                intent.putExtra("type",list.get(position).getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VievHolder extends RecyclerView.ViewHolder {

        ImageView kategoriImg;
        TextView kategoriName;

        public VievHolder(@NonNull View itemView) {
            super(itemView);
            kategoriImg = itemView.findViewById(R.id.kategori_resmi);
            kategoriName = itemView.findViewById(R.id.kategori_ismi);
        }
    }
}
