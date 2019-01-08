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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

public class Item_reservation extends AppCompatActivity {
    public String item_name, item_published_date, item_description_content, donator_name, donator_tel_content,
            donator_email_content, donator_address_content;
    public int item_view_num;
    public TextView textView_item_name, textView_item_published_date, textView_item_description_content, textView_donator_name,
            textView_tel_content, textView_email_content, textView_address_content;
    public String name_selected="", photo_url="";

    public String id_user, id_annonce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_reservation);

        Intent intent0 = getIntent();
        name_selected = intent0.getStringExtra("name");
        photo_url = intent0.getStringExtra("photo");



        textView_item_name = findViewById(R.id.textView_item_name);
        textView_item_published_date = findViewById(R.id.textView_item_published_date);
        textView_item_description_content = findViewById(R.id.textView_item_description_content);
        textView_donator_name = findViewById(R.id.textView_donator_name);
        textView_tel_content = findViewById(R.id.textView_tel_content);
        textView_email_content = findViewById(R.id.textView_email_content);
        textView_address_content = findViewById(R.id.textView_address_content);

        syn();

    }

    public void syn(){

        RequestQueue requestQueuereg = Volley.newRequestQueue(this);
        StringRequest requestreg = new StringRequest(Request.Method.GET, "http://10.0.2.2:3000/annonces/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonResponse = null;
                try {
                    jsonResponse = new JSONArray(response);

                    for(int i= 0;i<jsonResponse.length();i++) {
                        if(name_selected.equals(jsonResponse.getJSONObject(i).getString("name"))&&photo_url.equals(jsonResponse.getJSONObject(i).getString("photo"))){
                            setId_user(jsonResponse.getJSONObject(i).getString("id_user_pub"));
                            setId_annonce(jsonResponse.getJSONObject(i).getString("id_annonce"));

                            item_name = jsonResponse.getJSONObject(i).getString("name");
                            textView_item_name.setText(item_name);

                            item_published_date = jsonResponse.getJSONObject(i).getString("date_publication");
                            textView_item_published_date.setText(item_published_date);

                            item_description_content = jsonResponse.getJSONObject(i).getString("description");
                            textView_item_description_content.setText(item_description_content);

                            donator_name = jsonResponse.getJSONObject(i).getString("frist_name") + " " + jsonResponse.getJSONObject(i).getString("last_name");
                            textView_donator_name.setText(donator_name);

                            donator_tel_content = jsonResponse.getJSONObject(i).getString("telephone");
                            textView_tel_content.setText(donator_tel_content);

                            donator_email_content = jsonResponse.getJSONObject(i).getString("email");
                            textView_email_content.setText(donator_email_content);

                            donator_address_content = jsonResponse.getJSONObject(i).getJSONObject("id_adress").getString("rue") + "," +
                                    jsonResponse.getJSONObject(i).getJSONObject("id_adress").getString("code_postal") + " " +
                                    jsonResponse.getJSONObject(i).getJSONObject("id_adress").getString("ville") + "," +
                                    jsonResponse.getJSONObject(i).getJSONObject("id_adress").getString("pays");

                            textView_address_content.setText(donator_address_content);
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

    public void button_reservationClick(View view) {
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        String url = "http://10.0.2.2:3000/reservations?date_reservation=" + currentDateTimeString + "&&id_user=" +getId_user() + "&&id_annonce=" + getId_annonce();

        StringRequest requestreg = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONArray jsonResponse = null;
                try {
                    jsonResponse = new JSONArray(response);

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
        /*
        change item parametre : reserved = 1, receiverID = ?


        */
        //go to the NotificationActivity
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }

    public String getId_user() {return id_user;}

    public String getId_annonce() {return id_annonce;}

    public void setId_user( String id ){
        this.id_user = id;
    }

    public void setId_annonce( String id ) {
        this.id_annonce = id;
    }


}
