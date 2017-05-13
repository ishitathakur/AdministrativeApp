package com.example.ishita.administrativeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WatchmanSearchActivity extends AppCompatActivity {
    private EditText rollno;
    private Button   search;
    String rollNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchman_search);
        rollno=(EditText)findViewById(R.id.roll_no);
        search=(Button) findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                rollNo=rollno.getText().toString();
                String URL="https://eoutpass.herokuapp.com/application/"+rollNo;
                Intent i = new Intent(getApplicationContext(), WatchmanActivity.class);
                i.putExtra("GET_URL",URL);
                startActivity(i);
            }});

    }
}
