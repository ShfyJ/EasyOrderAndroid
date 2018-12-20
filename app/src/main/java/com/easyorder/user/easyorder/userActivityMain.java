package com.easyorder.user.easyorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class userActivityMain extends AppCompatActivity {

    Button btn_user_submit;
    Button btn_user_signout;
    EditText et_order;
    TextView tv_order_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        btn_user_submit = findViewById(R.id.btn_user_submit);
        btn_user_signout = findViewById(R.id.btn_signout);
        et_order= findViewById(R.id.et_order);
        tv_order_status = findViewById(R.id.tv_order_status);


//        Uncomment and use this to set callback messages from admin
//        tv_order_status.setText("");


        btn_user_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_order = et_order.getText().toString();

                // some actions to submit

            }
        });

        btn_user_signout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){

                    signOut();
            }

        });


    }
        public void signOut() {
            // [START auth_sign_out]
            FirebaseAuth.getInstance().signOut();
            // [END auth_sign_out]
            Intent intent = new Intent(userActivityMain.this, MainActivity.class);
            startActivity(intent);
        }
}
