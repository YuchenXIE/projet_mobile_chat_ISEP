package fr.isep.yuchen_xie.projet_mobile_chat_isep;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

/*
*
*
* @Mdalel_ahmed
*
* ISEP
* 2019
*
*
*/

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int NUM_COLUMNS = 2;

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList <String> mStatus = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(checkConnection()) {
            RequestQueue requestQueuereg = Volley.newRequestQueue(this);
            StringRequest requestreg = new StringRequest(Request.Method.GET, "http://10.0.2.2:3000/annonces/", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONArray jsonResponse = null;
                    try {
                        jsonResponse = new JSONArray(response);
                       // mNames.add(jsonResponse.getString("name"));
                       // mDescription.add(jsonResponse.getString("description"));
                        for(int i= 0;i<jsonResponse.length();i++) {
                      //      System.out.println("aaaa" + jsonResponse.getJSONObject(i).getString("description"));
                            mNames.add(jsonResponse.getJSONObject(i).getString("name"));
                            mStatus.add(jsonResponse.getJSONObject(i).getString("status"));
                            mImageUrls.add(jsonResponse.getJSONObject(i).getString("photo"));
                        }
                        initImageBitmaps();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("err", error.toString());
                }
            });

            requestQueuereg.add(requestreg);
        }
    }

    private void initImageBitmaps(){

        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: initializing staggered recyclerview.");

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        StaggeredRecyclerViewAdapter staggeredRecyclerViewAdapter =
                new StaggeredRecyclerViewAdapter(this, mNames, mImageUrls,mStatus);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(NUM_COLUMNS, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(staggeredRecyclerViewAdapter);
    }
    public boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else
            return false;
    }

}

