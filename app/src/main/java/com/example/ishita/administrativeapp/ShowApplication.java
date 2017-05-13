package com.example.ishita.administrativeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ShowApplication extends AppCompatActivity {


    String GET_URL = "https://eoutpass.herokuapp.com/profile";

    PersonalData personalData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.show_application);

        personalData = new PersonalData(this);

        final TextView first_name = (TextView) findViewById(R.id.show_first_name);
        final TextView last_name = (TextView) findViewById(R.id.show_last_name);
        final TextView roll_no = (TextView) findViewById(R.id.show_roll_no);
        final TextView department = (TextView) findViewById(R.id.show_department);
        final TextView room_no = (TextView) findViewById(R.id.show_room_no);
        final TextView semester = (TextView) findViewById(R.id.show_semester);
        final TextView hostel = (TextView) findViewById(R.id.show_hostel);
        final TextView phone_no = (TextView) findViewById(R.id.show_phone_no);
        final TextView phone_no_parent = (TextView) findViewById(R.id.show_phone_no_parent);

        Intent in = getIntent();
        Application bean = (Application) in.getSerializableExtra("Application");

        TextView id = (TextView) findViewById(R.id.show_id);
        //  TextView first_name = (TextView) findViewById(R.id.show_first_name);
        //TextView last_name = (TextView) findViewById(R.id.show_last_name);
        //TextView hostel = (TextView) findViewById(R.id.show_hostel);
        //TextView roll_no = (TextView) findViewById(R.id.show_roll_no);
        //TextView department = (TextView) findViewById(R.id.show_department);
        //TextView semester= (TextView) findViewById(R.id.show_semester);
        TextView departure_date = (TextView) findViewById(R.id.show_departure_date);
        TextView arrival_date = (TextView) findViewById(R.id.show_arrival_date);
        TextView departure_time = (TextView) findViewById(R.id.show_departure_time);
        TextView arrival_time = (TextView) findViewById(R.id.show_arrival_time);
        TextView place = (TextView) findViewById(R.id.show_place);
        TextView purpose = (TextView) findViewById(R.id.show_purpose);
        // TextView phone_no = (TextView) findViewById(R.id.show_phone_no);
        // TextView phone_no_parent = (TextView) findViewById(R.id.show_phone_no_parent);
        id.setText(bean.getId());
        // first_name.setText(bean.getFirst_Name());
        // last_name.setText(bean.getLast_Name());
        //hostel.setText(bean.getHostel());
        // roll_no.setText(bean.getRoll_No());
        // department.setText(bean.getDepartment());
        //  semester.setText(bean.getSemester());
        departure_date.setText(bean.getDeparture_Date());
        arrival_date.setText(bean.getArrival_Date());
        departure_time.setText(bean.getDeparture_Time());
        arrival_time.setText(bean.getArrival_Time());
        place.setText(bean.getPlace());
        purpose.setText(bean.getPurpose());
        // phone_no.setText(bean.getPhone_No());
        //phone_no_parent.setText(bean.getPhone_No_Parent());

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, GET_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("OBJECT", response.toString());

                    if (response.getString("status").equals("FETCHED")) {
                        Log.d("Profile information", response.toString());

                        JSONObject ob = response.getJSONObject("data");
                        first_name.setText(((String) ob.get("first_name")));
                        last_name.setText((String) ob.get("last_name"));
                        department.setText((String) ob.get("department"));
                        semester.setText((String) ob.get("semester"));
                        room_no.setText((String) ob.get("room_no"));
                        roll_no.setText((String) ob.get("roll_no"));
                        phone_no.setText((String) ob.get("phone_no"));
                        phone_no_parent.setText((String) ob.get("phone_parent"));
                        hostel.setText((String) ob.get("hostel"));

                    } else {

                        Toast.makeText(getApplicationContext(), "No Profile", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "No Connection", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Authorization", "token " + personalData.getToken());
                return map;
            }
        };
        queue.add(req);






    }
}

