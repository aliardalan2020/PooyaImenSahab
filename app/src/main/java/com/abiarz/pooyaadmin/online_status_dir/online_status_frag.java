package com.abiarz.pooyaadmin.online_status_dir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import com.abiarz.pooyaadmin.R;

public class online_status_frag extends Fragment {
    private View view;

    public online_status_frag() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_online_status_frag, container, false);


        return view;
    }
}