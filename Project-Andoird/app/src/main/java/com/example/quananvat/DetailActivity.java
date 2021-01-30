package com.example.quananvat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.viewpager.widget.ViewPager;

import com.example.quananvat.adapter.ViewPagerDXBLAdapter;
import com.example.quananvat.obj.Combo;
import com.example.quananvat.obj.GioHang;
import com.example.quananvat.obj.SanPham;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ImageButton ib_back, ib_yeuthich, ib_yeuthich_active, ib_more, ib_shopping_cart;
    private RelativeLayout rl_more, rl_cart, rl_share, rl_yeuthich, rl_soluong;
    private TabLayout tl_dxbl;
    private ViewPager vp_dxbl;
    private ImageView iv_hinh;
    private TextView tv_tensanpham, tv_danhgia, tv_gia, tv_giatruocsale, tv_giasausale, tv_soluong, tv_tonggiatien, tv_soluongdanhgia, tv_soluongtronggio;
    private LinearLayout ln_gia, ln_khuyenmai;
    private Button btn_plus, btn_minus, btn_muangay, btn_themgiohang;
    private int soluong;
    private ArrayList<SanPham> arr_dexuat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("dulieu");
        SanPham sanPham = (SanPham) bundle.getSerializable("sanpham");
        Combo combo = (Combo) bundle.getSerializable("combo");
        arr_dexuat = (ArrayList<SanPham>) bundle.getSerializable("mangdexuat");

        mapping();
        comeback();
        soluong = Integer.parseInt(tv_soluong.getText().toString());
        if (MainActivity.arrGioHang.size()!=0){
            rl_soluong.setVisibility(View.VISIBLE);
            tv_soluongtronggio.setText(MainActivity.arrGioHang.size()+"");
        }
        if (sanPham!=null){
            setThongTin(sanPham);
            themSoLuong(sanPham);
            giamSoLuong(sanPham);
            ib_shopping_cart.setOnClickListener(v -> {
                startActivity(new Intent(this, GioHangActivity.class));
            });
            btn_muangay.setOnClickListener(v -> {
                if (MainActivity.nguoiDung!=null){
                    ArrayList<GioHang> list = new ArrayList<>();
                    list.add(new GioHang(sanPham, Integer.parseInt(tv_soluong.getText().toString())));
                    Intent intent1 = new Intent(DetailActivity.this, XacNhanDonHang.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("listgiohang", list);
                    intent1.putExtra("dulieu", bundle1);
                    startActivity(intent1);
                }else{
                    Toast.makeText(this, "Bạn cần đăng nhập để thực hiện chức năng này.", Toast.LENGTH_SHORT).show();
                }
            });

            btn_themgiohang.setOnClickListener(v -> {
                if (MainActivity.arrGioHang.size()==0){
                    MainActivity.arrGioHang.add(new GioHang(sanPham, Integer.parseInt(tv_soluong.getText().toString())));
                }else{
                    int dem = 0;
                    for (GioHang gh: MainActivity.arrGioHang){
                        if (sanPham.getId().equals(gh.getSanPham().getId())){
                            int slcu = gh.getSoLuong();
                            int slmoi = Integer.parseInt(tv_soluong.getText().toString());
                            gh.setSoLuong(slcu+slmoi);
                        }else{
                            dem++;
                            continue;
                        }
                    }
                    if (dem==MainActivity.arrGioHang.size()){
                        MainActivity.arrGioHang.add(new GioHang(sanPham, Integer.parseInt(tv_soluong.getText().toString())));
                    }
//
                }


                rl_soluong.setVisibility(View.VISIBLE);
                tv_soluongtronggio.setText(MainActivity.arrGioHang.size()+"");
            });
        }

        if (combo!=null){
            setThongTinCombo(combo);
            themSoLuongCombo(combo);
            giamSoLuongCombo(combo);

            btn_muangay.setOnClickListener(v -> {
                ArrayList<GioHang> list = new ArrayList<>();
                list.add(new GioHang(combo, Integer.parseInt(tv_soluong.getText().toString())));
                Intent intent1 = new Intent(DetailActivity.this, XacNhanDonHang.class);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("listgiohang", list);
                intent1.putExtra("dulieu", bundle1);
                startActivity(intent1);
            });
        }
        vp_dxbl.setAdapter(new ViewPagerDXBLAdapter(getSupportFragmentManager()));
        tl_dxbl.setupWithViewPager(vp_dxbl);
        hienThiMenuMore();
        yeuThichSanPham(sanPham);

    }

    private void giamSoLuongCombo(Combo combo) {
        btn_minus.setOnClickListener(v -> {
            if (soluong>1){
                soluong--;
                tv_soluong.setText(soluong+"");
                tv_tonggiatien.setText(soluong*combo.getGia()+"VND");
            }
        });
    }

    private void themSoLuongCombo(Combo combo) {
        btn_plus.setOnClickListener(v -> {
            soluong++;
            tv_soluong.setText(soluong+"");
            tv_tonggiatien.setText(soluong*combo.getGia()+"VND");
        });
    }

    private void setThongTinCombo(Combo combo) {
        Picasso.get().load(combo.getHinhanh()).into(iv_hinh);
        tv_tensanpham.setText(combo.getCombo()+": "+combo.getMota());
        tv_danhgia.setText(combo.getDanhgia()+"");
        ln_gia.setVisibility(View.VISIBLE);
        ln_khuyenmai.setVisibility(View.INVISIBLE);
        tv_gia.setText(combo.getGia()+"VND");
        tv_tonggiatien.setText(soluong*combo.getGia()+"VND");

    }

    public  ArrayList<SanPham> getDeXuat(){
        return this.arr_dexuat;
    }

    private void yeuThichSanPham(SanPham sanPham) {

    }

    private void hienThiMenuMore() {
        ib_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuMore(v);
            }
        });
    }

    private void showMenuMore(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.inflate(R.menu.menu_more);
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_home:
                        startActivity(new Intent(DetailActivity.this, MainActivity.class));
                        break;
                }
                return false;
            }
        });

    }

    private void giamSoLuong(SanPham sanPham) {
        btn_minus.setOnClickListener(v -> {
            if (soluong>1){
                soluong--;
                tv_soluong.setText(soluong+"");
                if (sanPham.isSale()){
                    tv_tonggiatien.setText(soluong*sanPham.getGiakhisale()+"VND");
                }else{
                    tv_tonggiatien.setText(soluong*sanPham.getGiasanpham()+"VND");
                }
            }
        });
    }

    private void themSoLuong(SanPham sanPham) {
        btn_plus.setOnClickListener(v -> {
            soluong++;
            tv_soluong.setText(soluong+"");
            if (sanPham.isSale()){
                tv_tonggiatien.setText(soluong*sanPham.getGiakhisale()+"VND");
            }else{
                tv_tonggiatien.setText(soluong*sanPham.getGiasanpham()+"VND");
            }
        });
    }

    private void setThongTin(SanPham sanPham) {
//        iv_hinh.setImageResource(sanPham.getHinh());
        Picasso.get().load(sanPham.getHinhanh()).into(iv_hinh);
        tv_tensanpham.setText(sanPham.getTensanpham());
        tv_danhgia.setText(sanPham.getDanhgia()+"");

        if (sanPham.isSale()){
            ln_gia.setVisibility(View.INVISIBLE);
            ln_khuyenmai.setVisibility(View.VISIBLE);
            tv_giatruocsale.setText(sanPham.getGiasanpham()+"VND");
            tv_giasausale.setText(sanPham.getGiakhisale()+"VND");
            tv_tonggiatien.setText(soluong*sanPham.getGiakhisale()+"VND");
        }else{
            ln_gia.setVisibility(View.VISIBLE);
            ln_khuyenmai.setVisibility(View.INVISIBLE);
            tv_gia.setText(sanPham.getGiasanpham()+"VND");
            tv_tonggiatien.setText(soluong*sanPham.getGiasanpham()+"VND");
        }

    }

    private void comeback() {
        ib_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    public void mapping(){
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        rl_more = (RelativeLayout) findViewById(R.id.rl_more);
        rl_cart = (RelativeLayout) findViewById(R.id.rl_cart);
        rl_share = (RelativeLayout) findViewById(R.id.rl_share);
        rl_yeuthich = (RelativeLayout) findViewById(R.id.rl_yeuthich);
        ib_yeuthich = (ImageButton) findViewById(R.id.ib_yeuthich);
        ib_yeuthich_active = (ImageButton) findViewById(R.id.ib_yeuthich_active);
        tl_dxbl = (TabLayout) findViewById(R.id.tl_dxbl);
        vp_dxbl = (ViewPager) findViewById(R.id.vp_dxbl);
        iv_hinh = (ImageView) findViewById(R.id.iv_hinh);
        tv_tensanpham = (TextView) findViewById(R.id.tv_tensanpham);
        tv_danhgia = (TextView) findViewById(R.id.tv_danhgia);
        tv_giatruocsale = (TextView) findViewById(R.id.tv_giatruocsale);
        tv_giasausale = (TextView) findViewById(R.id.tv_giasausale);
        tv_gia = (TextView) findViewById(R.id.tv_gia);
        tv_tonggiatien = (TextView) findViewById(R.id.tv_tonggiatien);
        tv_soluong = (TextView) findViewById(R.id.tv_soluong);
        tv_soluongdanhgia = (TextView) findViewById(R.id.tv_soluongdanhgia);
        ln_gia = (LinearLayout) findViewById(R.id.ln_gia);
        ln_khuyenmai = (LinearLayout) findViewById(R.id.ln_khuyenmai);
        btn_minus = (Button) findViewById(R.id.btn_minus);
        btn_plus = (Button) findViewById(R.id.btn_plus);
        btn_muangay = (Button) findViewById(R.id.btn_muangay);
        btn_themgiohang = (Button) findViewById(R.id.btn_themgiohang);
        ib_more = (ImageButton) findViewById(R.id.ib_more);
        rl_soluong = (RelativeLayout) findViewById(R.id.rl_soluong);
        tv_soluongtronggio = (TextView) findViewById(R.id.tv_soluongtronggio);
        ib_shopping_cart = (ImageButton) findViewById(R.id.ib_shopping_cart);
    }
}