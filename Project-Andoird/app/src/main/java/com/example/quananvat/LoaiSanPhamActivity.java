package com.example.quananvat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quananvat.adapter.DanhSachSanPhamAdapter;
import com.example.quananvat.fragment.HomeFragment;
import com.example.quananvat.obj.LoaiSanPham;
import com.example.quananvat.obj.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoaiSanPhamActivity extends AppCompatActivity {

    private TextView tv_tenloaisp, tv_menu;
    private ListView lv_dssp;
    private ImageButton ib_back;
    private ArrayList<SanPham> listSanPham, list_dexuat;
    private DanhSachSanPhamAdapter adapter;
    private LoaiSanPham loaiSanPham;
    private DatabaseReference mdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_san_pham);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        loaiSanPham = (LoaiSanPham) bundle.getSerializable("loaisanpham");
        mdata = FirebaseDatabase.getInstance().getReference();

        mapping();
        onBack();
        initTextView();
        initListSanPham();
        adapter = new DanhSachSanPhamAdapter(this, listSanPham);
        lv_dssp.setAdapter(adapter);
        lv_dssp.setOnItemClickListener((parent, view, position, id) -> {
            list_dexuat = MainActivity.getDeXuat(HomeFragment.list_full, listSanPham.get(position));
            Intent intent1 = new Intent(LoaiSanPhamActivity.this, DetailActivity.class);
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("sanpham", listSanPham.get(position));
            bundle1.putSerializable("mangdexuat", list_dexuat);
            intent1.putExtra("dulieu", bundle1);
            startActivity(intent1);
        });




    }

    private void initTextView() {
        tv_tenloaisp.setText(loaiSanPham.getTen());
        tv_menu.setText("Menu "+loaiSanPham.getTen());
    }

    private void initListSanPham() {
        listSanPham = new ArrayList<>();
        mdata.child("san_pham").child(loaiSanPham.getTen()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    SanPham sp = ds.getValue(SanPham.class);
                    listSanPham.add(sp);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void onBack() {
        ib_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void mapping(){
        tv_tenloaisp = (TextView) findViewById(R.id.tv_tenloai);
        tv_menu = (TextView) findViewById(R.id.tv_menu);
        lv_dssp = (ListView) findViewById(R.id.lv_dssp);
        ib_back = (ImageButton) findViewById(R.id.ib_back);
    }
}