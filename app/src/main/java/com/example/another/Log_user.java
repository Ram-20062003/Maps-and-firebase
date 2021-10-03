package com.example.another;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Log_user extends AppCompatActivity {
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    EditText e_email,e_pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        e_email=(EditText)findViewById(R.id.log_name);
        e_pass=(EditText)findViewById(R.id.log_pass);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        Toast.makeText(Log_user.this, "yes", Toast.LENGTH_SHORT).show();
        if(firebaseUser!=null)
        {
            Toast.makeText(Log_user.this, "yes", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Log_user.this,MapsActivity.class);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(Log_user.this, "no", Toast.LENGTH_SHORT).show();
        }
            findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email,pass;
                    email=e_email.getText().toString().trim();
                    pass=e_pass.getText().toString().trim();
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(Log_user.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Intent intent=new Intent(Log_user.this,MapsActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Log_user.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    }
            });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_reg=new Intent(Log_user.this,Register_user.class);
                startActivity(intent_reg);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}

