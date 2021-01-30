package com.example.quananvat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quananvat.R;
import com.example.quananvat.obj.GioHang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DangDenAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GioHang> list;

    private ImageView iv_hinh, iv_sale;
    private TextView tv_tensanpham, tv_danhgia, tv_giatruocsale, tv_giasausale, tv_gia, tv_soluong;
    private LinearLayout ln_gia, ln_khuyenmai;

    public DangDenAdapter(Context context, ArrayList<GioHang> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_dangden, null);
        mapping(convertView);
        tv_tensanpham.setText(list.get(position).getSanPham().getTensanpham());
        tv_danhgia.setText(list.get(position).getSanPham().getDanhgia()+"");
//        iv_hinh.setImageResource(list.get(position).getHinh());
        Picasso.get().load(list.get(position).getSanPham().getHinhanh()).into(iv_hinh);
        if (list.get(position).getSanPham().isSale()){
            ln_khuyenmai.setVisibility(View.VISIBLE);
            ln_gia.setVisibility(View.INVISIBLE);
            tv_giatruocsale.setText(list.get(position).getSanPham().getGiasanpham()+"VND");
            tv_giasausale.setText(list.get(position).getSanPham().getGiakhisale()+"VND");
            iv_sale.setVisibility(View.VISIBLE);
        } else {
            ln_khuyenmai.setVisibility(View.INVISIBLE);
            ln_gia.setVisibility(View.VISIBLE);
            tv_gia.setText(list.get(position).getSanPham().getGiasanpham()+"VND");
            iv_sale.setVisibility(View.INVISIBLE);
        }
        tv_soluong.setText("x"+list.get(position).getSoLuong());
        return convertView;
    }

    private void mapping(View v){
        ln_gia = (LinearLayout) v.findViewById(R.id.ln_gia);
        ln_khuyenmai = (LinearLayout) v.findViewById(R.id.ln_khuyenmai);
        iv_hinh = (ImageView) v.findViewById(R.id.iv_hinh);
        iv_sale = (ImageView) v.findViewById(R.id.iv_sale);
        tv_tensanpham = (TextView) v.findViewById(R.id.tv_tensanpham);
        tv_danhgia = (TextView) v.findViewById(R.id.tv_danhgia);
        tv_gia = (TextView) v.findViewById(R.id.tv_gia);
        tv_giatruocsale = (TextView) v.findViewById(R.id.tv_giatruocsale);
        tv_giasausale = (TextView) v.findViewById(R.id.tv_giasausale);
        tv_soluong = (TextView) v.findViewById(R.id.tv_soluong);
    }
}
