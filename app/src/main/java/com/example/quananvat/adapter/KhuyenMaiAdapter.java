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
import com.example.quananvat.obj.SanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class KhuyenMaiAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SanPham> list;



    public KhuyenMaiAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    public void addListItemAdapter(ArrayList<SanPham> itemplus){
        list.addAll(itemplus);
        this.notifyDataSetChanged();
    }

    public void removeListItemAdapter(ArrayList<SanPham> itemRemove){
        list.removeAll(itemRemove);
        this.notifyDataSetChanged();
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
        convertView = inflater.inflate(R.layout.listview_khuyenmai, null);

        TextView txtTen = (TextView) convertView.findViewById(R.id.tv_tensanpham);
        TextView txtDanhGia = (TextView) convertView.findViewById(R.id.tv_danhgia);
        TextView txtGiaTruocSale = (TextView) convertView.findViewById(R.id.tv_giatruocsale);
        TextView txtGiaSauSale = (TextView) convertView.findViewById(R.id.tv_giasausale);
        ImageView imgHinh = (ImageView) convertView.findViewById(R.id.iv_hinh);

        txtTen.setText(list.get(position).getTensanpham());
        txtDanhGia.setText(list.get(position).getDanhgia()+"");
//        imgHinh.setImageResource(list.get(position).getHinh());
        Picasso.get().load(list.get(position).getHinhanh()).into(imgHinh);
        txtGiaTruocSale.setText(list.get(position).getGiasanpham()+"VND");
        txtGiaSauSale.setText(list.get(position).getGiakhisale()+"VND");


        return convertView;
    }
}
