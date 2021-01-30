package com.example.quananvat.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quananvat.LoaiSanPhamActivity;
import com.example.quananvat.R;
import com.example.quananvat.interfacecustom.ItemClickListener;
import com.example.quananvat.obj.LoaiSanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoaiSanPhamAdapter extends RecyclerView.Adapter<LoaiSanPhamAdapter.ViewHolder> {

    private Context context;
    private ArrayList<LoaiSanPham> arrloaisp;


    public LoaiSanPhamAdapter(Context context, ArrayList<LoaiSanPham> arrloaisp) {
        this.context = context;
        this.arrloaisp = arrloaisp;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_loaisanpham, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_ten.setText(arrloaisp.get(position).getTen());
//        holder.iv_hinh.setImageResource(arrloaisp.get(position).getHinh());
        Picasso.get().load(arrloaisp.get(position).getHinh()).into(holder.iv_hinh);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(context, LoaiSanPhamActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("loaisanpham", arrloaisp.get(position));
                intent.putExtra("data", bundle);
                context.startActivity(intent);
            }

            @Override
            public void onLove(int position) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return arrloaisp.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CircleImageView iv_hinh;
        private TextView tv_ten;
        private ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_hinh = (CircleImageView) itemView.findViewById(R.id.iv_hinh);
            tv_ten = (TextView) itemView.findViewById(R.id.tv_ten);

            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(getAdapterPosition());
        }
    }
}
