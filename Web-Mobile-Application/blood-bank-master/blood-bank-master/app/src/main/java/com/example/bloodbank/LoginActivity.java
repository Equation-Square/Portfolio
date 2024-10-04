package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
       private   Button registershiftbtn,loginbutton;
        private EditText loginemail,loginpassword;
        private FirebaseAuth mAuth;
        ProgressBar progressBar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar2=(ProgressBar) findViewById(R.id.progressBar2);
        registershiftbtn=(Button) findViewById(R.id.registershiftbtn);
        loginbutton=(Button) findViewById(R.id.loginbtn);
        loginemail=(EditText) findViewById(R.id.loginemail);
        loginpassword=(EditText)  findViewById(R.id.loginpassword);
        loginbutton.setOnClickListener(V-> loginuseer());
        mAuth=FirebaseAuth.getInstance();
        registershiftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,registeractivity.class));
            }
        });
    }
    private  void loginuseer(){
        progressBar2.setVisibility(View.VISIBLE);
        loginbutton.setEnabled(false);
        String email= loginemail.getText().toString().trim();
        String password=loginpassword.getText().toString().trim();
        if (email.isEmpty() )
        {
            progressBar2.setVisibility(View.GONE);
            loginbutton.setEnabled(true);
            loginemail.setError("Enter Email");
        }
        else if( password.isEmpty())
        {
            progressBar2.setVisibility(View.GONE);
            loginbutton.setEnabled(true);
            loginpassword.setError("Enter password");
        }
        else {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressBar2.setVisibility(View.GONE);

                        Toast.makeText(LoginActivity.this, "Login Confirmed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,dashboardactivity.class));
                        finish();

                    }else {
                        progressBar2.setVisibility(View.GONE);
                        loginbutton.setEnabled(true);
                        Toast.makeText(LoginActivity.this, "Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }



    }
}