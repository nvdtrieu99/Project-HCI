package com.example.quananvat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quananvat.MainActivity;
import com.example.quananvat.R;
import com.example.quananvat.obj.SanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

public class DanhSachSanPhamAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SanPham> list;
    private ArrayList<SanPham> phams;


    private ImageView iv_hinh, iv_sale;
    private TextView tv_tensanpham, tv_danhgia, tv_giatruocsale, tv_giasausale, tv_gia;
    private LinearLayout ln_gia, ln_khuyenmai;

    public DanhSachSanPhamAdapter(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
        this.phams = new ArrayList<>();
        this.phams.addAll(list);
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
        mapping(convertView);

        tv_tensanpham.setText(list.get(position).getTensanpham());
        tv_danhgia.setText(list.get(position).getDanhgia()+"");
//        iv_hinh.setImageResource(list.get(position).getHinh());
        Picasso.get().load(list.get(position).getHinhanh()).into(iv_hinh);
        if (list.get(position).isSale()){
            ln_khuyenmai.setVisibility(View.VISIBLE);
            ln_gia.setVisibility(View.INVISIBLE);
            tv_giatruocsale.setText(list.get(position).getGiasanpham()+"VND");
            tv_giasausale.setText(list.get(position).getGiakhisale()+"VND");
            iv_sale.setVisibility(View.VISIBLE);
        } else {
            ln_khuyenmai.setVisibility(View.INVISIBLE);
            ln_gia.setVisibility(View.VISIBLE);
            tv_gia.setText(list.get(position).getGiasanpham()+"VND");
            iv_sale.setVisibility(View.INVISIBLE);
        }

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
    }

    public void filter(String charText){
        charText = MainActivity.chuanHoaChuoi(charText.toLowerCase(Locale.getDefault()));
        list.clear();
        if (charText.length()==0){
            list.addAll(phams);
        }else{
            for (SanPham sp: phams){
                if (MainActivity.chuanHoaChuoi(sp.getTensanpham().toLowerCase(Locale.getDefault())).contains(charText)){
                    list.add(sp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
