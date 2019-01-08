package fr.isep.yuchen_xie.projet_mobile_chat_isep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {
     Button BtnSI0,BtnSU0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BtnSI0 = (Button) findViewById(R.id.BtnSI0);
        BtnSU0 = (Button) findViewById(R.id.BtnSU0);

        BtnSI0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,NotificationActivity.class);
                startActivity(intent);

            }
        });

        BtnSU0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,Sign_up.class);
                startActivity(intent);
            }
        });
    }
}
