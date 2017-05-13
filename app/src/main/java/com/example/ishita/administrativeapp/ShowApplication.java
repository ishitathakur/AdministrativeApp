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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.show_application);

        TextView first_name = (TextView) findViewById(R.id.show_first_name);
        TextView last_name = (TextView) findViewById(R.id.show_last_name);
        TextView roll_no = (TextView) findViewById(R.id.show_roll_no);
        TextView department = (TextView) findViewById(R.id.show_department);
        TextView room_no = (TextView) findViewById(R.id.show_room_no);
        TextView semester = (TextView) findViewById(R.id.show_semester);
        TextView hostel = (TextView) findViewById(R.id.show_hostel);
        TextView phone_no = (TextView) findViewById(R.id.show_phone_no);
        TextView phone_no_parent = (TextView) findViewById(R.id.show_phone_no_parent);
        TextView id = (TextView) findViewById(R.id.show_id);
        TextView departure_date = (TextView) findViewById(R.id.show_departure_date);
        TextView arrival_date = (TextView) findViewById(R.id.show_arrival_date);
        TextView departure_time = (TextView) findViewById(R.id.show_departure_time);
        TextView arrival_time = (TextView) findViewById(R.id.show_arrival_time);
        TextView place = (TextView) findViewById(R.id.show_place);
        TextView purpose = (TextView) findViewById(R.id.show_purpose);

        Intent in = getIntent();
        Application bean = (Application) in.getSerializableExtra("Application");

        id.setText(bean.getId());
        first_name.setText(bean.getFirstName());
        last_name.setText(bean.getLastName());
        hostel.setText(bean.getHostel());
        roll_no.setText(bean.getRollNo());
        department.setText(bean.getDepartment());
        semester.setText(bean.getSemester());
        departure_date.setText(bean.getDeparture_Date());
        arrival_date.setText(bean.getArrival_Date());
        departure_time.setText(bean.getDeparture_Time());
        arrival_time.setText(bean.getArrival_Time());
        place.setText(bean.getPlace());
        purpose.setText(bean.getPurpose());
        phone_no.setText(bean.getPhoneNo());
        phone_no_parent.setText(bean.getParentPhoneNo());
        room_no.setText(bean.getRoomNo());
    }
}
