package com.example.quananvat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.quananvat.MainActivity;
import com.example.quananvat.R;
import com.example.quananvat.adapter.DangDenAdapter;
import com.example.quananvat.obj.ChiTietDonHang;
import com.example.quananvat.obj.DonHang;
import com.example.quananvat.obj.GioHang;
import com.example.quananvat.obj.SanPham;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FragmentDangDen extends Fragment {
    private View view;
    private CardView cv_dangden;
    private ListView lv_dangden;
    private DangDenAdapter adapter;
    private DatabaseReference data;
    private ArrayList<GioHang> gioHangArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dangden, container, false);
        mapping(view);
        data = FirebaseDatabase.getInstance().getReference();
        ArrayList<ChiTietDonHang> chiTietDonHangs = new ArrayList<>();
        if (MainActivity.nguoiDung!=null){
            for (DonHang dh: MainActivity.nguoiDung.getDanhsachdonhang()){
                if (!dh.isTrangthaigiaohang()){
                    chiTietDonHangs.addAll(dh.getChitietdonhang());
//                    chiTietDonHangs = dh.getChitietdonhang();

                }
            }
            if (chiTietDonHangs!=null){
                cv_dangden.setVisibility(View.INVISIBLE);
                lv_dangden.setVisibility(View.VISIBLE);
                gioHangArrayList = new ArrayList<>();

                for (ChiTietDonHang ct: chiTietDonHangs){
                    for (SanPham sp: HomeFragment.list_full){
                        if (ct.getIdsanpham().equals(sp.getId())){
                            gioHangArrayList.add(new GioHang(sp, ct.getSoluong()));
                        }
                    }
                }
                adapter = new DangDenAdapter(getActivity(), gioHangArrayList);
                lv_dangden.setAdapter(adapter);
            }else{
                cv_dangden.setVisibility(View.VISIBLE);
                lv_dangden.setVisibility(View.INVISIBLE);
            }
        }else{
            cv_dangden.setVisibility(View.VISIBLE);
            lv_dangden.setVisibility(View.INVISIBLE);
        }
        return view;
    }

    private void mapping(View view) {
        cv_dangden = (CardView) view.findViewById(R.id.cv_dangden);
        lv_dangden = (ListView) view.findViewById(R.id.lv_dangden);
    }
}
