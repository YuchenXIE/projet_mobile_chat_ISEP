package fr.isep.yuchen_xie.projet_mobile_chat_isep;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
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

public class NotificationActivity extends AppCompatActivity {
    private NotificationDataAdapter mAdapter;
    SwipeController swipeController = null;

    SwipeController swipeController2 = null;
    private String id_annonce,id_user;

    public String getId_annonce() {
        return id_annonce;
    }

    public void setId_annonce(String id_annonce) {
        this.id_annonce = id_annonce;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setNotificationDataAdapter();
        setupRecyclerView();
    }
    private void setNotificationDataAdapter() {
        List<Notification> notificationList = new ArrayList<>();
        if(checkConnection()) {
            RequestQueue requestQueuereg = Volley.newRequestQueue(this);

            StringRequest requestreg = new StringRequest(Request.Method.GET, "http://10.0.2.2:3000/reservations/", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONArray jsonResponse = null;
                    try {
                        RequestQueue requestQueuereg2 = Volley.newRequestQueue(NotificationActivity.this);

                        jsonResponse = new JSONArray(response);
                        // mNames.add(jsonResponse.getString("name"));
                        // mDescription.add(jsonResponse.getString("description"));
                        for(int i= 0;i<jsonResponse.length();i++) {
                                    setId_annonce(jsonResponse.getJSONObject(i).getString("id_annonce"));
                                StringRequest requestreg2 = new StringRequest(Request.Method.GET, "http://10.0.2.2:3000/annonces/", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        JSONArray jsonResponse2 = null;

                                        try {
                                            jsonResponse2 = new JSONArray(response);
                                            // mNames.add(jsonResponse.getString("name"));
                                            // mDescription.add(jsonResponse.getString("description"));
                                            for(int i= 0;i<jsonResponse2.length();i++) {
                                                  if(getId_annonce().equals(jsonResponse2.getJSONObject(i).getString("id_annonce"))){
                                                      System.out.println("tres bien");
                                                  }

                                            }
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

                                requestQueuereg2.add(requestreg2);
                            }


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
        Notification notification = new Notification(getId_annonce(),"ahmed","refuse","07775000","hellouser");
        notificationList.add(notification);
        mAdapter = new NotificationDataAdapter(notificationList);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerViewnotification);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

     swipeController = new SwipeController(new SwipeControllerActions() {
         @Override
         public void onLeftClicked(int position) {
             Log.i("Send email", "");

             String[] TO = {"someone@gmail.com"};
             String[] CC = {"xyz@gmail.com"};
             Intent emailIntent = new Intent(Intent.ACTION_SEND);
             emailIntent.setData(Uri.parse("mailto:"));
             emailIntent.setType("text/plain");


             emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
             emailIntent.putExtra(Intent.EXTRA_CC, CC);
             emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
             emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

             try {
                 startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                 finish();
                 Log.i("Finished sending email...", "");
             } catch (android.content.ActivityNotFoundException ex) {
                 Toast.makeText(NotificationActivity.this,
                         "There is no email client installed.", Toast.LENGTH_SHORT).show();
             }
         }

         @Override
         public void onRightClicked(int position) {
             mAdapter.Notifications.remove(position);
             mAdapter.notifyItemRemoved(position);
             System.out.println("ok");
             mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
         }
     });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                swipeController.onDraw(c);
            }
        });



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
