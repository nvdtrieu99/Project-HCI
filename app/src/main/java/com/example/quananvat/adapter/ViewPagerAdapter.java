package com.example.quananvat.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.quananvat.fragment.FragmentDangDen;
import com.example.quananvat.fragment.FragmnetDonNhap;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentDangDen fragmentDangDen;
    private FragmnetDonNhap fragmnetDonNhap;
    private String listTab[] = {"Đang đến","Giỏ hàng"};
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragmentDangDen = new FragmentDangDen();
        fragmnetDonNhap = new FragmnetDonNhap();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return fragmentDangDen;
            case 1:
                return fragmnetDonNhap;
        }
        return null;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
