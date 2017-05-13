package com.example.ishita.administrativeapp;

import android.content.Context;
import android.content.SharedPreferences;

public class PersonalData {
    private static final String TOKEN = "token";
    private Context context;
    private SharedPreferences.Editor editor=null;
    private final String STATUS ="STATUS";

    public PersonalData(Context context)
    {
        this.context=context;
        editor=getEditor();
    }
    private SharedPreferences getSharedPreferences(){
        return context.getSharedPreferences("MyData", Context.MODE_PRIVATE);
    }
    private  SharedPreferences.Editor getEditor(){
        return getSharedPreferences().edit();
    }
    public void SaveToken(String token)
    {
        editor.putString(TOKEN, token);
        editor.commit();
    }
    public void SaveData(boolean status)
    {
        editor.putBoolean(STATUS, status);
        editor.commit();
    }
    public boolean getStatus()
    {
        return getSharedPreferences().getBoolean(STATUS,false);
    }
    public String getToken()
    {
        return getSharedPreferences().getString(TOKEN,"no token");
    }
}


