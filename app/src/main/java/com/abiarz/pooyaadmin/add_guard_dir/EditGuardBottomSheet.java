package com.abiarz.pooyaadmin.add_guard_dir;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.abiarz.pooyaadmin.R;
import com.abiarz.pooyaadmin.bus.RefreshGuardBus;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class EditGuardBottomSheet extends BottomSheetDialogFragment {
    private View view;
    private Button btn_submit;
    private String id="",name="",family="",shift="",user="",pass="",phone="";
    private EditText name_value,mobile_value,pass_value,user_value,shift_value,family_value;
    private RequestQueue requestQueue;
    
    public static EditGuardBottomSheet newInstance() {
        return new EditGuardBottomSheet();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.edit_guard_bottom_sheet, container,false);


        id=getArguments().getString("id");
        name=getArguments().getString("name");
        family=getArguments().getString("family");
        shift=getArguments().getString("shift");
        user=getArguments().getString("user");
        pass=getArguments().getString("pass");
        phone=getArguments().getString("phone");

        initialize();


        Log.e("gjvgk",id+":"+name+":"+family+":"+shift);

        name_value.setText(name);
        family_value.setText(family);
        shift_value.setText(shift);
        user_value.setText(user);
        pass_value.setText(pass);
        mobile_value.setText(phone);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_guard(name_value.getText().toString().trim(),family_value.getText().toString().trim(),shift_value.getText().toString().trim(),user_value.getText().toString().trim(),pass_value.getText().toString().trim(),mobile_value.getText().toString().trim());
                EventBus.getDefault().post(new RefreshGuardBus());
                dismissAllowingStateLoss();
            }
        });





        return view;

    }


    private void update_guard(final String name, final String family, final String shift,final String user,final String pass,final String mobile) {

        String url="http://abiapp.info/pooyaimensahab/api.php";
        //////////////////////////////////////
        final StringRequest request = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObjects=new JSONObject(response);
                            Log.e("vehicle_company_list", String.valueOf(jsonObjects));

                            if(jsonObjects.getInt("success")==1) {
                                dismissAllowingStateLoss();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getContext(), "خطایی رخ داد! دوباره تلاش کنید."+e, Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "خطایی رخ داد! دوباره تلاش کنید."+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("command","update_guard");
                params.put("id",id);
                params.put("name",name);
                params.put("family",family);
                params.put("shift",shift);
                params.put("user",user);
                params.put("pass",pass);
                params.put("mobile",mobile);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(18000,2,2f));
        request.setTag(TAG);
        requestQueue.add(request);
    }



    private void initialize() {
        btn_submit = view.findViewById(R.id.btn_submit);

        mobile_value=view.findViewById(R.id.mobile_value);
        pass_value=view.findViewById(R.id.pass_value);
        user_value=view.findViewById(R.id.user_value);
        shift_value=view.findViewById(R.id.shift_value);
        family_value=view.findViewById(R.id.family_value);
        name_value=view.findViewById(R.id.name_value);

        requestQueue= Volley.newRequestQueue(getContext());
    }
}