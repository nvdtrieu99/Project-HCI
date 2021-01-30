package com.example.quananvat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quananvat.R;
import com.example.quananvat.interfacecustom.ItemClickListener;
import com.example.quananvat.obj.Combo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Combo> list;

    private ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }

    public ComboAdapter(Context context, ArrayList<Combo> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_combo, parent, false);
        return new ComboAdapter.ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtcombo.setText(list.get(position).getCombo());
        holder.txtmota.setText(list.get(position).getMota());
        holder.txtgia.setText(list.get(position).getGia()+"VND");
//        holder.imghinh.setImageResource(list.get(position).getHinh());
        Picasso.get().load(list.get(position).getHinhanh()).into(holder.imghinh);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtcombo, txtmota, txtgia;
        private ImageView imghinh;

        public ViewHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);
            txtcombo = (TextView) itemView.findViewById(R.id.tv_combo);
            txtmota = (TextView) itemView.findViewById(R.id.tv_motacombo);
            txtgia = (TextView) itemView.findViewById(R.id.tv_giacombo);
            imghinh = (ImageView) itemView.findViewById(R.id.iv_hinh);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onClick(position);
                    }
                }
            });
        }
    }
}
