package com.example.another;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import javax.security.auth.callback.Callback;

public class otpactivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    String verify;
    EditText editText;
    private static final String TAG = "otpactivity";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Intent intent=getIntent();
        firebaseAuth=FirebaseAuth.getInstance();
        String num=intent.getStringExtra("mobile");
        editText=(EditText)findViewById(R.id.editTextTextPersonName);
        PhoneAuthProvider.OnVerificationStateChangedCallbacks

                // initializing our callbacks for on
                // verification callback method.
                mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // below method is used when
            // OTP is sent from Firebase
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(otpactivity.this, forceResendingToken.toString(), Toast.LENGTH_LONG).show();
                verify=s;
                Log.d(TAG,s);
                // when we receive the OTP it
                // contains a unique id which
                // we are storing in our string
                // which we have already created.
            }

            // this method is called when user
            // receive OTP from Firebase.
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                // below line is used for getting OTP code
                // which is sent in phone auth credentials.
                final String code = phoneAuthCredential.getSmsCode();
                Log.d(TAG,code);
                // checking if the code
                // is null or not.
                if (code != null) {
                    // if the code is not null then
                    // we are setting that code to
                    // our OTP edittext field.


                    // after setting this code
                    // to OTP edittext field we
                    // are calling our verifycode method.

                }
            }

            // this method is called when firebase doesn't
            // sends our OTP code due to any error or issue.
            @Override
            public void onVerificationFailed(FirebaseException e) {
                // displaying error message with firebase exception.
                Toast.makeText(otpactivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
       // FirebaseAuth.getInstance().getFirebaseAuthSettings().forceRecaptchaFlowForTesting(true);

        findViewById(R.id.get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(firebaseAuth)
                                .setPhoneNumber(firebaseAuth.getCurrentUser().getPhoneNumber())            // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(otpactivity.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallBack)           // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });
        findViewById(R.id.Verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edit=editText.getText().toString().trim();
                PhoneAuthCredential phoneAuthCredential=PhoneAuthProvider.getCredential(verify,edit);firebaseAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(false);

                firebaseAuth. signInWithCredential(phoneAuthCredential).addOnCompleteListener(otpactivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(otpactivity.this, "Done", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(otpactivity.this, "Not done", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
