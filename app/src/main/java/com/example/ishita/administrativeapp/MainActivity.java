package com.example.ishita.administrativeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText userId;
    private Button register;
    private TextView forgot;
    private FloatingActionButton fab;
    private EditText roll_no;
    private EditText password;
    boolean isPassword;
    boolean isRollNo;
    Toast loadToast;
    PersonalData personalData;
    private String URL="https://eoutpass.herokuapp.com/login";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        personalData = new PersonalData(this);
        userId=(EditText)findViewById(R.id.userId);
        password=(EditText)findViewById(R.id.password);
        loadToast= new Toast(this);

        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }

        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               // isRollNo = Validate.checkData(roll_no.getText().toString());
                //isPassword = Validate.checkData(password.getText().toString());
                String UserId = userId.getText().toString();
                String Password = password.getText().toString();

                if (userId.equals("00001") && Password.equals("wardenpgh")) {
                    if(true){
                    HashMap<String, String> param  = new HashMap<String, String>();
                    //loadToast.show();
                    param.put("roll_no", UserId);
                    param.put("password", Password);
                    JSONObject obj = new JSONObject(param);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                Log.d("Check",jsonObject.toString());
                                String status = jsonObject.getString("status");

                                if (status.equals("LOGIN")) {

                                    String token = jsonObject.getString("data");
                                    personalData.SaveToken(token);
                                    personalData.SaveData(true);
                                    Intent i = new Intent(getApplicationContext(), AttendantActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                              /*  else
                                 {
                                   loadToast.error();
                                    Toast.makeText(getApplicationContext(), "Not Reachable", Toast.LENGTH_SHORT).show();
                                }*/
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            NetworkResponse networkResponse= volleyError.networkResponse;
                            if(networkResponse!=null)
                            {
                                if(networkResponse.statusCode==401)
                                {
                                //    loadToast.error();
                                    Toast.makeText(getApplicationContext(),"Roll No./ Password Incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                              //  loadToast.error();
                                Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    queue.add(req);
                } else {
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });



        login_warden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // isRollNo = Validate.checkData(roll_no.getText().toString());
                //isPassword = Validate.checkData(password.getText().toString());
                String Roll_no = roll_no.getText().toString();
                String Password = password.getText().toString();
                if (true) {
                    HashMap<String, String> param = new HashMap<String, String>();
                    //loadToast.show();
                    param.put("roll_no", Roll_no);
                    param.put("password", Password);
                    JSONObject obj = new JSONObject(param);
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, obj, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                Log.d("Check",jsonObject.toString());
                                String status = jsonObject.getString("status");

                                if (status.equals("LOGIN")) {

                                    String token = jsonObject.getString("data");
                                    personalData.SaveToken(token);
                                    personalData.SaveData(true);
                                    Intent i = new Intent(getApplicationContext(), WardenActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                              /*  else
                                 {
                                   loadToast.error();
                                    Toast.makeText(getApplicationContext(), "Not Reachable", Toast.LENGTH_SHORT).show();
                                }*/
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            NetworkResponse networkResponse= volleyError.networkResponse;
                            if(networkResponse!=null)
                            {
                                if(networkResponse.statusCode==401)
                                {
                                    //    loadToast.error();
                                    Toast.makeText(getApplicationContext(),"Roll No./ Password Incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                //  loadToast.error();
                                Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    queue.add(req);
                } else {
                    Toast.makeText(getApplicationContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}

