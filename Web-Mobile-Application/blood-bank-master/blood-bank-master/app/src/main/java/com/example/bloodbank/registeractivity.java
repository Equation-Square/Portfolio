package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class registeractivity extends AppCompatActivity {


    Button loginshiftbtn,register;

    EditText registeremail,registerpassword,confirmpassworde;
     FirebaseAuth mAuth;
     ConstraintLayout aaa;
     ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);
        progressBar=findViewById(R.id.progressBar);
        aaa=(ConstraintLayout) findViewById(R.id.aaa);
        loginshiftbtn=(Button) findViewById(R.id.loginshiftbtn);
        register=(Button) findViewById(R.id.register);
        registeremail=(EditText) findViewById(R.id.registeremail);
        registerpassword=(EditText) findViewById(R.id.registerpassword);
        confirmpassworde=(EditText) findViewById(R.id.confrimpassworde);
        mAuth=FirebaseAuth.getInstance();



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jandooo();

            }


        });

        loginshiftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registeractivity.this,LoginActivity.class));
                finish();
            }
        });
    }
    private void jandooo() {
        register.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);


        String email=registeremail.getText().toString().trim();
        String password =registerpassword.getText().toString().trim();
        String check=confirmpassworde.getText().toString().trim();
        if ( password.isEmpty()  ){
            progressBar.setVisibility(View.GONE);
            register.setEnabled(true);
            Toast.makeText(registeractivity.this, "Empty Credientails", Toast.LENGTH_SHORT).show();
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            progressBar.setVisibility(View.GONE);
            register.setEnabled(true);
            Toast.makeText(registeractivity.this, "Enter Valid Email Address", Toast.LENGTH_SHORT).show();
        }
        else if (password.length()<9){
            progressBar.setVisibility(View.GONE);
            register.setEnabled(true);
            Toast.makeText(registeractivity.this, "password too short", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(check)){
            progressBar.setVisibility(View.GONE);
            register.setEnabled(true);

            Toast.makeText(registeractivity.this, "Passoword not matched", Toast.LENGTH_SHORT).show();
        }
        else {

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressBar.setVisibility(View.GONE);
                        register.setEnabled(true);
                        Toast.makeText(registeractivity.this, "Done", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(registeractivity.this,LoginActivity.class));
                        finish();


                    }
                    else {Toast.makeText(registeractivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        register.setEnabled(true);
                    }

                }
            });


        }


    }
}