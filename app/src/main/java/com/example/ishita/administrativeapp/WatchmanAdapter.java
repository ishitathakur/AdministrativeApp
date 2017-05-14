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

/**
 * Created by ishita on 13/5/17.
 */
public class WatchmanAdapter extends BaseAdapter{
    Context c;
    ArrayList<Application> items = new ArrayList<>();
    PersonalData  personalData;
    LoadToast loadToast;
    private String URL=null;

    public WatchmanAdapter(Context c, ArrayList<Application> items,LoadToast toast, String url) {
        this.c = c;
        this.items = items;
        this.personalData = new PersonalData(c);
        this.loadToast = toast;
        this.URL = url;
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
            row = inflater.inflate(R.layout.application_item_watchman, parent, false);
        } else{
            row = convertView;
        }
        TextView first_name = (TextView) row.findViewById(R.id.first_name);
        TextView last_name = (TextView) row.findViewById(R.id.last_name);
        TextView roll_no = (TextView) row.findViewById(R.id.roll_no);
        TextView id = (TextView) row.findViewById(R.id.item_id);
        final Button out=(Button)row.findViewById(R.id.out);
        final Button in=(Button)row.findViewById(R.id.in);

        id.setText("" + items.get(position).getId());
        first_name.setText("" + items.get(position).getFirstName());
        last_name.setText(" " + items.get(position).getLastName());
        roll_no.setText(" " + items.get(position).getRollNo());


        if(items.get(position).getStatus().equals("2")){
            in.setEnabled(false);
            in.setText("Outside");
        }

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

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestTheServer(position,in,out,"0");

            }
        });

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {requestTheServer(position,in,out,"2");
            }});

        return row;
    }

    /**
     * This function will request the server with the accept or reject response
     * @param position
     * @param inButton
     * @param outButton
     * @param status
     */
    private void requestTheServer(int position, final Button inButton, final Button outButton, String status){
        Application bean  = items.get(position);
        loadToast.show();
        String uid = bean.getId();

        HashMap<String, String> param = new HashMap<String, String>();

        param.put("uid", uid);
        param.put("status", status);

        JSONObject obj = new JSONObject(param);
        RequestQueue queue = Volley.newRequestQueue(c);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT,URL,obj,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    Log.d("Check",jsonObject.toString());
                    String status = jsonObject.getString("status");
                    if (status.equals("UPDATED")) {
                        String data = jsonObject.getString("data");
                        if(data.equals("Status = 2")){
                            loadToast.success();
                            outButton.setEnabled(false);
                            outButton.setText("Outside");
                            notifyDataSetChanged();
                        }else if(data.equals("Status = 3")){
                            loadToast.success();
                            inButton.setEnabled(false);
                            inButton.setText("Inside");
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




