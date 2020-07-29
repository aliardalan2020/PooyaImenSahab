package com.abiarz.pooyaadmin.add_guard_dir;//package com.abiarz.pooyaimensahabadmin.add_guard_dir;
//
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RelativeLayout;
//import android.widget.Switch;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.IdRes;
//import androidx.fragment.app.DialogFragment;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//import com.arzabi.abiarz_admin.R;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import cn.pedant.SweetAlert.SweetAlertDialog;
//
//
//public class EditAdminDialog extends DialogFragment {
//    private static final String URI_SHOW_PARAMS="http://abiapp.info/abiapp/api/v1/upload.php";
//    private RequestQueue requestQueue;
//    private static final String TAG = "MyTag";
//
//
//    private EditText user_name,password,email;
//    RadioButton btn_a,btn_b;
//    RadioGroup roles;
//    private String user_name_st,password_st,email_st;
//    Button ok,cancel,delete;
//    private int role_selected=0;
//    private int swch_state=0;
//    TextView active_swch_txt;
//    Switch switchButton;
//
//    RelativeLayout options;
//    CheckBox edit_curr,add_curr,online_status,send_notifs;
//    int edit_curr_st=0,add_curr_st=0,online_status_st=0,send_notifs_st=0;
//
//    public EditAdminDialog() {}
//
//    public static EditAdminDialog newInstance(String id,String user_name,String password,String role,String email,String status,int add_curr,int edit_curr,int online_status,int send_notifs) {
//        EditAdminDialog frag = new EditAdminDialog();
//        Bundle args = new Bundle();
//        args.putString("id", id);
//        args.putString("user_name", user_name);
//        args.putString("password", password);
//        args.putString("role", role);
//        args.putString("email", email);
//        args.putString("status", status);
//        args.putInt("add_curr", add_curr);
//        args.putInt("edit_curr", edit_curr);
//        args.putInt("online_status", online_status);
//        args.putInt("send_notifs", send_notifs);
//        frag.setArguments(args);
//        return frag;
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (getDialog() == null) {
//            return;
//        }
//        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        int width =1000;
//        getDialog().getWindow().setLayout(width, height);
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullscreenTheme_b);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.editadmin_dialog, container, false);
//
//        if (getContext()!=null) requestQueue= Volley.newRequestQueue(getContext());
//
//
//        ok = view.findViewById(R.id.ok);
//        cancel = view.findViewById(R.id.cancel);
//        delete=view.findViewById(R.id.delete);
//
//        options=view.findViewById(R.id.options);
//        user_name = view.findViewById(R.id.user_name);
//        password= view.findViewById(R.id.password);
//        email=view.findViewById(R.id.email);
//        active_swch_txt=view.findViewById(R.id.active_swch_txt);
//
//
//        user_name.setText(getArguments().getString("user_name"));
//        password.setText(getArguments().getString("password"));
//        email.setText(getArguments().getString("email"));
//
//
//        //radio keys
//        btn_a= (RadioButton) view.findViewById(R.id.btn_a);
//        btn_b= (RadioButton) view.findViewById(R.id.btn_b);
//
//        if (getArguments().getString("role").equals("main_admin")){
//            btn_a.setChecked(true);
//            role_selected=1;
//            options.setVisibility(View.GONE);
//        }else {
//            btn_b.setChecked(true);
//            role_selected=2;
//            options.setVisibility(View.VISIBLE);
//        }
//
//
//        //      answer keys     //
//        roles= (RadioGroup) view.findViewById(R.id.roles);
//        roles.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                switch (roles.getCheckedRadioButtonId()){
//                    case R.id.btn_a:
//                        role_selected=1;
//                        options.setVisibility(View.GONE);
//                        break;
//                    case R.id.btn_b:
//                        role_selected=2;
//                        options.setVisibility(View.VISIBLE);
//                        break;
//                }
//            }
//        });
//        //      answer keys     //
//
//
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "لطفا دکمه را نگه دارید...", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "برای حذف این مدیر، دکمه را نگه دارید...", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//
//        delete.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                new SweetAlertDialog(getActivity(), SweetAlertDialog.CUSTOM_IMAGE_TYPE)
//                        .setTitleText("خروج")
//                        .setContentText("آیا از حذف این مدیر مطمئن هستید؟")
//                        .setConfirmText("بله")
//                        .setCancelText("خیر")
//                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                sweetAlertDialog.dismiss();
//                            }
//                        })
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                delete_admin();
//
//                                sweetAlertDialog.dismissWithAnimation();
//                            }
//                        }).show();
//                return false;
//            }
//        });
//
//
//
//
//
//        switchButton =view.findViewById(R.id.active_swch);
//        if (getArguments().getString("status").equals("1")){
//            switchButton.setChecked(true);
//            swch_state=1;
//            active_swch_txt.setText("فعال");
//            active_swch_txt.setTextColor(Color.parseColor("#77c800"));
//        }else {
//            switchButton.setChecked(false);
//            swch_state=2;
//            active_swch_txt.setText("غیر فعال");
//            active_swch_txt.setTextColor(Color.parseColor("#E92B2B"));
//        }
//
//
//        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    swch_state=1;
//                    active_swch_txt.setText("فعال");
//                    active_swch_txt.setTextColor(Color.parseColor("#77c800"));
//                }else {
//                    swch_state=2;
//                    active_swch_txt.setText("غیر فعال");
//                    active_swch_txt.setTextColor(Color.parseColor("#E92B2B"));
//                }
//            }
//        });
//
//
//
//        edit_curr=view.findViewById(R.id.edit_curr);
//        add_curr=view.findViewById(R.id.add_curr);
//        online_status=view.findViewById(R.id.online_status);
//        send_notifs=view.findViewById(R.id.send_notifs);
//
//        edit_curr_st=getArguments().getInt("edit_curr");
//        add_curr_st=getArguments().getInt("add_curr");
//        online_status_st=getArguments().getInt("online_status");
//        send_notifs_st=getArguments().getInt("send_notifs");
//
//        if (getArguments().getInt("add_curr")==1){
//            add_curr.setChecked(true);
//        }else {
//            add_curr.setChecked(false);
//        }
//
//
//        if (getArguments().getInt("edit_curr")==1){
//            edit_curr.setChecked(true);
//        }else {
//            edit_curr.setChecked(false);
//        }
//
//        if (getArguments().getInt("online_status")==1){
//            online_status.setChecked(true);
//        }else {
//            online_status.setChecked(false);
//        }
//
//        if (getArguments().getInt("send_notifs")==1){
//            send_notifs.setChecked(true);
//        }else {
//            send_notifs.setChecked(false);
//        }
//
//
//        edit_curr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    edit_curr_st=1;
//                }else {
//                    edit_curr_st=0;
//                }
//            }
//        });
//
//
//        add_curr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    add_curr_st=1;
//                }else {
//                    add_curr_st=0;
//                }
//            }
//        });
//
//
//        online_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    online_status_st=1;
//                }else {
//                    online_status_st=0;
//                }
//            }
//        });
//
//
//        send_notifs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked){
//                    send_notifs_st=1;
//                }else {
//                    send_notifs_st=0;
//                }
//            }
//        });
//
//
//        ok.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                user_name_st = user_name.getText().toString().trim();
//                password_st = password.getText().toString().trim();
//                email_st=email.getText().toString().trim();
//                if (user_name_st.length() > 0) {
//                    if (password_st.length() > 0) {
//                        if (role_selected>0) {
//                            if (validateEmail(email_st)) {
//                                edit_admin();
//                            }else {
//                                Toast.makeText(getContext(), "آدرس ایمیل صحیح وارد نمایید!", Toast.LENGTH_SHORT).show();
//                            }
//                        }else {
//                            Toast.makeText(getContext(), "نقش مدیر را مشخص نمایید.", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(getContext(), "نام کاربری را وارد نمایید.", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(getContext(), "رمز عبور را وارد نمایید.", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });
//
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//
//
//
//
//        return view;
//    }
//
//    private void delete_admin() {
//        String url="http://abiapp.info/admin/api/v1/api.php";
//        final StringRequest request = new StringRequest(
//                Request.Method.POST,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONObject jo=new JSONObject(response);
//                            String success=jo.getString("success");
//
//                            if (success.equals("1")) {
//                                Toast.makeText(getContext(), "یک مدیر با موفقیت حذف شد.", Toast.LENGTH_SHORT).show();
//
//                                ((AddAdminDialog.refresh_admin) getContext()).refresh_rv_admin();
//                                dismiss();
//                            }
//
//                        } catch (JSONException e) {
//                            Toast.makeText(getContext(), "خطا در حذف مدیر...", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), "خطا در حذف مدیر...", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("command", "delete_admin");
//                params.put("user_id",getArguments().getString("id"));
//                return params;
//            }
//        };
//
//        request.setRetryPolicy(new DefaultRetryPolicy(18000,0,2f));
//        request.setTag(TAG);
//        requestQueue.add(request);
//    }
//
//
//    public interface refresh {
//        public void refresh_rv_curr();
//    }
//
//
//
//    private void edit_admin() {
//        String url="http://abiapp.info/admin/api/v1/api.php";
//        final StringRequest request = new StringRequest(
//                Request.Method.POST,
//                url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        try {
//                            JSONObject jo=new JSONObject(response);
//                            String success=jo.getString("success");
//
//                            if (success.equals("1")) {
//                                Toast.makeText(getContext(), "اطلاعات مدیر با موفقیت به روزرسانی شد.", Toast.LENGTH_SHORT).show();
//
//                                ((AddAdminDialog.refresh_admin) getContext()).refresh_rv_admin();
//                                dismiss();
//                            }
//
//                        } catch (JSONException e) {
//                            Toast.makeText(getContext(), "خطا در ایجاد ارز جدید", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), "خطا در ایجاد ارز جدید", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("command", "edit_admin");
//                params.put("user_id",getArguments().getString("id"));
//                params.put("user_name",user_name_st);
//                params.put("password",password_st);
//                params.put("email",email_st);
//                params.put("edit_curr_st", String.valueOf(edit_curr_st));
//                params.put("add_curr_st", String.valueOf(add_curr_st));
//                params.put("online_status_st", String.valueOf(online_status_st));
//                params.put("send_notifs_st", String.valueOf(send_notifs_st));
//                params.put("role", String.valueOf(role_selected));
//                params.put("status", String.valueOf(swch_state));
//                return params;
//            }
//        };
//
//        request.setRetryPolicy(new DefaultRetryPolicy(18000,0,2f));
//        request.setTag(TAG);
//        requestQueue.add(request);
//    }
//
//
//    private boolean validateEmail(String email) {
//        Pattern pattern;
//        Matcher matcher;
//        String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//        pattern = Pattern.compile(EMAIL_PATTERN);
//        matcher = pattern.matcher(email);
//        return matcher.matches();
//    }
//}
