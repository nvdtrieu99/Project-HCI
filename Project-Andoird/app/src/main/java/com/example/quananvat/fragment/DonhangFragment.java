package com.example.quananvat.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.quananvat.R;
import com.example.quananvat.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class DonhangFragment extends Fragment {
    private View view;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_donhang, container, false);
        init(view);
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    public void init(View v){
        viewPager = (ViewPager) v.findViewById(R.id.vp_donhang);
        tabLayout = (TabLayout) v.findViewById(R.id.tl_donhang);
    }
}
