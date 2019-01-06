package fr.isep.yuchen_xie.projet_mobile_chat_isep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ItemCreation extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_creation);
    }

    public void button_submitClick(View view) {
        //go to the NotificationActivity
        Intent intent = new Intent(this, NotificationActivity.class);
        startActivity(intent);
    }
}
