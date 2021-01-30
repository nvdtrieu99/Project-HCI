package com.example.quananvat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.quananvat.DiaChiActivity;
import com.example.quananvat.LoginActivity;
import com.example.quananvat.MainActivity;
import com.example.quananvat.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ToiFragment extends Fragment {
    private Button btn_dndk, btn_dangxuat;
    private CircleImageView iv_logotoi;
    private RelativeLayout rl_thongtin;
    private TextView tv_tenuser;
    private RelativeLayout rl_diachi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toi, container, false);
        mapping(view);

        if (MainActivity.nguoiDung!=null){
            btn_dndk.setVisibility(View.INVISIBLE);
            btn_dangxuat.setVisibility(View.VISIBLE);
            rl_thongtin.setVisibility(View.VISIBLE);
            Picasso.get().load(MainActivity.nguoiDung.getHinhanh()).into(iv_logotoi);
            tv_tenuser.setText(MainActivity.nguoiDung.getTennguoidung());

        }else{
            btn_dndk.setVisibility(View.VISIBLE);
            btn_dangxuat.setVisibility(View.INVISIBLE);
            rl_thongtin.setVisibility(View.INVISIBLE);
        }

        btn_dndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        btn_dangxuat.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            MainActivity.nguoiDung = null;
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ToiFragment()).commit();
        });

        rl_diachi.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), DiaChiActivity.class));
        });

        return view;
    }

    public void mapping(View v){
        btn_dndk = (Button) v.findViewById(R.id.btn_dndk);
        btn_dangxuat = (Button) v.findViewById(R.id.btn_dangxuat);
        iv_logotoi = (CircleImageView) v.findViewById(R.id.iv_logotoi);
        rl_thongtin = (RelativeLayout) v.findViewById(R.id.rl_thongtin);
        tv_tenuser  = (TextView) v.findViewById(R.id.tv_tenuser);
        rl_diachi = (RelativeLayout) v.findViewById(R.id.rl_diachi);
    }


}
