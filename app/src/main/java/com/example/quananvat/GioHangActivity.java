package com.example.quananvat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quananvat.adapter.GioHangAdapter;
import com.example.quananvat.obj.GioHang;

import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {

    private TextView tv_xoahet, tv_tongcong;
    private CheckBox cb_tatca;
    private Button btn_thanhtoan;
    private RelativeLayout rl_footer;
    private RecyclerView rcv_giohang;
    private RelativeLayout rl_xoahet;
    private ArrayList<GioHang> arrGioHang;
    private ImageButton ib_back;
    private GioHangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        mapping();
        ib_back.setOnClickListener(v -> {
            onBackPressed();
        });
        initGioHang();
        if (arrGioHang.size()==0){
            rl_footer.setVisibility(View.INVISIBLE);
            rl_xoahet.setVisibility(View.INVISIBLE);
        }

        rcv_giohang.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new GioHangAdapter(getApplicationContext(), arrGioHang);
        rcv_giohang.setAdapter(adapter);
        adapter.setOnItemClickListener(new GioHangAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                xoaSanPham(position);
            }

            @Override
            public void onPlus(int position) {
                themSoLuong(position);
            }

            @Override
            public void onMinus(int position) {
                giamSoLuong(position);
            }


        });

        checkAll();
        xoaHet();

        if (MainActivity.nguoiDung!=null){
            btn_thanhtoan.setOnClickListener(v -> {
                if (cb_tatca.isChecked()){
                    Intent intent = new Intent(GioHangActivity.this, XacNhanDonHang.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("listgiohang", arrGioHang);
                    intent.putExtra("dulieu", bundle);
                    startActivity(intent);
                }

            });
        }else{
            btn_thanhtoan.setOnClickListener(v -> {
                Toast.makeText(this, "Bạn cần đăng nhập để thực hiện chức năng này!", Toast.LENGTH_SHORT).show();
            });
        }

    }

    public void initGioHang() {
        arrGioHang = MainActivity.arrGioHang;
    }
    private void xoaHet() {
        tv_xoahet.setOnClickListener(v -> {
            arrGioHang.clear();
            MainActivity.arrGioHang.clear();
            adapter.notifyDataSetChanged();
            rl_footer.setVisibility(View.INVISIBLE);
            rl_xoahet.setVisibility(View.INVISIBLE);
        });
    }

    private void checkAll(){
        cb_tatca.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    adapter.selectAll();
                    double tongcong = 0.0;
                    for (GioHang gh: arrGioHang) {
                        if (gh.getSanPham().isSale()){
                            tongcong += gh.getSanPham().getGiakhisale()*gh.getSoLuong();
                        }else{
                            tongcong += gh.getSanPham().getGiasanpham()*gh.getSoLuong();
                        }
                    }
                    tv_tongcong.setText(tongcong+"VND");
                }else{
                    adapter.unselectall();
                    tv_tongcong.setText(0.0+"VND");
                }
            }
        });
    }

    public void xoaSanPham(int position){
        arrGioHang.remove(position);
        adapter.notifyItemRemoved(position);
        if (cb_tatca.isChecked()){
            if (arrGioHang.size()>0){
                double tongcong = 0.0;
                for (GioHang gh: arrGioHang) {
                    if (gh.getSanPham().isSale()){
                        tongcong += gh.getSanPham().getGiakhisale()*gh.getSoLuong();
                    }else{
                        tongcong += gh.getSanPham().getGiasanpham()*gh.getSoLuong();
                    }
                }
                tv_tongcong.setText(tongcong+"VND");
            }else{
                tv_tongcong.setText(0.0+"VND");
                cb_tatca.setChecked(false);

            }
        }
    }

    public void giamSoLuong(int position){
        int soluong = arrGioHang.get(position).getSoLuong();
        soluong--;
        if (soluong>=1){
            arrGioHang.get(position).setSoLuong(soluong);
            adapter.notifyItemChanged(position);
            if (cb_tatca.isChecked()){
                CheckBox cb = (CheckBox)rcv_giohang.getChildAt(position).findViewById(R.id.cb_tick);
                cb.setChecked(true);
                double tongcong = 0.0;
                for (GioHang gh: arrGioHang) {
                    if (gh.getSanPham().isSale()){
                        tongcong += gh.getSanPham().getGiakhisale()*gh.getSoLuong();
                    }else{
                        tongcong += gh.getSanPham().getGiasanpham()*gh.getSoLuong();
                    }
                }
                tv_tongcong.setText(tongcong+"VND");
            }
        }

    }
    public void themSoLuong(int position){
        int soluong = arrGioHang.get(position).getSoLuong();
        soluong++;
        arrGioHang.get(position).setSoLuong(soluong);
        adapter.notifyItemChanged(position);
        if (cb_tatca.isChecked()){
            CheckBox cb = (CheckBox)rcv_giohang.getChildAt(position).findViewById(R.id.cb_tick);
            cb.setChecked(true);
            double tongcong = 0.0;
            for (GioHang gh: arrGioHang) {
                if (gh.getSanPham().isSale()){
                    tongcong += gh.getSanPham().getGiakhisale()*gh.getSoLuong();
                }else{
                    tongcong += gh.getSanPham().getGiasanpham()*gh.getSoLuong();
                }
            }
            tv_tongcong.setText(tongcong+"VND");
        }
    }
    public void mapping(){
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        tv_xoahet = (TextView) findViewById(R.id.tv_xoahet);
        tv_tongcong = (TextView) findViewById(R.id.tv_tongcong);
        cb_tatca = (CheckBox) findViewById(R.id.cb_tatca);
        btn_thanhtoan = (Button) findViewById(R.id.btn_thanhtoan);
        rcv_giohang = (RecyclerView) findViewById(R.id.rcv_giohang);
        rl_footer = (RelativeLayout) findViewById(R.id.rl_footer);
        rl_xoahet = (RelativeLayout) findViewById(R.id.rl_xoahet);
    }
}