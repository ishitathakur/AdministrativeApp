package com.example.ishita.administrativeapp;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WardenActivity extends AppCompatActivity {

    WardenAdapter adapter;
    ListView list;
    Button Accept;
    Button Reject;
    SwipeRefreshLayout mSwipeRefreshLayout;
    PersonalData personalData;
    String GET_URL="https://eoutpass.herokuapp.com/unchecked_hw";
    // private  LoadToast loadToast;
    ArrayList<Application> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendant);
        list = (ListView) findViewById(R.id.list);
        adapter = new WardenAdapter(getApplicationContext(), items);
        list.setAdapter(adapter);
        Accept=(Button)findViewById(R.id.accept);
        Reject=(Button)findViewById(R.id.reject);
        personalData =new PersonalData(this);
        fetchData();
    }
    public void fetchData() {
        Log.v("check","123");
        items.clear();
        //  loadToast.setText("Loading");
        // loadToast.show();

        Log.d("check", "12345");
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, GET_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("JSON", response.toString());
                    if(response.getString("status").equals("DATABASE FETCHED")) {
                        //loadToast.success();
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
                    }
                    else
                    {
                        // loadToast.error();
                        Toast.makeText(getApplicationContext(),"No Applications", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // loadToast.error();
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
}

