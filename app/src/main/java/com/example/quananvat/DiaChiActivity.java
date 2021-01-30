package com.example.quananvat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quananvat.adapter.DiaChiAdapter;
import com.example.quananvat.obj.DiaChiNhanHang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DiaChiActivity extends AppCompatActivity {

    private ImageButton ib_back;
    private Button btn_themdiachi;
    private ListView lv_diachi;
    private DiaChiAdapter diaChiAdapter;
    private ArrayList<DiaChiNhanHang> list_dc= new ArrayList<>();
    private DatabaseReference data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi);
        data = FirebaseDatabase.getInstance().getReference();
        mapping();
        onBack();
        if (MainActivity.nguoiDung!=null){
            if (MainActivity.nguoiDung.getDanhsachdiachi()!=null){
                list_dc = MainActivity.nguoiDung.getDanhsachdiachi();
            }

        }
        diaChiAdapter = new DiaChiAdapter(this, list_dc);
        lv_diachi.setAdapter(diaChiAdapter);
        lv_diachi.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(DiaChiActivity.this, DiaChiCustomizeActivity.class);
            intent.putExtra("suadiachi", list_dc.get(position));
            startActivity(intent);
        });

        themDiaChiMoi();

    }

    private void themDiaChiMoi() {
        btn_themdiachi.setOnClickListener(v -> {
            if (MainActivity.nguoiDung!=null){
                startActivity(new Intent(this, DiaChiCustomizeActivity.class));
            }else{
                Toast.makeText(this, "Bạn cần đăng nhập để thực hiện chức năng này.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onBack() {
        ib_back.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("chuyentrang", 4);
            startActivity(intent);
            finish();
        });
    }

    private void mapping() {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        btn_themdiachi = (Button) findViewById(R.id.btn_plus);
        lv_diachi = (ListView) findViewById(R.id.lv_diachi);
    }
}