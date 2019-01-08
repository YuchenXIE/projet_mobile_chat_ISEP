package fr.isep.yuchen_xie.projet_mobile_chat_isep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DateFormat;
import java.util.Date;

public class ItemCreation extends Activity {

    public EditText editText_item_name, editText_item_description;
    public String item_name, item_description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_creation);

        editText_item_name = findViewById(R.id.editText_item_name);
        editText_item_description = findViewById(R.id.editText_item_description);
    }

    public void button_submitClick(View view) {
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        String url = "http://10.0.2.2:3000/annonces?date_publication=" + currentDateTimeString + "&&status=created&&categorie=item&&name=" + item_name +
                "&&description=" +item_description + "&&phtot=photo_url&&id_user_pub=3";


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
}
