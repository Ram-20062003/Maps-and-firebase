package com.example.another;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register_user extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    private static final String TAG = "Register_user";
    EditText e_email,e_pass,e_phone,e_confo,e_name;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;
    String id;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        e_name=findViewById(R.id.reg_name);
        e_email=(EditText)findViewById(R.id.reg_email);
        e_pass=(EditText)findViewById(R.id.reg_pass);
        e_phone=(EditText)findViewById(R.id.reg_phone);
        e_confo=(EditText)findViewById(R.id.reg_confirm);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        findViewById(R.id.reg_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sign=new Intent(Register_user.this,Log_user.class);
                startActivity(intent_sign);
            }
        });
        findViewById(R.id.reg_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check_fields();
            }
        });
    }

    private void check_fields() {
        int check=0;
        boolean pass_check=false;
        String name,email,pass,confo_pass,phone;
        name=e_name.getText().toString().trim();
        email=e_email.getText().toString().trim();
        pass=e_pass.getText().toString();
        confo_pass=e_confo.getText().toString();
        phone=e_phone.getText().toString().trim();
        if(name.length()==0||name.isEmpty())
            check++;
        if(email.length()==0||email.isEmpty())
            check++;
        if(pass.length()==0||pass.isEmpty())
            check++;
        if(phone.length()==0||phone.isEmpty())
            check++;
        if(confo_pass.equals(pass))
            pass_check=true;
        if(check>0)
            Toast.makeText(getApplicationContext(),"Check your details",Toast.LENGTH_SHORT).show();
        if(pass_check&&check==0)
            register_user();
        if(pass_check==false)
            Toast.makeText(getApplicationContext(),"Check you password",Toast.LENGTH_SHORT).show();
    }

    public void register_user() {
        String email,pass,phone;
        email=e_email.getText().toString().trim();
        pass=e_pass.getText().toString().trim();
        phone=e_phone.getText().toString().trim();
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    firebaseUser=firebaseAuth.getCurrentUser();
                    id=firebaseUser.getUid();
                    DocumentReference documentReference=firebaseFirestore.collection("user").document(id);
                    Map<String,Object> map=new HashMap<>();
                    map.put("email",email);
                    map.put("phone",phone);
                    documentReference.set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: "+id);
                        }
                    });
                    Toast.makeText(Register_user.this, "Account created"+firebaseUser.getEmail().toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Register_user.this, "Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent_sign=new Intent(Register_user.this,Log_user.class);
        startActivity(intent_sign);
    }
}
