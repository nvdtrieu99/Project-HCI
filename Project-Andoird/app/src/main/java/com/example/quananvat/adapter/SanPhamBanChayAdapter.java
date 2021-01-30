package com.example.quananvat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quananvat.R;
import com.example.quananvat.interfacecustom.ItemClickListener;
import com.example.quananvat.obj.SanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SanPhamBanChayAdapter extends RecyclerView.Adapter<SanPhamBanChayAdapter.ViewHolder> {

    private ArrayList<SanPham> listspbc;
    private Context context;
    private ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }

    public SanPhamBanChayAdapter(Context context, ArrayList<SanPham> listspbc) {
        this.listspbc = listspbc;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_spbc, parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTenSanPham.setText(listspbc.get(position).getTensanpham());
        holder.txtDanhGia.setText(listspbc.get(position).getDanhgia()+"");
//        holder.imgHinh.setImageResource(listspbc.get(position).getHinh());
        Picasso.get().load(listspbc.get(position).getHinhanh()).into(holder.imgHinh);
        if (listspbc.get(position).isSale()){
            holder.lnKhuyenMai.setVisibility(View.VISIBLE);
            holder.imgSale.setVisibility(View.VISIBLE);
            holder.txtGia.setVisibility(View.INVISIBLE);
            holder.txtGiaTruocSale.setText(listspbc.get(position).getGiasanpham()+"VND");
            holder.txtGiaSauSale.setText(listspbc.get(position).getGiakhisale()+"VND");
        }else{
            holder.lnKhuyenMai.setVisibility(View.INVISIBLE);
            holder.imgSale.setVisibility(View.INVISIBLE);
            holder.txtGia.setVisibility(View.VISIBLE);
            holder.txtGia.setText(listspbc.get(position).getGiasanpham()+"VND");
        }
    }

    @Override
    public int getItemCount() {
        if (listspbc.size()>5){
            return 5;
        }
        return listspbc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTenSanPham;
        TextView txtDanhGia;
        TextView txtGia, txtGiaTruocSale, txtGiaSauSale;
        ImageView imgHinh, imgSale, iv_yeuthich, iv_yeuthich_active;
        LinearLayout lnKhuyenMai;
        RelativeLayout rl_yeuthich;

        public ViewHolder(View itemView, ItemClickListener listener) {
            super(itemView);
            mapping(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onClick(position);

                        }
                    }
                }
            });

            rl_yeuthich.setOnClickListener(v -> {
                if (listener!=null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onLove(position);

                        if(iv_yeuthich.getVisibility()==View.VISIBLE){
                            iv_yeuthich.setVisibility(View.INVISIBLE);
                            iv_yeuthich_active.setVisibility(View.VISIBLE);
                        } else{
                            iv_yeuthich.setVisibility(View.VISIBLE);
                            iv_yeuthich_active.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            });


        }

        public void mapping(View itemView){
            txtTenSanPham = (TextView) itemView.findViewById(R.id.tv_tensanpham);
            txtDanhGia = (TextView) itemView.findViewById(R.id.tv_danhgia);
            txtGia = (TextView) itemView.findViewById(R.id.tv_gia);
            lnKhuyenMai = (LinearLayout) itemView.findViewById(R.id.ln_khuyenmai);
            txtGiaTruocSale = (TextView) itemView.findViewById(R.id.tv_giatruocsale);
            txtGiaSauSale = (TextView) itemView.findViewById(R.id.tv_giasausale);
            imgSale = (ImageView) itemView.findViewById(R.id.iv_sale);
            imgHinh = (ImageView) itemView.findViewById(R.id.iv_hinh);
            rl_yeuthich = (RelativeLayout) itemView.findViewById(R.id.rl_yeuthich);
            iv_yeuthich = (ImageView) itemView.findViewById(R.id.iv_yeuthich);
            iv_yeuthich_active = (ImageView) itemView.findViewById(R.id.iv_yeuthich_active);
        }
    }


}
