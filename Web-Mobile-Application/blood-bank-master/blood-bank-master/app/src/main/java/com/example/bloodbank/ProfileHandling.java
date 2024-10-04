package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileHandling extends AppCompatActivity {
    ProgressBar progressBar3;
    String selectedgroupa;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    private TextView textView25;
    EditText Username,Userphonenumber;
    Button Submit;

    String userid=firebaseUser.getUid();




      private Spinner spinner;
      ArrayAdapter <CharSequence> groupspinner;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_handling);
        progressBar3=(ProgressBar) findViewById(R.id.progressBar3);
        textView25=(TextView) findViewById(R.id.textView25);
        Username=(EditText) findViewById(R.id.username);
        Submit=(Button) findViewById(R.id.Submit);
        Userphonenumber=(EditText) findViewById(R.id.userphonenumber);
        Submit.setOnClickListener(v -> profiledata());


        spinner=(Spinner) findViewById(R.id.spinner);
         groupspinner=ArrayAdapter.createFromResource(this,R.array.BlOOD_GROUPS,R.layout.spinner_layout);
        groupspinner.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(groupspinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            selectedgroupa=spinner.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





    }

    private void profiledata() {
        Submit.setEnabled(false);
        progressBar3.setVisibility(View.VISIBLE);
        String name=Username.getText().toString().trim();
        String phonenumber=Userphonenumber.getText().toString().trim();

        if (TextUtils.isEmpty(name)|| TextUtils.isEmpty(phonenumber) ){
            Toast.makeText(this, "Complete all the Credientails", Toast.LENGTH_SHORT).show();
            Submit.setEnabled(true);
            progressBar3.setVisibility(View.GONE);
        }
        else if (selectedgroupa.equals("Select")){
            textView25.setError("");
            Submit.setEnabled(true);
            progressBar3.setVisibility(View.GONE);

        }
        else {
            Map<String,String> profile=new HashMap<>();
            profile.put("Name",name);
            profile.put("Phone_Number",phonenumber);
            profile.put("Blood_Group",selectedgroupa);
            profile.put("City","NULL");
            profile.put("LAST_DATE_OF_DONATION","NULL");
            profile.put("Donar","NO");



            db.collection("USERS").document(userid).set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(ProfileHandling.this, "Profile Completed", Toast.LENGTH_SHORT).show();
                    progressBar3.setVisibility(View.GONE);
                    startActivity(new Intent(ProfileHandling.this,dashboardactivity.class));
                    finish();



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ProfileHandling.this, "Something Went Wrong\nCheck Network ", Toast.LENGTH_SHORT).show();
                    Submit.setEnabled(true);
                    progressBar3.setVisibility(View.GONE);
                }
            });






        }


    }

}