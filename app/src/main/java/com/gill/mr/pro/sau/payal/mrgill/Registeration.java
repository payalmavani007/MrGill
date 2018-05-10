package com.gill.mr.pro.sau.payal.mrgill;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Registeration extends AppCompatActivity {
    EditText edt_name, edt_email, edt_pwd, edt_number;
    Button btn_register;
    TextView txt_login_reg, txt_setdate,txt_gender;
    private int mYear, mMonth, mDay;
    ImageView img_calender;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        edt_name = findViewById(R.id.reg_uname);
        edt_email = findViewById(R.id.reg_email);
        edt_pwd = findViewById(R.id.reg_pwd);
        edt_number = findViewById(R.id.reg_number);
        btn_register = findViewById(R.id.btn_register);



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_name.getText().toString().equals(""))
                {
                    edt_name.setError("Enter Name");
                }
                else if (edt_email.getText().toString().equals(""))
                {
                    edt_email.setError("Enter Email");
                }

                else if (edt_pwd.getText().toString().equals(""))
                {
                    edt_pwd.setError("Enter Password");
                }

                else if (edt_number.getText().toString().equals(""))
                {
                    edt_number.setError("Enter Number");
                }
                else if (txt_gender.getText().toString().equals("")){
                    txt_gender.setError("Select Gender.");
                }
                else if (txt_setdate.getText().toString().equals(""))
                {
                    txt_setdate.setError("Select date");
                }
            else {
                    //String url = "http://192.168.1.200/Mr.Gill/form_register?name=kanji&email=kanji@gmail.com&password=123123&gender=male&contact=1234567890&birthdate=1993-11-27";
                    String url = "http://192.168.1.200/Mr.Gill/form_register?name="
                    +edt_name.getText().toString()+
                            "&email="+edt_email.getText().toString()+
                            "&password="+edt_pwd.getText().toString()+
                            "&gender="+txt_gender.getText().toString()+
                            "&contact="+edt_number.getText().toString()+
                                    "&birthdate="+txt_setdate.getText().toString();

                    final KProgressHUD hud = KProgressHUD.create(Registeration.this)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setCancellable(false)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                    Log.e("Registration url",url);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject resp = new JSONObject(response);
                                if (resp.getInt("status") == 0) {
                                    JSONArray data = resp.getJSONArray("data");
                                    JSONObject object = (JSONObject) data.get(0);
                                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("status", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("id", object.getString("id"));
                                    editor.putString("name", edt_name.getText().toString());
                                    editor.putString("gender", object.getString("gender"));
                                    editor.putString("birthdate", object.getString("birthdate"));
                                    editor.putString("contact", edt_number.getText().toString());
                                    editor.putString("email", edt_email.getText().toString());
                                    editor.putString("password", edt_pwd.getText().toString());
                                    hud.dismiss();
                                    Toast.makeText(Registeration.this, "Register Successfull.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Registeration.this, Login.class);
                                    startActivity(intent);

                                }
                                        else {

                                        hud.dismiss();
                                        Toast.makeText(Registeration.this, "Invalid register", Toast.LENGTH_SHORT).show();
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
            }
        });
        txt_login_reg = findViewById(R.id.login_reg);
        txt_login_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registeration.this, Login.class);
                startActivity(intent);
            }
        });
        txt_setdate = findViewById(R.id.set_date);
        img_calender = findViewById(R.id.img_cal);
        img_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                @SuppressLint("ResourceType") DatePickerDialog datePickerDialog = new DatePickerDialog(Registeration.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                Date dx = new Date(year - 1900, monthOfYear, dayOfMonth);
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                                String cdate = formatter.format(dx);
                                txt_setdate.setText(cdate);
                                txt_setdate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();

            }
        });
        txt_gender= findViewById(R.id.gender);

        txt_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name[] = {"Female", "Male"};
                final Dialog dialog = new Dialog(Registeration.this);
                dialog.setContentView(R.layout.genderlist);
                lv = dialog.findViewById(R.id.lv);
                dialog.setCancelable(true);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, name);
                lv.setAdapter(adapter);
                dialog.show();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView textView = (TextView) view;
                        txt_gender.setText(name[i]);
                        dialog.dismiss();
                    }
                });

            }
        });

    }
}
