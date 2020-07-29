package com.abiarz.pooyaadmin.add_guard_dir;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abiarz.pooyaadmin.Login;
import com.abiarz.pooyaadmin.R;
import com.abiarz.pooyaadmin.bus.DeleteGuardBus;
import com.abiarz.pooyaadmin.bus.EditGuardBus;
import com.abiarz.pooyaadmin.bus.RefreshGuardBus;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.ContentValues.TAG;

public class add_guard_frag extends Fragment {
  private View view;

    private adapterAdmin adapter;
    private ProgressBar pb_hottopics;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private ImageView back_ic;
    FloatingActionButton fab_add_currency;


    String URI_SHOW_PARAMS = "http://abiapp.info/pooyaimensahab/api.php";
    public static final String TAG = "MyTag";
    SharedPreferences setting;

    public add_guard_frag() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_add_guard_frag, container, false);


        init();


        back_ic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });


        setUpRecyclerView();



        fab_add_currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction ft_a;
//                assert getFragmentManager() != null;
//                ft_a = getFragmentManager().beginTransaction();
//                AddAdminDialog newFragment = AddAdminDialog.newInstance();
//                newFragment.show(ft_a, "add_admin");
//                newFragment.setCancelable(true);
            }
        });

        return view;
    }

    private void init() {
        requestQueue = Volley.newRequestQueue(getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        pb_hottopics = view.findViewById(R.id.pb_hottopics);
        fab_add_currency = view.findViewById(R.id.fab_add_currency);
        back_ic = view.findViewById(R.id.back_ic);

        requestQueue = Volley.newRequestQueue(getContext());
        setting = PreferenceManager.getDefaultSharedPreferences(getContext());
    }


    public void setUpRecyclerView(){
            final StringRequest request = new StringRequest(
                    Request.Method.POST,
                    URI_SHOW_PARAMS,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jo = new JSONObject(response);
                                Log.e("setUpRecyclerView_ad", String.valueOf(jo));
                                if (jo.getString("success").equals("1")) {

                                    pb_hottopics.setVisibility(View.GONE);

                                    List<datamodelAdmin> datamodels_v=new ArrayList<>();
                                    JSONArray jsonArray_v = jo.getJSONArray("guard_list");

                                    for (int i = 0; i < jsonArray_v.length(); i++) {
                                        datamodelAdmin datamodel_v = new datamodelAdmin();
                                        try {
                                            JSONObject jsonObject_v = jsonArray_v.getJSONObject(i);

                                            datamodel_v.setId(jsonObject_v.getString("id"));
                                            datamodel_v.setName(jsonObject_v.getString("name"));
                                            datamodel_v.setFamily(jsonObject_v.getString("family"));
                                            datamodel_v.setUser_name(jsonObject_v.getString("user"));
                                            datamodel_v.setPassword(jsonObject_v.getString("pass"));
                                            datamodel_v.setPhone(jsonObject_v.getString("phone"));
                                            datamodel_v.setShift(jsonObject_v.getString("shift"));

                                            datamodels_v.add(datamodel_v);
                                        } catch (JSONException e) {
                                            Toast.makeText(getContext(), "خطا در دریافت اطلاعات از سرور! دوباره تلاش کنید."+e, Toast.LENGTH_SHORT).show();
                                        }
                                    }




                                    pb_hottopics.setVisibility(View.GONE);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    adapter = new adapterAdmin(getContext(), datamodels_v);
                                    recyclerView.setAdapter(adapter);


                                    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                        @Override
                                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                            super.onScrolled(recyclerView, dx, dy);
                                            if (dy > 0 && fab_add_currency.getVisibility() == View.VISIBLE) {
                                                fab_add_currency.hide();
                                            } else if (dy < 0 && fab_add_currency.getVisibility() != View.VISIBLE) {
                                                fab_add_currency.show();
                                            }
                                        }
                                    });



                                } else {
                                    Toast.makeText(getContext(), "خطایی رخ داد. دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();
                        }
                    }

            ) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("command", "admins_list");
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(20000, 0, 2f));
            request.setTag(TAG);
            requestQueue.add(request);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EditGuardBus event) {
        Bundle postion_clicked=new Bundle();
        postion_clicked.putString("id", String.valueOf(event.id));
        postion_clicked.putString("name",event.name);
        postion_clicked.putString("family",event.family);
        postion_clicked.putString("shift",event.shift);
        postion_clicked.putString("user",event.user);
        postion_clicked.putString("pass",event.pass);
        postion_clicked.putString("phone",event.phone);

        EditGuardBottomSheet addPhotoBottomDialogFragment = EditGuardBottomSheet.newInstance();
        addPhotoBottomDialogFragment.setArguments(postion_clicked);
        addPhotoBottomDialogFragment.show(getFragmentManager(), "fragment");
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefreshGuardBus event) {
        Toast.makeText(getContext(), "با موفقیت به روز شد!", Toast.LENGTH_SHORT).show();
        setUpRecyclerView();
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final DeleteGuardBus event) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("حذف نگهبان")
                .setContentText("آیا می خواهید این نگهبان را حذف کنید؟")
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
                       delete_guard(event.id);
                    }
                }).show();
    }



    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    private void delete_guard(final String id) {
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
                                Toast.makeText(getContext(), "با موفقیت حذف شد!", Toast.LENGTH_SHORT).show();
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
                params.put("command","delete_guard");
                params.put("id",id);
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(18000,2,2f));
        request.setTag(TAG);
        requestQueue.add(request);
    }


}