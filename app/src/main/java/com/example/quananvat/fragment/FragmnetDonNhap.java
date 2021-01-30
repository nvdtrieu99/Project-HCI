package com.example.quananvat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quananvat.MainActivity;
import com.example.quananvat.R;
import com.example.quananvat.XacNhanDonHang;
import com.example.quananvat.adapter.GioHangAdapter;
import com.example.quananvat.obj.GioHang;

import java.util.ArrayList;

public class FragmnetDonNhap extends Fragment {
    private View view;
    private TextView tv_xoahet, tv_tongcong;
    private CheckBox cb_tatca;
    private AppCompatButton btn_thanhtoan;
    private RelativeLayout rl_footer, rl_xoahet;
    private RecyclerView rcv_giohang;
    private ArrayList<GioHang> arrGioHang;
    private GioHangAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_donnhap, container, false);
        mapping(view);
        initGioHang();
        if (arrGioHang.size()==0){
            rl_footer.setVisibility(View.INVISIBLE);
            rl_xoahet.setVisibility(View.INVISIBLE);
        }

        rcv_giohang.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new GioHangAdapter(view.getContext(), arrGioHang);
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
                    Intent intent = new Intent(getActivity(), XacNhanDonHang.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("listgiohang", arrGioHang);
                    intent.putExtra("dulieu", bundle);
                    startActivity(intent);
                }

            });
        }else{
            btn_thanhtoan.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Bạn cần đăng nhập để thực hiện chức năng này!", Toast.LENGTH_SHORT).show();
            });
        }
        return view;
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
    public void mapping(View v){
        tv_xoahet = (TextView) v.findViewById(R.id.tv_xoahet);
        tv_tongcong = (TextView) v.findViewById(R.id.tv_tongcong);
        cb_tatca = (CheckBox) v.findViewById(R.id.cb_tatca);
        btn_thanhtoan = (AppCompatButton) v.findViewById(R.id.btn_thanhtoan);
        rcv_giohang = (RecyclerView) v.findViewById(R.id.rcv_giohang);
        rl_footer = (RelativeLayout) v.findViewById(R.id.rl_footer);
        rl_xoahet = (RelativeLayout) v.findViewById(R.id.rl_xoahet);
    }

    public void initGioHang(){
        arrGioHang = MainActivity.arrGioHang;
//        arrGioHang = new ArrayList<>();
//        arrGioHang.add(new GioHang(new SanPham("Bia úp ngược vị chanh",45000,39000,true, 4.5,R.drawable.biaupvichanh), 1));
//        arrGioHang.add(new GioHang(new SanPham("Trà sữa thái xanh",35000,29000,true, 4.5,R.drawable.thaixanh), 1));
    }
}

