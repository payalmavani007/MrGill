package com.gill.mr.pro.sau.payal.mrgill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    TextView txt_reg,txt_fpwd;
    EditText edt_email,edt_pwd;
    Button btn_login_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt_reg = findViewById(R.id.login_reg_here);
        txt_fpwd = findViewById(R.id.login_fpwd_here);
        edt_email = findViewById(R.id.login_email);
        edt_pwd = findViewById(R.id.login_pwd);
        btn_login_main = findViewById(R.id.btn_login_main);

        btn_login_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_email.getText().toString().equals(""))
                {
                    edt_email.setError("Email Required");
                }
                else if (edt_pwd.getText().toString().equals(""))
                {
                    edt_pwd.setError("Password Required");
                }
                else {
                //String url = "http://192.168.1.200/Mr.Gill/user_login?email=test@gmail.com&password=123123";
                String url = "http://192.168.1.200/Mr.Gill/user_login?email="+edt_email.getText().toString()+"&password=" +edt_pwd.getText().toString();
                    final KProgressHUD hud = KProgressHUD.create(Login.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                    Log.e("login url",url);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("fgsdgb",response);
                        try {
                            JSONObject resp  = new JSONObject(response);
                            if (resp.getInt("status") == 0){
                                JSONArray data = resp.getJSONArray("data");
                                JSONObject object = (JSONObject) data.get(0);
                                SharedPreferences preferences = Login.this.getSharedPreferences("status", MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("id", object.getString("id"));
                                editor.putString("name", object.getString("name"));
                                editor.putString("gender", object.getString("gender"));
                                editor.putString("birthdate", object.getString("birthdate"));
                                editor.putString("contact", object.getString("contact"));
                                editor.putString("email", edt_email.getText().toString());
                                editor.putString("password", edt_pwd.getText().toString());
                                editor.apply();
                                hud.dismiss();
                                Intent intent = new Intent(Login.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            }
                            else {

                                hud.dismiss();
                                Toast.makeText(Login.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> param = new HashMap<>();
                            param.put("email", edt_email.getText().toString());
                            param.put("password", edt_pwd.getText().toString());
                            return param;
                        }
                    };
                    Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

                }

            }
        });
        txt_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registeration.class);
                startActivity(intent);
            }
        });
        txt_fpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,ForgotPassword.class);
                startActivity(intent);
            }
        });
    }
}
