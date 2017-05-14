package com.example.ishita.administrativeapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class WatchmanSearchActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
    private EditText rollno;
    private Button   search;
    PersonalData personalData;
    String rollNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_watch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        personalData = new PersonalData(this);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
            Intent launch_logout=new Intent(WatchmanSearchActivity.this,MainActivity.class);
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
