package com.example.ishita.administrativeapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ishita on 8/5/17.
 */
public class WardenAdapter extends BaseAdapter {
    Context c;
    ArrayList<Application> items = new ArrayList<>();
    private PersonalData  personalData;
    private LoadToast loadToast;
    public static final String URL="https://eoutpass.herokuapp.com/unchecked_hw";

    public WardenAdapter(Context c, ArrayList<Application> items,LoadToast loadToast) {
        this.c = c;
        this.items = items;
        this.personalData = new PersonalData(c);
        this.loadToast = loadToast;
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row;
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.application_item_warden, parent, false);
        } else{
            row = convertView;
        }
        TextView first_name = (TextView) row.findViewById(R.id.first_name);
        TextView last_name = (TextView) row.findViewById(R.id.last_name);
        TextView roll_no = (TextView) row.findViewById(R.id.last_name);
        TextView id = (TextView) row.findViewById(R.id.item_id);
        final Button accept=(Button)row.findViewById(R.id.accept);
        final Button reject=(Button)row.findViewById(R.id.reject);

        id.setText("" + items.get(position).getId());
        first_name.setText("" + items.get(position).getFirstName());
        last_name.setText(" " + items.get(position).getLastName());
        roll_no.setText(" " + items.get(position).getLastName());

        CardView card = (CardView) row.findViewById(R.id.list_card);

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application bean = items.get(position);
                Intent in = new Intent(c, ShowApplication.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.putExtra("Application", bean);
                c.startActivity(in);
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "true";
                requestTheServer(position,accept,reject,status);
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status = "false";
                requestTheServer(position,accept,reject,status);
            }
        });

        return row;
    }

    /**
     * This function will request the server with the accept or reject response
     * @param position
     * @param acceptButton
     * @param rejectButton
     * @param status
     */
    private void requestTheServer(int position, final Button acceptButton, final Button rejectButton, String status){
        Application bean  = items.get(position);
        loadToast.show();
        String uid = bean.getId();

        JSONArray array = new JSONArray();

        HashMap<String, String> param = new HashMap<String, String>();

        param.put("uid", uid);
        param.put("status", status);


        JSONObject obj = new JSONObject(param);
        array.put(obj);
        RequestQueue queue = Volley.newRequestQueue(c);
        ModifiedJsonArrayRequest req = new ModifiedJsonArrayRequest(URL,array,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Log.d("Check",jsonObject.toString());
                    String status = jsonObject.getString("status");
                    if (status.equals("Updated")) {
                        String data = jsonObject.getString("data");
                        if(data.equals("ACCEPTED")){

                            loadToast.success();
                            acceptButton.setEnabled(false);
                            acceptButton.setText("Accepted");
                            notifyDataSetChanged();
                        }else if(data.equals("DENIED")){
                            loadToast.success();
                            rejectButton.setEnabled(false);
                            rejectButton.setText("Rejected");
                            notifyDataSetChanged();
                        }
                    }
                    else {
                        loadToast.error();
                        Toast.makeText(c, "Not Reachable", Toast.LENGTH_SHORT).show();
                    }
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
                        loadToast.error();
                        Toast.makeText(c,"Not authenticated", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    loadToast.error();
                    Toast.makeText(c, "No Connection", Toast.LENGTH_SHORT).show();
                }
            }
        }
        ){
            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Authorization","token " + personalData.getToken());
                return map;
            }

        };
        queue.add(req);
    }

}


