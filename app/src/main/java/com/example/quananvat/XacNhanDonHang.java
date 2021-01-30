package com.example.quananvat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quananvat.adapter.XacNhanDonHangAdapter;
import com.example.quananvat.obj.ChiTietDonHang;
import com.example.quananvat.obj.DiaChiNhanHang;
import com.example.quananvat.obj.DonHang;
import com.example.quananvat.obj.GioHang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class XacNhanDonHang extends AppCompatActivity {

    private ImageButton ib_back;
    private LinearLayout ln_airpay, ln_tienmat;
    private ListView listView;
    private TextView tv_tong, tv_phigiaohang, tv_phidichvu, tv_tongcong, tv_tenkh, tv_sdt, tv_diachi;
    private TextView tv_suadiachi, tv_thietlapdiachi;
    private RelativeLayout rl_diachi;
    private Button btn_muangay;
    private ArrayList<GioHang> listgiohang;
    private int phuongthucthanhtoan = 1;
    private DatabaseReference data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_don_hang);
        data = FirebaseDatabase.getInstance().getReference();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dulieu");
        listgiohang = (ArrayList<GioHang>) bundle.getSerializable("listgiohang");

        mapping();
        onBack();
        phuongThucThanhToan();
        setThongTinDonHang();
        XacNhanDonHangAdapter adapter = new XacNhanDonHangAdapter(this, listgiohang);
        listView.setAdapter(adapter);
        MainActivity.setListViewHeightBasedOnItems(listView);

        tinhTongTien();
        muaNgay();
    }

    private void muaNgay() {
        btn_muangay.setOnClickListener(v -> {
            String madonhang = data.child("don_hang").push().getKey();
            DonHang dh = new DonHang();
            dh.setIdnguoidathang(MainActivity.nguoiDung.getUID());
            dh.setTennguoinhan(tv_tenkh.getText().toString());
            dh.setSdtnguoinhan(tv_sdt.getText().toString());
            dh.setDiachinguoinhan(tv_diachi.getText().toString());
            Date date = new Date();
            dh.setThoigiandathang(MainActivity.formatter.format(date));
            dh.setTongtien(Double.parseDouble(tv_tongcong.getText().toString().replace("VND","")));
            if (phuongthucthanhtoan==1){
                dh.setTrangthaithanhtoan(false);
            }
            if (phuongthucthanhtoan==0){
                dh.setTrangthaithanhtoan(true);
            }
            dh.setTrangthaigiaohang(false);
            dh.setLoaithanhtoan(phuongthucthanhtoan);
            dh.setMadonhang(madonhang);
            ArrayList<ChiTietDonHang> ctdh = new ArrayList<>();
            for (GioHang gh: listgiohang){
                ChiTietDonHang ct = new ChiTietDonHang();
                if (gh.getSanPham()!=null){
                    ct.setIdsanpham(gh.getSanPham().getId());
                }
                if (gh.getCombo()!=null){
                    ct.setIdcombo(gh.getCombo().getId());
                }
                ct.setSoluong(gh.getSoLuong());
                ctdh.add(ct);
            }
            dh.setChitietdonhang(ctdh);
            data.child("don_hang").child(madonhang).setValue(dh);

            data.child("nguoi_dung").child(MainActivity.nguoiDung.getUID())
                    .child("dsdonhang").child(madonhang)
                    .setValue(dh);
            MainActivity.arrGioHang.clear();
            Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void setThongTinDonHang() {
        DiaChiNhanHang diaChiNhanHang = null;
        for (DiaChiNhanHang dc: MainActivity.nguoiDung.getDanhsachdiachi()){
            if (dc.getDiachimacdinh()){
                diaChiNhanHang = dc;
                break;
            }
        }
        if (diaChiNhanHang!=null){
            btn_muangay.setEnabled(true);
            tv_tenkh.setText(diaChiNhanHang.getTennguoinhan());
            tv_sdt.setText(diaChiNhanHang.getSdtnguoinhan());
            tv_diachi.setText(diaChiNhanHang.getDiachicuthe()+","+diaChiNhanHang.getPhuong_xa()+","+diaChiNhanHang.getQuan_huyen()
            +","+diaChiNhanHang.getTinh_thanhpho());
        }else{
            btn_muangay.setEnabled(false);
            rl_diachi.setVisibility(View.INVISIBLE);
            tv_suadiachi.setVisibility(View.INVISIBLE);
            tv_thietlapdiachi.setVisibility(View.VISIBLE);
        }

    }

    private void tinhTongTien(){
        double tong = 0.0;
        double phigiaohang = 10000.0;
        double phidichvu = 2000.0;
        double tongcong = 0.0;

        for (GioHang gh: listgiohang) {
            if (gh.getSanPham()!=null){
                if (gh.getSanPham().isSale()){
                    tong = tong + gh.getSanPham().getGiakhisale()*gh.getSoLuong();
                }else{
                    tong = tong + gh.getSanPham().getGiasanpham()*gh.getSoLuong();
                }
            }else {
                tong = tong + gh.getCombo().getGia()*gh.getSoLuong();
            }
        }

        tongcong = tong+phigiaohang+phidichvu;
        tv_tong.setText(tong+"VND");
        tv_phigiaohang.setText(phigiaohang+"VND");
        tv_phidichvu.setText(phidichvu+"VND");
        tv_tongcong.setText(tongcong+"VND");
    }

    private void onBack(){
        ib_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void phuongThucThanhToan(){
        ln_airpay.setOnClickListener(v -> {
            ln_airpay.setBackground(getDrawable(R.drawable.bg_ltt_active));
            ln_tienmat.setBackground(getDrawable(R.drawable.bg_ltt));
            phuongthucthanhtoan = 0;
        });

        ln_tienmat.setOnClickListener(v -> {
            ln_airpay.setBackground(getDrawable(R.drawable.bg_ltt));
            ln_tienmat.setBackground(getDrawable(R.drawable.bg_ltt_active));
            phuongthucthanhtoan = 1;
        });
    }

    private void mapping(){
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ln_airpay = (LinearLayout) findViewById(R.id.ln_airpay);
        ln_tienmat = (LinearLayout) findViewById(R.id.ln_tienmat);
        listView = (ListView) findViewById(R.id.lv_xndh);
        tv_tong = (TextView) findViewById(R.id.tv_tong);
        tv_phidichvu = (TextView) findViewById(R.id.tv_phidichvu);
        tv_phigiaohang = (TextView) findViewById(R.id.tv_phigiaohang);
        tv_tongcong = (TextView) findViewById(R.id.tv_tongcong);
        tv_tenkh = (TextView) findViewById(R.id.tv_tenkh);
        tv_sdt = (TextView) findViewById(R.id.tv_sdtkh);
        tv_diachi = (TextView) findViewById(R.id.tv_diachi);
        tv_suadiachi = (TextView) findViewById(R.id.tv_suadiachi);
        tv_thietlapdiachi = (TextView) findViewById(R.id.tv_thietlapdiachi);
        rl_diachi = (RelativeLayout) findViewById(R.id.rl_2);
        btn_muangay = (Button) findViewById(R.id.btn_muangay);
    }
}