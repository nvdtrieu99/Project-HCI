package com.example.quananvat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quananvat.DetailActivity;
import com.example.quananvat.R;
import com.example.quananvat.adapter.DanhSachSanPhamAdapter;
import com.example.quananvat.obj.SanPham;

import java.util.ArrayList;

public class FragmentDeXuat extends Fragment {
    private View view;
    private ListView lv_dexuat;
    private ArrayList<SanPham> arr_dexuat = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dexuat, container, false);
        mapping(view);
        getDeXuat();
        DanhSachSanPhamAdapter adapter = new DanhSachSanPhamAdapter(view.getContext(), arr_dexuat);
        lv_dexuat.setAdapter(adapter);
        return view;
    }

    private void mapping(View v){
        lv_dexuat = v.findViewById(R.id.lv_dexuat);
    }

    private void getDeXuat(){
        DetailActivity detailActivity = (DetailActivity) getActivity();
        if (detailActivity.getDeXuat().size()!=0 && detailActivity.getDeXuat()!= null){
            arr_dexuat.addAll(detailActivity.getDeXuat());
        }
    }
}
