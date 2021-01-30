package com.example.quananvat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quananvat.R;
import com.example.quananvat.obj.DiaChiNhanHang;

import java.util.ArrayList;

public class DiaChiAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DiaChiNhanHang> list;

    private TextView tv_ten, tv_diachicuthe, tv_phuongxa, tv_quanhuyen, tv_tinhthanhpho, tv_macdinh, tv_sdt;

    public DiaChiAdapter(Context context, ArrayList<DiaChiNhanHang> list) {
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
        convertView = inflater.inflate(R.layout.listview_diachi, null);
        mapping(convertView);
        tv_ten.setText(list.get(position).getTennguoinhan());
        tv_sdt.setText(list.get(position).getSdtnguoinhan());
        tv_diachicuthe.setText(list.get(position).getDiachicuthe());
        tv_phuongxa.setText(list.get(position).getPhuong_xa());
        tv_quanhuyen.setText(list.get(position).getQuan_huyen());
        tv_tinhthanhpho.setText(list.get(position).getTinh_thanhpho());
        if(list.get(position).getDiachimacdinh()){
            tv_macdinh.setVisibility(View.VISIBLE);
        }else {
            tv_macdinh.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    private void mapping(View v) {
        tv_ten = (TextView) v.findViewById(R.id.tv_ten);
        tv_sdt = (TextView) v.findViewById(R.id.tv_sdtkh);
        tv_diachicuthe = (TextView) v.findViewById(R.id.tv_diachicuthe);
        tv_phuongxa = (TextView) v.findViewById(R.id.tv_phuongxa);
        tv_quanhuyen = (TextView) v.findViewById(R.id.tv_quanhuyen);
        tv_tinhthanhpho = (TextView) v.findViewById(R.id.tv_tinhthanhpho);
        tv_macdinh = (TextView) v.findViewById(R.id.tv_dcmacdinh);
    }
}
