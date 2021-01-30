package com.example.quananvat.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.quananvat.fragment.FragmentBinhLuan;
import com.example.quananvat.fragment.FragmentDeXuat;

public class ViewPagerDXBLAdapter extends FragmentStatePagerAdapter {

    private FragmentDeXuat deXuat;
    private FragmentBinhLuan binhLuan;
    private String[] tabName = {"Đề xuất cho bạn", "Bình luận"};

    public ViewPagerDXBLAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        deXuat = new FragmentDeXuat();
        binhLuan = new FragmentBinhLuan();
        switch (position){
            case 0:
                return deXuat;
            case 1:
                return binhLuan;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabName.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabName[position];
    }
}
