package com.example.quananvat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quananvat.DetailActivity;
import com.example.quananvat.GioHangActivity;
import com.example.quananvat.MainActivity;
import com.example.quananvat.R;
import com.example.quananvat.TimKiemActivity;
import com.example.quananvat.XemThemActivity;
import com.example.quananvat.adapter.ComboAdapter;
import com.example.quananvat.adapter.KhuyenMaiAdapter;
import com.example.quananvat.adapter.LoaiSanPhamAdapter;
import com.example.quananvat.adapter.SanPhamBanChayAdapter;
import com.example.quananvat.interfacecustom.ItemClickListener;
import com.example.quananvat.obj.Combo;
import com.example.quananvat.obj.LoaiSanPham;
import com.example.quananvat.obj.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {

    private RecyclerView lvspbc, rcv_loaisp;
    private ArrayList<SanPham> listspbc;
    private ListView lvspkm;
    private ArrayList<SanPham> listspkm;
    private boolean isLoading = false;
    private RecyclerView lvcombo;
    private ArrayList<Combo> listcombo;
    private EditText searchbar;
    private LinearLayout lnxemthemkm, lnthugonkm, lnxemthemspbc, lnsanphammoi;
    private RelativeLayout rl_soluong, rl_timkiem;
    private TextView tv_soluongtronggio;
    private KhuyenMaiAdapter khuyenMaiAdapter;
    private ImageButton ib_shopping_cart;
    private ArrayList<SanPham> listspkm_xemthem, listspkm_thugon, list_dexuat, list_spmoi;
    public static ArrayList<SanPham> list_full;
    private View footerView;
    private mHandler handler;
    private ArrayList<LoaiSanPham> arrloaisp;
    private DatabaseReference mdata;
    private LoaiSanPhamAdapter adapter;
    private SanPhamBanChayAdapter spbcAdapter;
    private ComboAdapter comboAdapter;
    private Random random = new Random();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mapping(view);
        mdata = FirebaseDatabase.getInstance().getReference();

        if (MainActivity.arrGioHang!=null && MainActivity.arrGioHang.size()!= 0){
            rl_soluong.setVisibility(View.VISIBLE);
            tv_soluongtronggio.setText(MainActivity.arrGioHang.size()+"");
        }else{
            rl_soluong.setVisibility(View.INVISIBLE);
        }

//      Loai san pham
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.HORIZONTAL, false);
        rcv_loaisp.setLayoutManager(gridLayoutManager);
        initArrayList();
        adapter = new LoaiSanPhamAdapter(view.getContext(), arrloaisp);
        rcv_loaisp.setAdapter(adapter);

//      San pham moi
        xemSanPhamMoi();

//      San pham ban chay
        lvspbc = (RecyclerView) view.findViewById(R.id.rcv_spbc);
        ititSanPhamBanChay();
        lvspbc.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        spbcAdapter = new SanPhamBanChayAdapter(view.getContext(), listspbc);
        lvspbc.setAdapter(spbcAdapter);
        spbcAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("sanpham", listspbc.get(position));
                bundle.putSerializable("mangdexuat", MainActivity.getDeXuat(list_full, listspbc.get(position)));
                intent.putExtra("dulieu", bundle);
                startActivity(intent);
            }

            @Override
            public void onLove(int position) {

            }
        });
        xemThemSanPhamBanChay();



//      San pham khuyen mai
        lvspkm = (ListView) view.findViewById(R.id.lv_khuyenmai);
        footerView = inflater.inflate(R.layout.footer_listview, null, false);
        handler = new mHandler();
        initSanPhamKhuyenMai();

        khuyenMaiAdapter = new KhuyenMaiAdapter(this.getContext(), listspkm_thugon);
        lvspkm.setAdapter(khuyenMaiAdapter);

        lnxemthemkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new ThreadData();
                thread.start();
                lnxemthemkm.setVisibility(View.INVISIBLE);
                lnthugonkm.setVisibility(View.VISIBLE);
            }
        });

        lnthugonkm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khuyenMaiAdapter.removeListItemAdapter(listspkm_xemthem);
                MainActivity.setListViewHeightBasedOnItems(lvspkm);
                lnxemthemkm.setVisibility(View.VISIBLE);
                lnthugonkm.setVisibility(View.INVISIBLE);
            }
        });
        lvspkm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("sanpham", listspkm.get(position));
                bundle.putSerializable("mangdexuat", MainActivity.getDeXuat(list_full, listspkm.get(position)));
                intent.putExtra("dulieu", bundle);
                startActivity(intent);
            }
        });

