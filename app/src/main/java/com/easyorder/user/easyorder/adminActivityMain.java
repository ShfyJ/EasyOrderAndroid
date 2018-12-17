package com.easyorder.user.easyorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class adminActivityMain extends AppCompatActivity {


    Button btn_admin_submit;
    EditText et_callback_message;
    TextView tv_recieved_order_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        btn_admin_submit = findViewById(R.id.btn_admin_submit);
        et_callback_message = findViewById(R.id.et_callback_message);
        tv_recieved_order_message = findViewById(R.id.tv_recieved_order_message);


//        Uncomment and use this to set ordered food information from user
//        tv_recieved_order_message.setText("");


        btn_admin_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str_order = et_callback_message.getText().toString();

                // some actions to submit
            }
        });


    }
}
