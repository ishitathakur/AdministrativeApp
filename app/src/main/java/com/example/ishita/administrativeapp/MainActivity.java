package com.example.ishita.administrativeapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText userId;

    private EditText password;
    LoadToast loadToast;
    PersonalData personalData;
    private final String URL="https://eoutpass.herokuapp.com/login";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //    setSupportActionBar(toolbar);
       // toolbar.setTitleTextColor(Color.WHITE);
        loadToast=new LoadToast(this);
        loadToast.setTranslationY(150);
        loadToast.setBackgroundColor(Color.WHITE).setProgressColor(Color.parseColor("#FF4081")).setTextColor(Color.BLACK);
       // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
       // ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
      //  drawer.setDrawerListener(toggle);
       // toggle.syncState();
       // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
       // navigationView.setNavigationItemSelectedListener(this);
        personalData = new PersonalData(this);
        userId=(EditText)findViewById(R.id.userId);
        password=(EditText)findViewById(R.id.password);
        loadToast= new LoadToast(this);

        if (getIntent().getBooleanExtra("EXIT", false))
        {
            finish();
        }

        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //isRollNo = Validate.checkData(roll_no.getText().toString());
                //isPassword = Validate.checkData(password.getText().toString());
                final String UserId = userId.getText().toString();
                String Password = password.getText().toString();

                    if(null!=UserId && null != Password){
                    HashMap<String, String> param  = new HashMap<String, String>();
                    loadToast.show();
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
                                    loadToast.success();
                                    personalData.SaveToken(token);
                                    personalData.SaveData(true);
                                    Intent i = null;
                                    if(UserId.equals("10001") || UserId.equals("10002")){
                                        personalData.saveRole(Splash.ATTENDENT);
                                        i = new Intent(getApplicationContext(), AttendantActivity.class);
                                    }else if (UserId.equals("00001") || UserId.equals("00002")){
                                        personalData.saveRole(Splash.WARDEN);
                                        i = new Intent(getApplicationContext(), WardenActivity.class);
                                    }else{
                                        personalData.saveRole(Splash.WATCHMAN);
                                        i = new Intent(getApplicationContext(), WatchmanSearchActivity.class);
                                    }
                                    startActivity(i);
                                    finish();
                                }  else{
                                   loadToast.error();
                                    Toast.makeText(getApplicationContext(), "Not Reachable", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                loadToast.error();
                                Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
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
                                    loadToast.error();
                                    Toast.makeText(getApplicationContext(),"Roll No./ Password Incorrect", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                loadToast.error();
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

