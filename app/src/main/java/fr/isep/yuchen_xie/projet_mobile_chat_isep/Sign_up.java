package fr.isep.yuchen_xie.projet_mobile_chat_isep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Sign_up extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        final EditText FirstName = (EditText)findViewById(R.id.FirstName);
        final EditText LasstName = (EditText)findViewById(R.id.LastName);
        final EditText Email = (EditText)findViewById(R.id.Email);
        final EditText Password = (EditText)findViewById(R.id.Password);
        final EditText Phonenumber= (EditText)findViewById(R.id.PhoneNumber);
        final Button BtnSU = (Button) findViewById(R.id.BtnSU);
    }
}
