package fr.isep.yuchen_xie.projet_mobile_chat_isep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Item_reservation extends AppCompatActivity {
    public String item_name, item_published_date, item_description_content, donator_name, donator_tel_content,
            donator_email_content, donator_address_content;
    public int item_view_num;
    public TextView textView_item_name, textView_item_published_date, textView_item_description_content, textView_donator_name,
            textView_tel_content, textView_email_content, textView_address_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_reservation);
        item_name = "Apple";
        item_published_date = "01/01/2019";
        item_description_content = "This is an apple.";
        donator_name = "Yuchen";
        donator_tel_content = "0712345678";
        donator_email_content = "email_receiver@email.com";
        donator_address_content = "88 Rue de Apple, 75000 Paris";

        textView_item_name = (TextView) findViewById(R.id.textView_item_name);
        textView_item_published_date = (TextView) findViewById(R.id.textView_item_published_date);
        textView_item_description_content = (TextView) findViewById(R.id.textView_item_description_content);
        textView_donator_name = (TextView) findViewById(R.id.textView_receiver_name);
        textView_tel_content = (TextView) findViewById(R.id.textView_tel_content);
        textView_email_content = (TextView) findViewById(R.id.textView_email_content);
        textView_address_content = (TextView) findViewById(R.id.textView_address_content);

        textView_item_name.setText(item_name);
        textView_item_published_date.setText(item_published_date);
        textView_item_description_content.setText(item_description_content);
        textView_donator_name.setText(donator_name);
        textView_tel_content.setText(donator_tel_content);
        textView_email_content.setText(donator_email_content);
        textView_address_content.setText(donator_address_content);
    }

    public void button_reservationClick(View view) {
        /*
        change item parametre : reserved = 1, receiverID = ?


        */
        //go to the NotificationActivity
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }
}
