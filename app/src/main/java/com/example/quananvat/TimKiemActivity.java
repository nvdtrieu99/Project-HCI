package com.example.quananvat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.quananvat.adapter.DanhSachSanPhamAdapter;
import com.example.quananvat.fragment.HomeFragment;
import com.example.quananvat.obj.SanPham;

import java.util.ArrayList;

public class TimKiemActivity extends AppCompatActivity {

    private ImageButton ib_back;
    private SearchView searchView;
    private ListView lv_timkiem;
    private ArrayList<SanPham> list_timkiem;
    private DanhSachSanPhamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        mapping();
        searchView.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
        list_timkiem = new ArrayList<>();

        if (HomeFragment.list_full!=null){
            list_timkiem.addAll(HomeFragment.list_full);
        }

        adapter = new DanhSachSanPhamAdapter(TimKiemActivity.this, list_timkiem);
        lv_timkiem.setAdapter(adapter);
        lv_timkiem.setOnItemClickListener((parent, view, position, id) -> {
            ArrayList<SanPham> list_dexuat = MainActivity.getDeXuat(HomeFragment.list_full, list_timkiem.get(position));
            Intent intent1 = new Intent(TimKiemActivity.this, DetailActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("sanpham", list_timkiem.get(position));
            bundle1.putSerializable("mangdexuat", list_dexuat);
            intent1.putExtra("dulieu", bundle1);
            startActivity(intent1);
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    adapter.filter("");
                    lv_timkiem.clearTextFilter();
                    lv_timkiem.setVisibility(View.INVISIBLE);
                }else{
                    lv_timkiem.setVisibility(View.VISIBLE);
                    adapter.filter(newText);
                }
                return true;
            }
        });


        ib_back.setOnClickListener(v -> {
            onBackPressed();

        });
    }



    private void mapping() {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        searchView = (SearchView) findViewById(R.id.search_view);
        lv_timkiem = (ListView) findViewById(R.id.lv_timkiem);
    }
}