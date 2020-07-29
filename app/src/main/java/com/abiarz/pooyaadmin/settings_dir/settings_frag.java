package com.abiarz.pooyaadmin.settings_dir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.abiarz.pooyaadmin.R;

public class settings_frag extends Fragment {
    private View view;

    public settings_frag() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view= inflater.inflate(R.layout.fragment_settings_frag, container, false);



       return view;
    }
}