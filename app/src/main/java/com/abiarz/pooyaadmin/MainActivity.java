package com.abiarz.pooyaadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;

import com.abiarz.pooyaadmin.add_guard_dir.add_guard_frag;
import com.abiarz.pooyaadmin.home.HomeFragment;
import com.abiarz.pooyaadmin.online_status_dir.online_status_frag;
import com.abiarz.pooyaadmin.settings_dir.settings_frag;

import cn.pedant.SweetAlert.SweetAlertDialog;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainActivity extends MyActivity {
    private ImageView back_ic;
    private SharedPreferences setting;
    private ImageView add_guard,online_status,settings;
    private String user_id="-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSans(FaNum).ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        //font


        setting= PreferenceManager.getDefaultSharedPreferences(this);
        user_id=String.valueOf(setting.getString("user_id","-1"));

        if (setting.getString("user_id","-1").equals("-1"))
        {
            startActivity(new Intent(MainActivity.this, Login.class));
            MainActivity.this.finish();
        }else {
            HomeFragment home = new HomeFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.main_container, home, "add_guard").addToBackStack("add_guard");
            ft.commitAllowingStateLoss();
        }

    }

}