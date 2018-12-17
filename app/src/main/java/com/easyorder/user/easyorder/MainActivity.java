package com.easyorder.user.easyorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btn_login;
    EditText et_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = findViewById(R.id.btn_login);
        et_phone = findViewById(R.id.et_phone);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = et_phone.getText().toString();

                if(phone.isEmpty() || phone.length()!=13)
                {
                    Toast.makeText(MainActivity.this, "Please, enter your phone number", Toast.LENGTH_SHORT).show();
                }
                else if(phone.equals("+998915652285"))
                {
                    Toast.makeText(MainActivity.this, "You logged in as a user, Doston", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, userActivityMain.class);
                    startActivity(intent);
                }
                else if(phone.equals("+998973033264"))
                {
                    Toast.makeText(MainActivity.this, "You logged in as a Admin, Jaloliddin", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, adminActivityMain.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Error in input", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}
