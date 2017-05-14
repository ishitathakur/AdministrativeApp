package com.example.ishita.administrativeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    PersonalData personalData;
    public static final String WARDEN = "WARDEN";
    public static final String ATTENDENT = "ATTENDENT";
    public static final String WATCHMAN = "WATCHMAN";
    Button warden,attendent,watchman;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        personalData = new PersonalData(this);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                   startDesiredActivity();

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void startDesiredActivity(){
        Intent mainIntent = null;
        if(personalData.getStatus()){
            if(personalData.getRole().equals(ATTENDENT)){
                mainIntent = new Intent(Splash.this, AttendantActivity.class);
            }else if(personalData.getRole().equals(WARDEN)){
                mainIntent = new Intent(Splash.this, WardenActivity.class);
            }else{
                mainIntent = new Intent(Splash.this, WatchmanActivity.class);
            }
        }else{
            mainIntent = new Intent(Splash.this, MainActivity.class);
        }
        Splash.this.startActivity(mainIntent);
        Splash.this.finish();
    }
}
