package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Donationacticity extends AppCompatActivity {
    EditText lastdateofdonation, edcity;
    Spinner cityspiner;
    Button donar;
    ArrayAdapter<CharSequence> cityAdatpter;
    String cityy;
    DatePickerDialog picker;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    String uid=firebaseUser.getUid();
    ProgressBar progressBar5;
    String testname,testphone,testbloodgroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donationacticity);

        lastdateofdonation = (EditText) findViewById(R.id.dateofd);
        edcity = (EditText) findViewById(R.id.edcity);
        cityspiner = (Spinner) findViewById(R.id.spinner2);
        donar = (Button) findViewById(R.id.donateeee);
        progressBar5=(ProgressBar) findViewById(R.id.progressBar5);


        //spinners
        cityAdatpter = ArrayAdapter.createFromResource(this, R.array.city, R.layout.spinner_layout);
        cityAdatpter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        cityspiner.setAdapter(cityAdatpter);
        cityspiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityy = cityspiner.getSelectedItem().toString();
                if (!cityy.equals("Choose Your City")) {
                    edcity.setText(cityy);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        lastdateofdonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar calendar = Calendar.getInstance();
                int DAY = calendar.get(Calendar.DAY_OF_MONTH);
                int MONTH = calendar.get(Calendar.MONTH);
                int YEAT = calendar.get(Calendar.YEAR);
                picker = new DatePickerDialog(Donationacticity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        lastdateofdonation.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, YEAT, MONTH, DAY);
                picker.getDatePicker().setMaxDate(System.currentTimeMillis());
                picker.show();


            }
        });

        donar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Donor();
            }
        });


    }

    public void Donor() {
        progressBar5.setVisibility(View.VISIBLE);
        donar.setEnabled(false);
        String testcity, testdata;
        testcity = edcity.getText()
                .toString().trim();
        testdata=lastdateofdonation.getText().toString().trim();


        if ( TextUtils.isEmpty(testcity)) {
            edcity.setError("Select Your City");
            progressBar5.setVisibility(View.GONE);
            donar.setEnabled(true);
        }
        else if (TextUtils.isEmpty(testdata)){
            edcity.setError("Select Date");
            progressBar5.setVisibility(View.GONE);
            donar.setEnabled(true);
        }
        else {


            uid=firebaseUser.getUid();
            db.collection("USERS").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    testname=documentSnapshot.getString("Name");
                    testphone=documentSnapshot.getString("Phone_Number");
                    testbloodgroup=documentSnapshot.getString("Blood_Group");
                    Map<String,String> profile=new HashMap<>();
                    profile.put("Name",testname);
                    profile.put("Phone_Number",testphone);
                    profile.put("Blood_Group",testbloodgroup);
                    profile.put("City",testcity);
                    profile.put("LAST_DATE_OF_DONATION",testdata);
                    profile.put("Donar","YES");

                    db.collection("USERS").document(uid).set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressBar5.setVisibility(View.GONE);
                            donar.setEnabled(true);
                            Toast.makeText(Donationacticity.this, "Done", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Donationacticity.this,dashboardactivity.class));
                            finish();




                        }
                    });



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Donationacticity.this, "Somnething went wrong", Toast.LENGTH_SHORT).show();

                }
            });



        }


    }
}