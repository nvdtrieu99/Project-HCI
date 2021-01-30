package com.example.quananvat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quananvat.R;
import com.example.quananvat.obj.GioHang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {

    private Context context;
    private ArrayList<GioHang> list;
    private OnItemClickListener mlistener;
    private boolean isSelectedAll;

    public GioHangAdapter(Context context, ArrayList<GioHang> arrGioHang) {
        this.context = context;
        this.list = arrGioHang;
    }

    public interface OnItemClickListener{
        void onDeleteClick(int position);
        void onPlus(int position);
        void onMinus(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }

    public void selectAll(){
        isSelectedAll=true;
        notifyDataSetChanged();
    }
    public void unselectall(){
        isSelectedAll=false;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GioHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_giohang, parent, false);
        GioHangAdapter.ViewHolder viewHolder = new GioHangAdapter.ViewHolder(view, mlistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.ViewHolder holder, int position) {
        holder.tv_soluong.setText(list.get(position).getSoLuong()+"");
//        holder.iv_hinh.setImageResource(list.get(position).getSanPham().getHinh());
        Picasso.get().load(list.get(position).getSanPham().getHinhanh()).into(holder.iv_hinh);
        holder.tv_tensanpham.setText(list.get(position).getSanPham().getTensanpham());
        boolean sale = list.get(position).getSanPham().isSale();
        if (sale){
            holder.iv_sale.setVisibility(View.VISIBLE);
            holder.ln_khuyenmai.setVisibility(View.VISIBLE);
            holder.ln_gia.setVisibility(View.INVISIBLE);
            holder.tv_giatruocsale.setText(list.get(position).getSanPham().getGiasanpham()+"VND");
            holder.tv_giasausale.setText(list.get(position).getSanPham().getGiakhisale()+"VND");
        }else{
            holder.iv_sale.setVisibility(View.INVISIBLE);
            holder.ln_khuyenmai.setVisibility(View.INVISIBLE);
            holder.ln_gia.setVisibility(View.VISIBLE);
            holder.tv_gia.setText(list.get(position).getSanPham().getGiasanpham()+"VND");
        }

        if (!isSelectedAll){
            holder.cb_tick.setChecked(false);
        }else {
            holder.cb_tick.setChecked(true);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private CheckBox cb_tick;
        private ImageView iv_hinh, iv_sale;
        private ImageButton ib_xoa;
        private AppCompatImageButton ib_plus, ib_minus;
        private TextView tv_tensanpham, tv_soluong, tv_gia, tv_giatruocsale, tv_giasausale;
        private LinearLayout ln_gia, ln_khuyenmai;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mapping(itemView);
            ib_xoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            ib_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cb_tick.isChecked()){
                        cb_tick.setChecked(true);
                    }else{
                        cb_tick.setChecked(false);
                    }
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onPlus(position);

                        }
                    }
                }
            });

            ib_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onMinus(position);
                        }
                    }
                }
            });

        }

        public void mapping(View v){
            cb_tick = (CheckBox) v.findViewById(R.id.cb_tick);
            iv_hinh = (ImageView) v.findViewById(R.id.iv_hinh);
            iv_sale = (ImageView) v.findViewById(R.id.iv_sale);
            ib_xoa = (ImageButton) v.findViewById(R.id.ib_xoa);
            ib_plus = (AppCompatImageButton) v.findViewById(R.id.ib_plus);
            ib_minus = (AppCompatImageButton) v.findViewById(R.id.ib_minus);
            tv_tensanpham = (TextView) v.findViewById(R.id.tv_tensanpham);
            tv_soluong = (TextView) v.findViewById(R.id.tv_soluong);
            tv_gia = (TextView) v.findViewById(R.id.tv_gia);
            tv_giatruocsale = (TextView) v.findViewById(R.id.tv_giatruocsale);
            tv_giasausale = (TextView) v.findViewById(R.id.tv_giasausale);
            ln_gia = (LinearLayout) v.findViewById(R.id.ln_gia);
            ln_khuyenmai = (LinearLayout) v.findViewById(R.id.ln_khuyenmai);
        }
    }
}
