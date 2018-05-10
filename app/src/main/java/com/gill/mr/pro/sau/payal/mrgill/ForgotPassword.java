package com.gill.mr.pro.sau.payal.mrgill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassword extends AppCompatActivity {
    TextView txt_login,txt_reg;
    EditText edt_email;
    Button send_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        edt_email = findViewById(R.id.forgot_email);
        txt_login=findViewById(R.id.forgot_login);
        txt_reg = findViewById(R.id.forgot_register);
        send_email =findViewById(R.id.btn_forgot_email_send);
        send_email.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        // call a service
            String url = "http://192.168.1.200/Mr.Gill/forget_password_api?email="+edt_email.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject resp  = new JSONObject(response);
                        if (resp.getInt("status") == 0){
                            Toast.makeText(ForgotPassword.this, resp.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ForgotPassword.this, resp.getString("msg"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

        }
    });
    txt_reg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(ForgotPassword.this,Registeration.class);
            startActivity(intent);

        }
    });
    txt_login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ForgotPassword.this,Login.class);
            startActivity(intent);
        }
    });
    }
}
