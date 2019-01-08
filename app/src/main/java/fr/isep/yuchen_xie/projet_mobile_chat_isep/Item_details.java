package fr.isep.yuchen_xie.projet_mobile_chat_isep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

public class Item_details extends AppCompatActivity {
    public String item_name, item_published_date, item_description_content, receiver_name, receiver_tel_content,
            receiver_email_content, receiver_address_content;
    public int item_view_num;
    public TextView textView_item_name, textView_item_published_date, textView_item_description_content, textView_receiver_name,
            textView_tel_content, textView_email_content, textView_address_content;
    public String name_selected="", photo_url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Intent intent0 = getIntent();
        name_selected = intent0.getStringExtra("name");
        photo_url = intent0.getStringExtra("photo");

        syn();

        textView_item_name = (TextView) findViewById(R.id.textView_item_name);
        textView_item_published_date = (TextView) findViewById(R.id.textView_item_published_date);
        textView_item_description_content = (TextView) findViewById(R.id.textView_item_description_content);
        textView_receiver_name = (TextView) findViewById(R.id.textView_receiver_name);
        textView_tel_content = (TextView) findViewById(R.id.textView_tel_content);
        textView_email_content = (TextView) findViewById(R.id.textView_email_content);
        textView_address_content = (TextView) findViewById(R.id.textView_address_content);

        textView_item_name.setText(item_name);
        textView_item_published_date.setText(item_published_date);
        textView_item_description_content.setText(item_description_content);
        textView_receiver_name.setText(receiver_name);
        textView_tel_content.setText(receiver_tel_content);
        textView_email_content.setText(receiver_email_content);
        textView_address_content.setText(receiver_address_content);

    }
    public void syn(){
        RequestQueue requestQueuereg = Volley.newRequestQueue(this);
        StringRequest requestreg = new StringRequest(Request.Method.GET, "http://10.0.2.2:3000/reservations/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonResponse = null;
                try {
                    jsonResponse = new JSONArray(response);

                    for(int i= 0;i<jsonResponse.length();i++) {
                        if(name_selected.equals(jsonResponse.getJSONObject(i).getString("name"))&&photo_url.equals(jsonResponse.getJSONObject(i).getString("photo"))){
                            item_name = jsonResponse.getJSONObject(i).getString("name");
                            item_published_date = jsonResponse.getJSONObject(i).getString("date_publication");
                            item_description_content = jsonResponse.getJSONObject(i).getString("description");
                            receiver_name = jsonResponse.getJSONObject(i).getString("frist_name") + " " + jsonResponse.getJSONObject(i).getString("last_name");
                            receiver_tel_content = jsonResponse.getJSONObject(i).getString("telephone");
                            receiver_email_content = jsonResponse.getJSONObject(i).getString("email");
                            receiver_address_content = jsonResponse.getJSONObject(i).getJSONObject("id_adress").getString("rue") + "," +
                                    jsonResponse.getJSONObject(i).getJSONObject("id_adress").getString("code_postal") + " " +
                                    jsonResponse.getJSONObject(i).getJSONObject("id_adress").getString("ville") + "," +
                                    jsonResponse.getJSONObject(i).getJSONObject("id_adress").getString("pays");
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

                requestQueuereg.add(requestreg);

    }

    public void button_receiver_acceptClick(View view) {
        RequestQueue requestQueuereg = Volley.newRequestQueue(this);
        StringRequest requestreg = new StringRequest(Request.Method.POST, "http://10.0.2.2:3000/reservations/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonResponse = null;
                try {
                    jsonResponse = new JSONArray(response);

                    for(int i= 0;i<jsonResponse.length();i++) {
                        if(name_selected.equals(jsonResponse.getJSONObject(i).getString("name"))&&photo_url.equals(jsonResponse.getJSONObject(i).getString("photo"))){
                            item_name = jsonResponse.getJSONObject(i).getString("name");
                            item_published_date = jsonResponse.getJSONObject(i).getString("date_publication");
                            item_description_content = jsonResponse.getJSONObject(i).getString("description");
                            receiver_name = jsonResponse.getJSONObject(i).getString("frist_name") + " " + jsonResponse.getJSONObject(i).getString("last_name");
                            receiver_tel_content = jsonResponse.getJSONObject(i).getString("telephone");
                            receiver_email_content = jsonResponse.getJSONObject(i).getString("email");
                            receiver_address_content = jsonResponse.getJSONObject(i).getJSONObject("id_adress").getString("rue") + "," +
                                    jsonResponse.getJSONObject(i).getJSONObject("id_adress").getString("code_postal") + " " +
                                    jsonResponse.getJSONObject(i).getJSONObject("id_adress").getString("ville") + "," +
                                    jsonResponse.getJSONObject(i).getJSONObject("id_adress").getString("pays");
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

        requestQueuereg.add(requestreg);
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }
}
