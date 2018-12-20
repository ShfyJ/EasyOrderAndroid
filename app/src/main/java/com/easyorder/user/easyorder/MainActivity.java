package com.easyorder.user.easyorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinnerCountries);
        //Setting country phone codes
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        editText = findViewById(R.id.editTextPhone);


        //Getting phone number
        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                String number = editText.getText().toString().trim();

                //Checking phone number validation
                if (number.isEmpty() || number.length() < 8) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    Toast.makeText(MainActivity.this, "Please, enter your phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                //assigning phone number
                phoneNumber = "+" + code + number;

                //Sending for verification
                Intent intent = new Intent(MainActivity.this, PhoneAuthActivity.class);
                intent.putExtra("phonenumber", phoneNumber);//phonenumber

                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        Intent intent;
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){

            //Checking user role
            //if admin
           /* if(phoneNumber.equals("+998915652285")) {
                intent = new Intent(MainActivity.this, adminActivityMain.class);
            }
            //if simple user
            else{*/
                intent = new Intent(MainActivity.this, userActivityMain.class);
            //}
            //Setting flags
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }

    }

}

