package com.gill.mr.pro.sau.payal.mrgill;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kaopiz.kprogresshud.KProgressHUD;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {

    private Handler mHandler = new Handler();
    SharedPreferences.Editor editor;
    private Runnable mRunnable;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences preferences = SplashScreen.this.getSharedPreferences("status", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains("email") && preferences.contains("password")) {
            String url = "http://192.168.1.200/Mr.Gill/user_login?email=" + preferences.getString("email", "") + "&password=" + preferences.getString("password", "");
            final KProgressHUD hud = KProgressHUD.create(SplashScreen.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
            Log.e("login url", url);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("fgsdgb", response);
                    try {
                        JSONObject resp = new JSONObject(response);
                        if (resp.getInt("status") == 0) {
                            JSONArray data = resp.getJSONArray("data");
                            JSONObject object = (JSONObject) data.get(0);

                            hud.dismiss();SharedPreferences preferences = getApplicationContext().getSharedPreferences("status", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            preferences.getString("email","email");
                            preferences.getString("password","password");
                            editor.apply();
                            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else
                            {
                                Intent intent = new Intent(SplashScreen.this, Login.class);
                                startActivity(intent);
                                hud.dismiss();
                                Toast.makeText(SplashScreen.this, "Invalid Login", Toast.LENGTH_SHORT).show();
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

            Volley.newRequestQueue(SplashScreen.this).add(stringRequest);

        } else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

        @Override
        protected void onDestroy ()
        {
            if (mHandler != null && mRunnable != null)
            {
                mHandler.removeCallbacks(mRunnable);
            }
            super.onDestroy();
        }
    }


