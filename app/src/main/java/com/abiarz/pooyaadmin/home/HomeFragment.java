package com.abiarz.pooyaadmin.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.abiarz.pooyaadmin.Login;
import com.abiarz.pooyaadmin.MainActivity;
import com.abiarz.pooyaadmin.R;
import com.abiarz.pooyaadmin.add_guard_dir.add_guard_frag;
import com.abiarz.pooyaadmin.online_status_dir.online_status_frag;
import com.abiarz.pooyaadmin.settings_dir.settings_frag;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment extends Fragment {
    private View view;
    private ImageView back_ic;
    private SharedPreferences setting;
    private ImageView add_guard,online_status,settings;
    private String user_id="-1";

    public HomeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_home, container, false);



        init();

        add_guard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_guard_frag home = new add_guard_frag();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_container, home, "add_guard").addToBackStack("add_guard");
                ft.commitAllowingStateLoss();
            }
        });



        online_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                online_status_frag home = new online_status_frag();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_container, home, "online_status").addToBackStack("add_guard");
                ft.commitAllowingStateLoss();
            }
        });



        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settings_frag home = new settings_frag();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_container, home, "settings").addToBackStack("add_guard");
                ft.commitAllowingStateLoss();
            }
        });




        back_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("خروج")
                        .setContentText("آیا می خواهید از برنامه خارج شوید؟")
                        .setConfirmText("بله")
                        .setCancelText("خیر")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                SharedPreferences.Editor editor=setting.edit();
                                editor.putString("user_id","-1");
                                editor.commit();

                                startActivity(new Intent(getContext(), Login.class));
                                getActivity().finish();
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        }).show();
            }
        });



        return view;
    }


    private void init() {
        back_ic=view.findViewById(R.id.back_ic);
        add_guard=view.findViewById(R.id.add_guard);
        online_status=view.findViewById(R.id.online_status);
        settings=view.findViewById(R.id.settings);

        setting= PreferenceManager.getDefaultSharedPreferences(getContext());
    }
}