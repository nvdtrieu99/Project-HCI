package com.example.quananvat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quananvat.adapter.DanhSachSanPhamAdapter;
import com.example.quananvat.fragment.HomeFragment;
import com.example.quananvat.obj.SanPham;

import java.util.ArrayList;

public class XemThemActivity extends AppCompatActivity {

    private ListView listView;
    private ImageButton ib_back;
    private TextView tv_tieude, tv_menu;
    private ArrayList<SanPham> list_spbc, list_spmoi, list_dexuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_them);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        list_spbc = (ArrayList<SanPham>) bundle.getSerializable("list_spbc");
        list_spmoi = (ArrayList<SanPham>) bundle.getSerializable("list_spmoi");

        mapping();
        onBack();
        if (list_spbc!=null){
            tv_tieude.setText("Sản phẩm bán chạy");
            tv_menu.setText("Menu sản phẩm bán chạy");
            DanhSachSanPhamAdapter adapter = new DanhSachSanPhamAdapter(this, list_spbc);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    list_dexuat = MainActivity.getDeXuat(HomeFragment.list_full, list_spbc.get(position));
                    Intent intent1 = new Intent(XemThemActivity.this, DetailActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("sanpham", list_spbc.get(position));
                    bundle1.putSerializable("mangdexuat", list_dexuat);
                    intent1.putExtra("dulieu", bundle1);
                    startActivity(intent1);
                }
            });
        }

        if (list_spmoi!=null){
            tv_tieude.setText("Sản phẩm mới");
            tv_menu.setText("Menu sản phẩm mới");
            DanhSachSanPhamAdapter adapter = new DanhSachSanPhamAdapter(this, list_spmoi);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    list_dexuat = MainActivity.getDeXuat(HomeFragment.list_full, list_spmoi.get(position));
                    Intent intent1 = new Intent(XemThemActivity.this, DetailActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("sanpham", list_spmoi.get(position));
                    bundle1.putSerializable("mangdexuat", list_dexuat);
                    intent1.putExtra("dulieu", bundle1);
                    startActivity(intent1);
                }
            });
        }
    }

    private void onBack() {
        ib_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void mapping(){
        listView = (ListView) findViewById(R.id.lv_spbc);
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        tv_tieude = (TextView) findViewById(R.id.tv_tieude);
        tv_menu = (TextView) findViewById(R.id.tv_menu);
    }
}