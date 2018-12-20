package com.easyorder.user.easyorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class PhoneAuthActivity extends AppCompatActivity {

    private static final String TAG = "PhoneAuthActivity";

  /*  private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
*/
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private ProgressBar mProgressBar;

    private EditText mVerificationField;

    private Button mSignInButton;
    private Button mResendButton;

    private String phonenumber;

    public PhoneAuthActivity() {
    }

    protected void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_auth_phone);

        //Restore intance state
        if (SavedInstanceState != null) {
            onRestoreInstanceState(SavedInstanceState);
        }

        //Assign views
        mVerificationField = findViewById(R.id.editTextCode);

        mSignInButton = findViewById(R.id.buttonSignIn);
        mResendButton = findViewById(R.id.buttonResend);

        mProgressBar =findViewById(R.id.progressbar);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        phonenumber = getIntent().getStringExtra("phonenumber");
        startPhoneNumberVerification(phonenumber);

        findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = mVerificationField.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    mVerificationField.setError("Enter code...");
                    mVerificationField.requestFocus();
                    return;
                }
                verifyPhoneNumberWithCode(code);
            }
        });

        findViewById(R.id.buttonResend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode(phonenumber,mResendToken);

            }
        });

    }

        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    mVerificationField.setText(code);
                    verifyPhoneNumberWithCode(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                Toast.makeText(PhoneAuthActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                Log.w(TAG, "onVerificationFailed", e);
            }


            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                mResendToken = token;
                // Save verification ID and resending token so we can use them later
                super.onCodeSent(verificationId, token);
                mVerificationId = verificationId;

            }
        };
        // [END phone_auth_callbacks]


    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        mProgressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
        // [END verify_with_code]
        signInWithCredential(credential);
    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        mProgressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent;
                            //Checking user role
                            //if admin
                            if(phonenumber.equals("+998915652285")) {
                                Toast.makeText(PhoneAuthActivity.this, "You logged in as an Admin, Doston", Toast.LENGTH_SHORT).show();
                                intent = new Intent(PhoneAuthActivity.this, adminActivityMain.class);
                            }
                            //if simple user
                            else {
                                Toast.makeText(PhoneAuthActivity.this, "Successfully signed in", Toast.LENGTH_SHORT).show();
                                intent = new Intent(PhoneAuthActivity.this, userActivityMain.class);
                            }
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        } else {
                            Toast.makeText(PhoneAuthActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }





}
