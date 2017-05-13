package com.example.ishita.administrativeapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttendantActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    AttendentAdapter adapter;
    ListView list;
    SwipeRefreshLayout mSwipeRefreshLayout;
    PersonalData personalData;
    private final String GET_URL="https://eoutpass.herokuapp.com/unchecked_ha";
    private  LoadToast loadToast;
    ArrayList<Application> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         // setSupportActionBar(toolbar);
         //   toolbar.setTitleTextColor(Color.WHITE);

        loadToast=new LoadToast(this);
        loadToast.setTranslationY(150);
        loadToast.setBackgroundColor(Color.WHITE).setProgressColor(Color.parseColor("#FF4081")).setTextColor(Color.BLACK);
        loadToast = new LoadToast(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refresh();

            }
        });
        list = (ListView) findViewById(R.id.list);
        adapter = new AttendentAdapter(getApplicationContext(), items,loadToast);
        list.setAdapter(adapter);
        personalData =new PersonalData(this);
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        //toggle.syncState();
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        fetchData();
    }

    private void refresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        fetchData();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void fetchData() {
        items.clear();
        loadToast.setText("Loading");
        loadToast.show();

        Log.d("check", "12345");
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, GET_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("JSON", response.toString());
                    if(response.getString("status").equals("DATABASE FETCHED")) {
                        loadToast.success();
                        JSONArray array = response.getJSONArray("data");
                        if(array.length()==1)
                        {
                            Toast.makeText(getApplicationContext(),"No Applications", Toast.LENGTH_SHORT);
                        }
                        for (int i = array.length() - 1; i >= 0; i--) {
                            Application bean = new Application();
                            JSONObject ob = array.getJSONObject(i);

                            Log.d("JSON", ob.toString());

                            bean.setId((String) ob.get("uid"));
                            bean.setDeparture_Date((String) ob.get("depart_date"));
                            bean.setArrival_Date((String) ob.get("arrival_date"));
                            bean.setArrival_Time((String) ob.get("arrival_time"));
                            bean.setDeparture_Time((String) ob.get("dept_time"));
                            bean.setPlace((String) ob.get("place"));
                            bean.setPurpose((String) ob.get("purpose"));
                            bean.setAcceptedWarden((Boolean) ob.get("chw"));
                            bean.setAcceptedAttendant((Boolean) ob.get("cha"));


                            bean.setFirstame((String) ob.get("first_name"));
                            bean.setLastame((String) ob.get("last_name"));
                            bean.setDepartment((String) ob.get("department"));
                            bean.setSemester((String) ob.get("semester"));
                            bean.setRollNo((String) ob.get("roll_no"));
                            bean.setRoomNo((String) ob.get("room_no"));
                            bean.setPhoneNo((String) ob.get("purpose"));
                            bean.setParentPhoneNo((String) ob.get("phone_parent"));
                            bean.setHostel((String) ob.get("hostel"));

                            Log.d("Received", bean.toString());
                            items.add(bean);
                            adapter.notifyDataSetChanged();
                        }
                    }else {
                        loadToast.error();
                        Toast.makeText(getApplicationContext(),"No Applications", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadToast.error();
                Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Authorization","token " + personalData.getToken());
                return map;
            }

        };
//
        req.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(req);
    }
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();
        if(id==R.id.action_logout){

            personalData.SaveData(false);
            Intent launch_logout=new Intent(AttendantActivity.this,MainActivity.class);
            launch_logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            launch_logout.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(launch_logout);
            finish();

        }/*else if(id==R.id.password) {
            Intent in = new Intent(getApplicationContext(), ChangePassword.class);
            startActivity(in);

        }*/
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id= item.getItemId();
        if(id==R.id.profile){
           /* Intent launch_profile= new Intent(getApplicationContext(),Profile.class);
            launch_profile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            launch_profile.putExtra("EXIT",true);
            startActivity(launch_profile);*/
        } else if(id==R.id.about){
          /*  AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(String.format("%1$s", getString(R.string.app_name)));
            builder.setMessage(getResources().getText(R.string.about_text));
            builder.setPositiveButton("OK", null);
            builder.setIcon(R.mipmap.ic_launcher);
            AlertDialog welcomeAlert = builder.create();
            welcomeAlert.show();
            ((TextView) welcomeAlert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());*/
        }else if(id==R.id.contributors){
           /* AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(String.format("%1$s", getString(R.string.contributors)));
            builder.setMessage(getResources().getText(R.string.contributors_text));
            builder.setPositiveButton("OK", null);
            //builder.setIcon(R.mipmap.nimbus_icon);
            AlertDialog welcomeAlert = builder.create();
            welcomeAlert.show();
            ((TextView) welcomeAlert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());*/
        }else if(id==R.id.bug){
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode("ishitathakur@gmail.com") + "?subject=" +
                    Uri.encode("Reporting A Bug/Feedback") + "&body=" + Uri.encode("Hello, Ishita \nI want to report a bug/give feedback corresponding to the app EOutPass.\n.....\n\n-Your name");

            Uri uri = Uri.parse(uriText);
            intent.setData(uri);
            startActivity(Intent.createChooser(intent, "Send Email"));
        }else if(id==R.id.licenses){
          /*  AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(String.format("%1$s", getString(R.string.open_source_licenses)));
            builder.setMessage(getResources().getText(R.string.licenses_text));
            builder.setPositiveButton("OK", null);
            //builder.setIcon(R.mipmap.nimbus_icon);
            AlertDialog welcomeAlert = builder.create();
            welcomeAlert.show();
            ((TextView) welcomeAlert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());*/
        }
        return true;

    }

}
