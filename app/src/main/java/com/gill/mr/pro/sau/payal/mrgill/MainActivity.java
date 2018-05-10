package com.gill.mr.pro.sau.payal.mrgill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.MailTo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
MyRecyclerViewAdapter adapter;
ImageView imageView;
Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        imageView =findViewById(R.id.logout);

        SharedPreferences preferences = MainActivity.this.getSharedPreferences("status", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        imageView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        editor.clear().apply();
        Intent i = new Intent(MainActivity.this, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }
});

        displaydata();
    }

    private void displaydata() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        String url = "http://192.168.1.200/Mr.Gill/getCourceData";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("res",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    ArrayList<ListModel> listModels=new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = (JSONObject) array.get(i);
                        ListModel list_model = new ListModel();
                        list_model.setName(o.getString("name"));
                        list_model.setDescription(o.getString("description"));
                        list_model.setUrl(o.getString("url"));
                        list_model.setPicture(o.getString("picture"));

                        listModels.add(list_model);

                    }
                    adapter = new MyRecyclerViewAdapter(getApplicationContext(), listModels);
                    recyclerView.setAdapter(adapter);

                }
                 catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                
            }
        });
        requestQueue.add(stringRequest);
    }
}
