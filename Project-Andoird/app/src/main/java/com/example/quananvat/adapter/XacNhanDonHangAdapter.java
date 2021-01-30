package com.example.quananvat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quananvat.R;
import com.example.quananvat.obj.GioHang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class XacNhanDonHangAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<GioHang> arrayList;

    private ImageView iv_hinh;
    private TextView tv_tensanpham, tv_soluong, tv_gia;

    public XacNhanDonHangAdapter(Context context, ArrayList<GioHang> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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
        convertView = inflater.inflate(R.layout.listview_xacnhandonhang, null);
        mapping(convertView);
        if (arrayList.get(position).getSanPham()!=null){
//            iv_hinh.setImageResource(arrayList.get(position).getSanPham().getHinh());
            Picasso.get().load(arrayList.get(position).getSanPham().getHinhanh()).into(iv_hinh);
            tv_tensanpham.setText(arrayList.get(position).getSanPham().getTensanpham());
            if (arrayList.get(position).getSanPham().isSale()){
                tv_gia.setText(arrayList.get(position).getSanPham().getGiakhisale()+" VND");
            }else{
                tv_gia.setText(arrayList.get(position).getSanPham().getGiasanpham()+" VND");
            }
        } else {
            Picasso.get().load(arrayList.get(position).getCombo().getHinhanh()).into(iv_hinh);
            tv_tensanpham.setText(arrayList.get(position).getCombo().getCombo());
            tv_gia.setText(arrayList.get(position).getCombo().getGia()+"");
        }
        tv_soluong.setText("x"+arrayList.get(position).getSoLuong());

        return convertView;
    }

    private void mapping(View v){
        iv_hinh = (ImageView) v.findViewById(R.id.iv_hinh);
        tv_tensanpham = (TextView) v.findViewById(R.id.tv_tensanpham);
        tv_soluong = (TextView) v.findViewById(R.id.tv_soluong);
        tv_gia = (TextView) v.findViewById(R.id.tv_gia);
    }
}