//        Combo
        lvcombo = (RecyclerView) view.findViewById(R.id.rcv_combo);
        initCombo();
        lvcombo.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        comboAdapter = new ComboAdapter(view.getContext(), listcombo);
        lvcombo.setAdapter(comboAdapter);
        comboAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("combo", listcombo.get(position));
                bundle.putSerializable("mangdexuat", MainActivity.getDeXuat(list_full, null));
                intent.putExtra("dulieu", bundle);
                startActivity(intent);
            }

            @Override
            public void onLove(int position) {

            }
        });

        ib_shopping_cart.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), GioHangActivity.class));
        });

        searchbar.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), TimKiemActivity.class));
        });

        return view;
    }

    private void xemSanPhamMoi() {
        list_spmoi = new ArrayList<>();
        mdata.child("san_pham").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_spmoi.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        SanPham sp = ds.getValue(SanPham.class);
                        if (sp.isSanphammoi()){
                            list_spmoi.add(sp);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        lnsanphammoi.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), XemThemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("list_spmoi", list_spmoi);
            intent.putExtra("data",bundle);
            startActivity(intent);
        });
    }

    private void xemThemSanPhamBanChay() {
        lnxemthemspbc.setOnClickListener(v -> {
//            Toast.makeText(getActivity(), "xemthem", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), XemThemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("list_spbc", listspbc);
            intent.putExtra("data",bundle);
            startActivity(intent);
        });
    }

    private void initCombo() {
        listcombo = new ArrayList<>();
        mdata.child("com_bo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listcombo.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    Combo cb = ds.getValue(Combo.class);
                    listcombo.add(cb);
                }
                comboAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void ititSanPhamBanChay() {
        listspbc = new ArrayList<SanPham>();
        list_full = new ArrayList<>();
        mdata.child("san_pham").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listspbc.clear();
                list_full.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    for (DataSnapshot ds: dataSnapshot.getChildren()){
                        SanPham sp = ds.getValue(SanPham.class);
                        list_full.add(sp);
                        if (sp.isSanphambanchay()){
                            listspbc.add(sp);
                        }
                    }
                }
                spbcAdapter.notifyDataSetChanged();

                if (listspbc.size()>5){
                    lnxemthemspbc.setVisibility(View.VISIBLE);
                } else {
                    lnxemthemspbc.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private ArrayList<SanPham> getDeXuat() {
        list_dexuat = new ArrayList<>();
        ArrayList<SanPham> listTam = new ArrayList<>();
        listTam.addAll(list_full);
        list_dexuat = randomChoice3(listTam, 3, null);
        return list_dexuat;
    }

    private ArrayList<SanPham> randomChoice3(ArrayList<SanPham> list, int number, SanPham sanPham){
        if (number==0){
            return list_dexuat;
        }
        if (sanPham!=null){
            list.remove(sanPham);
        }
        int r = random.nextInt(list.size()-1);
        list_dexuat.add(list.get(r));
        number--;
        return randomChoice3(list, number, list.get(r));
    }

    private void mapping(View v){
        rcv_loaisp = (RecyclerView) v.findViewById(R.id.rcv_loaisp);
        lnxemthemkm = (LinearLayout) v.findViewById(R.id.ln_xemthemkm);
        lnthugonkm = (LinearLayout) v.findViewById(R.id.ln_thugonkm);
        lnxemthemspbc = (LinearLayout) v.findViewById(R.id.ln_xemthemspbc);
        lnsanphammoi = (LinearLayout) v.findViewById(R.id.ln_sanphammoi);
        rl_soluong = (RelativeLayout) v.findViewById(R.id.rl_soluong);
        tv_soluongtronggio = (TextView) v.findViewById(R.id.tv_soluongtronggio);
        ib_shopping_cart = (ImageButton) v.findViewById(R.id.ib_shopping_cart);
        rl_timkiem = (RelativeLayout) v.findViewById(R.id.rl_timkiem);
        searchbar = (EditText) v.findViewById(R.id.searchbar);

    }

    private void initArrayList(){
        arrloaisp = new ArrayList<>();
        mdata.child("loai_san_pham").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrloaisp.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    LoaiSanPham lsp = ds.getValue(LoaiSanPham.class);
                    arrloaisp.add(lsp);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initSanPhamKhuyenMai() {
        listspkm = new ArrayList<>();
        listspkm_thugon = new ArrayList<>();
        listspkm_xemthem = new ArrayList<>();
        mdata.child("san_pham").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listspkm.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                   for (DataSnapshot ds: dataSnapshot.getChildren()){
                       SanPham sp = ds.getValue(SanPham.class);
                       if (sp.isSale()){
                           listspkm.add(sp);
                       }
                   }
                }
                if (listspkm.size()>3){
                    listspkm_thugon.clear();
                    for (int i=0; i<3; i++){
                        listspkm_thugon.add(listspkm.get(i));
                    }
                    lnxemthemkm.setVisibility(View.VISIBLE);
                    lnthugonkm.setVisibility(View.INVISIBLE);

                    listspkm_xemthem.clear();
                    for(int i=3; i<listspkm.size(); i++){
                        listspkm_xemthem.add(listspkm.get(i));
                    }
                }else{
                    listspkm_thugon.addAll(listspkm);
                    lnxemthemkm.setVisibility(View.INVISIBLE);
                    lnthugonkm.setVisibility(View.INVISIBLE);
                }
                khuyenMaiAdapter.notifyDataSetChanged();
                MainActivity.setListViewHeightBasedOnItems(lvspkm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }


    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvspkm.addFooterView(footerView);
                    MainActivity.setListViewHeightBasedOnItems(lvspkm);
                    break;
                case 1:
                    lvspkm.removeFooterView(footerView);
                    khuyenMaiAdapter.addListItemAdapter((ArrayList<SanPham>) msg.obj);
                    MainActivity.setListViewHeightBasedOnItems(lvspkm);
                    break;
            }
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = handler.obtainMessage(1, listspkm_xemthem);
            handler.sendMessage(message);
        }
    }



}
