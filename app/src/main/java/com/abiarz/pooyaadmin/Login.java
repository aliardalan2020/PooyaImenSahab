package com.abiarz.pooyaadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Login extends AppCompatActivity {
    EditText pass_input, textInputLayout;
    Button ok_btn;
    String URI_SHOW_PARAMS = "http://abiapp.info/pooyaimensahab/api.php";
    RequestQueue requestQueue;
    public static final String TAG = "MyTag";
    SharedPreferences setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        setting = PreferenceManager.getDefaultSharedPreferences(this);
        //font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSans(FaNum).ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        //font

        pass_input = findViewById(R.id.pass_input);
        textInputLayout = findViewById(R.id.textInputLayout);

        ok_btn = findViewById(R.id.ok_btn);

        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textInputLayout.getText().toString().trim().length() > 0) {
                    if (pass_input.getText().toString().trim().length() > 0) {
                        do_login(textInputLayout.getText().toString().trim(), pass_input.getText().toString().trim());
                    } else {
                        Toast.makeText(Login.this, "رمز عبور را وارد نمایید.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "نام کاربری را وارد نمایید.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void do_login(final String user_name, final String password) {
        final StringRequest request = new StringRequest(
                Request.Method.POST,
                URI_SHOW_PARAMS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jo = new JSONObject(response);
                            if (jo.getString("success").equals("1")) {

                                if (jo.getString("status").equals("1")) {

                                    JSONObject jo_data = jo.getJSONObject("data");


                                    String user_id = jo_data.getString("id");
                                    SharedPreferences.Editor editor = setting.edit();
                                    editor.putString("user_id", user_id);
                                    editor.commit();


                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                    Login.this.finish();

                                } else {
                                    Toast.makeText(getApplicationContext(), "کاربری با این مشخصات یافت نشد.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "خطایی رخ داد. دوباره تلاش کنید", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, "دوباره تلاش کنید.", Toast.LENGTH_SHORT).show();
                    }
                }

        ) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("command", "login_admin");
                params.put("password", password);
                params.put("user_name", user_name);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(20000, 0, 2f));
        request.setTag(TAG);
        requestQueue.add(request);
    }
}