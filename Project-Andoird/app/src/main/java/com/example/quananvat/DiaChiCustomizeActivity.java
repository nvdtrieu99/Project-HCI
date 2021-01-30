package com.example.quananvat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quananvat.obj.DiaChiNhanHang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DiaChiCustomizeActivity extends AppCompatActivity {

    private ImageButton ib_back;
    private TextView tv_editdiachi;
    private EditText et_hoten, et_sdt, et_phuongxa, et_quanhuyen, et_tinhthanhpho, et_diachicuthe;
    private Switch switch_dcmd;
    private Button btn_chon;
    private RelativeLayout rl_xoadiachi;
    private DatabaseReference data;
    private DiaChiNhanHang diaChiNhanHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi_customize);
        data = FirebaseDatabase.getInstance().getReference();
        mapping();
        onBack();
        Intent intent = getIntent();
        diaChiNhanHang = (DiaChiNhanHang) intent.getSerializableExtra("suadiachi");
        if (diaChiNhanHang!=null){
            tv_editdiachi.setText("Sửa địa chỉ");
            rl_xoadiachi.setVisibility(View.VISIBLE);
            et_hoten.setText(diaChiNhanHang.getTennguoinhan());
            et_sdt.setText(diaChiNhanHang.getSdtnguoinhan());
            et_tinhthanhpho.setText(diaChiNhanHang.getTinh_thanhpho());
            et_quanhuyen.setText(diaChiNhanHang.getQuan_huyen());
            et_phuongxa.setText(diaChiNhanHang.getPhuong_xa());
            et_diachicuthe.setText(diaChiNhanHang.getDiachicuthe());
            if (diaChiNhanHang.getDiachimacdinh()){
                switch_dcmd.setChecked(true);
            }else{
                switch_dcmd.setChecked(false);
            }
            xoaDiaChi(diaChiNhanHang);
        }else{
            tv_editdiachi.setText("Địa chỉ mới");
        }

        chon();

    }



    private void xoaDiaChi(DiaChiNhanHang dc) {
        rl_xoadiachi.setOnClickListener(v -> {
            data.child("nguoi_dung")
                    .child(MainActivity.nguoiDung.getUID())
                    .child("dsdiachinhanhang")
                    .child(dc.getKey())
                    .removeValue();
            startActivity(new Intent(this, DiaChiActivity.class));
        });
    }

    private void chon() {
        btn_chon.setOnClickListener(v -> {
            if (MainActivity.nguoiDung!=null){
                DiaChiNhanHang dcnh = new DiaChiNhanHang();
                dcnh.setTennguoinhan(et_hoten.getText().toString().trim());
                dcnh.setSdtnguoinhan(et_sdt.getText().toString().trim());
                dcnh.setDiachicuthe(et_diachicuthe.getText().toString().trim());
                dcnh.setPhuong_xa(et_phuongxa.getText().toString().trim());
                dcnh.setQuan_huyen(et_quanhuyen.getText().toString().trim());
                dcnh.setTinh_thanhpho(et_tinhthanhpho.getText().toString().trim());
                if (switch_dcmd.isChecked()){
                    dcnh.setDiachimacdinh(true);
                    ArrayList<DiaChiNhanHang> list = MainActivity.nguoiDung.getDanhsachdiachi();
                    if (list!=null && list.size()!=0){
                        for (DiaChiNhanHang dc: list){
                            data.child("nguoi_dung")
                                    .child(MainActivity.nguoiDung.getUID())
                                    .child("dsdiachinhanhang")
                                    .child(dc.getKey())
                                    .child("diachimacdinh")
                                    .setValue(false);
                        }
                    }
                }else{
                    dcnh.setDiachimacdinh(false);
                }
                if (diaChiNhanHang!=null){
                    dcnh.setKey(diaChiNhanHang.getKey());
                }else {
                    String key = data.child("nguoi_dung")
                            .child(MainActivity.nguoiDung.getUID())
                            .child("dsdiachinhanhang")
                            .push().getKey();
                    dcnh.setKey(key);
                }

                data.child("nguoi_dung")
                        .child(MainActivity.nguoiDung.getUID())
                        .child("dsdiachinhanhang")
                        .child(dcnh.getKey())
                        .setValue(dcnh);
                startActivity(new Intent(this, DiaChiActivity.class));
                finish();
            }else{
                Toast.makeText(this, "Bạn cần đăng nhập để thực hiện chức năng này.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onBack() {
        ib_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void mapping() {
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        tv_editdiachi = (TextView) findViewById(R.id.tv_editdiachi);
        et_hoten = (EditText) findViewById(R.id.et_hoten);
        et_sdt = (EditText) findViewById(R.id.et_sdt);
        et_phuongxa = (EditText) findViewById(R.id.et_phuongxa);
        et_quanhuyen = (EditText) findViewById(R.id.et_quanhuyen);
        et_tinhthanhpho = (EditText) findViewById(R.id.et_tinhthanhpho);
        et_diachicuthe = (EditText) findViewById(R.id.et_diachicuthe);
        switch_dcmd = (Switch) findViewById(R.id.switch_dcmd);
        btn_chon = (Button) findViewById(R.id.btn_chon);
        rl_xoadiachi = (RelativeLayout) findViewById(R.id.rl_xoadiachi);
    }
}