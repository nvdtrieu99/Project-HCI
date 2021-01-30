package com.example.quananvat;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.quananvat.fragment.DonhangFragment;
import com.example.quananvat.fragment.HomeFragment;
import com.example.quananvat.fragment.ToiFragment;
import com.example.quananvat.fragment.YeuthichFragment;
import com.example.quananvat.obj.DiaChiNhanHang;
import com.example.quananvat.obj.DonHang;
import com.example.quananvat.obj.GioHang;
import com.example.quananvat.obj.NguoiDung;
import com.example.quananvat.obj.SanPham;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<GioHang> arrGioHang = new ArrayList<>();
    public static NguoiDung nguoiDung;
    public static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        int chuyentrang = -1;
        if (bundle!=null){
            chuyentrang = bundle.getInt("chuyentrang");
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                getNguoiDungHienTai();
            }
        });
        t.start();

        BottomNavigationView botnav =(BottomNavigationView) findViewById(R.id.bottom_nav);
        botnav.setOnNavigationItemSelectedListener(navListener);

        if (chuyentrang!=-1){
            if (chuyentrang==4){
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ToiFragment toiFragment = new ToiFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, toiFragment).commit();
//                botnav.getMenu().findItem(R.id.nav_home).setChecked(false);
//                botnav.getMenu().findItem(R.id.nav_donhang).setChecked(false);
//                botnav.getMenu().findItem(R.id.nav_yeuthich).setChecked(false);
//                botnav.getMenu().findItem(R.id.nav_user).setChecked(true);
                botnav.findViewById(R.id.nav_user).performClick();
            }
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }


    }


    private void getNguoiDungHienTai() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            DatabaseReference data = FirebaseDatabase.getInstance().getReference();
            data.child("nguoi_dung").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    nguoiDung = snapshot.getValue(NguoiDung.class);

                    ArrayList<DiaChiNhanHang> ds_dcnh = new ArrayList<>();
                    for (DataSnapshot ds: snapshot.child("dsdiachinhanhang").getChildren()){
                        DiaChiNhanHang dcnh = ds.getValue(DiaChiNhanHang.class);
                        ds_dcnh.add(dcnh);
                    }
                    ArrayList<DonHang> ds_donhang = new ArrayList<>();
                    for (DataSnapshot ds: snapshot.child("dsdonhang").getChildren()){
                        DonHang dh = ds.getValue(DonHang.class);
                        if (dh!=null){
                            ds_donhang.add(dh);
                        }
                    }
                    nguoiDung.setDanhsachdonhang(ds_donhang);
                    nguoiDung.setDanhsachdiachi(ds_dcnh);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectFragment = new HomeFragment();
                            break;
                        case R.id.nav_donhang:
                            selectFragment = new DonhangFragment();
                            break;
                        case R.id.nav_yeuthich:
                            selectFragment = new YeuthichFragment();
                            break;
                        case R.id.nav_user:
                            selectFragment = new ToiFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectFragment).commit();
                    return true;
                }
            };

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }



    public static ArrayList<SanPham> getDeXuat(ArrayList<SanPham> listfull, SanPham sp) {
        ArrayList<SanPham> list_dexuat = new ArrayList<>();
        ArrayList<SanPham> listTam = new ArrayList<>();
        listTam.addAll(listfull);
        if (sp!=null){
            listTam.remove(sp);
        }
        int number = 3;
        Random random = new Random();
        while (number!=0){
            int r = random.nextInt(listTam.size()-1);
            list_dexuat.add(listTam.get(r));
            listTam.remove(listTam.get(r));
            number--;
        }
        return list_dexuat;
    }

    public static String chuanHoaChuoi(String s){
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

}