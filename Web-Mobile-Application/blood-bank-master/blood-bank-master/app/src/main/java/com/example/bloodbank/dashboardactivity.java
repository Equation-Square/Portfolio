package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class dashboardactivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 100;

    Button createprofile, logoutt, updateprofile;
    TextView name, bloodgroup, phonenumber, textView10, textView11, textView12, city;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    String namee, userid;
    ProgressBar progressBar4;
    ImageView imageView7;
    String Lastdate,send;
    CardView cardView4, cardView2, cardView3, cardView5, cardView6, cardView7, cardView8, cardView10, cardViewnipata;
    int a = 1, b = 2, c = 3, d = 4, e = 5, f = 6, g = 7, h = 8, i = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboardactivity);
        city = (TextView) findViewById(R.id.textView27);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        cardView2 = (CardView) findViewById(R.id.cardView2);
        cardView3 = (CardView) findViewById(R.id.cardView3);
        cardView4 = (CardView) findViewById(R.id.cardView4);
        cardView5 = (CardView) findViewById(R.id.cardView5);
        cardView6 = (CardView) findViewById(R.id.cardView6);
        cardView7 = (CardView) findViewById(R.id.cardView7);
        cardView8 = (CardView) findViewById(R.id.cardView8);
        cardView10 = (CardView) findViewById(R.id.cardView10);
        cardViewnipata = (CardView) findViewById(R.id.cardviewnipata);


        cardView2.setOnClickListener(v -> shift(a));
        cardView3.setOnClickListener(v -> shift(b));
        cardView4.setOnClickListener(v -> shift(c));
        cardView5.setOnClickListener(v -> shift(d));
        cardView6.setOnClickListener(v -> shift(e));
        cardView7.setOnClickListener(v -> shift(f));
        cardView8.setOnClickListener(v -> shift(g));
        cardView10.setOnClickListener(v -> shift(h));
        cardViewnipata.setOnClickListener(v -> shift(i));


        createprofile = (Button) findViewById(R.id.createbutton);
        progressBar4 = (ProgressBar) findViewById(R.id.progressBar4);
        updateprofile = (Button) findViewById(R.id.button7);
        logoutt = (Button) findViewById(R.id.logoutt);
        name = (TextView) findViewById(R.id.textname);
        bloodgroup = (TextView) findViewById(R.id.textbloodgroup);
        phonenumber = (TextView) findViewById(R.id.Textphone);
        textView10 = (TextView) findViewById(R.id.textView10);
        textView11 = (TextView) findViewById(R.id.textView11);
        textView12 = (TextView) findViewById(R.id.textView12);
        imageView7 = (ImageView) findViewById(R.id.imageView7);
        namee = name.getText().toString().trim();
        createprofile.setOnClickListener(v -> createprofile());
        logoutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(dashboardactivity.this, LoginActivity.class));

                finish();
            }
        });
        textView10.setOnClickListener(v -> donationactivity());
        imageView7.setOnClickListener(v -> donationactivity());


    }

    private void shift(int a) {

      progressBar4.setVisibility(View.VISIBLE);
        getLastLocation();

        Handler handler=new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar4.setVisibility(View.GONE);
                String test = String.valueOf(a);
                Intent intent = new Intent(getApplicationContext(), KHOOON.class);
                intent.putExtra("message_key", test);
                intent.putExtra("message_key2", send);

                startActivity(intent);
            }
        },5000);

    }

    private void getLastLocation() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(android.location.Location location) {
                    if (location != null) {

                        Geocoder geocoder = new Geocoder(dashboardactivity.this, Locale.getDefault());
                        List<Address> addresses = null;
                        try {

                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                            city.setText("" + addresses.get(0).getLocality());
                            send=city.getText().toString().trim();


                            ///for city getlocalcityname  getcountryname
                        } catch (IOException e) {
                            e.printStackTrace();


                        }


                    }
                }
            });
        } else {

            askpermission();
        }

    }

    private void askpermission() {

        ActivityCompat.requestPermissions(dashboardactivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== REQUEST_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }
            else {
                Toast.makeText(dashboardactivity.this, "Please provide the Requested Permission", Toast.LENGTH_SHORT).show();


            }
        }
    }


    private void donationactivity() {
        startActivity(new Intent(dashboardactivity.this, Donationacticity.class));
    }

    private void createprofile() {
        startActivity(new Intent(dashboardactivity.this, ProfileHandling.class));

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser == null) {

            startActivity(new Intent(dashboardactivity.this, Splashactivity.class));
            finish();
        } else {
            progressBar4.setVisibility(View.VISIBLE);
            userid = firebaseUser.getUid();
            db.collection("USERS").document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    if (documentSnapshot.exists()) {
                        progressBar4.setVisibility(View.GONE);
                        updateprofile.setVisibility(View.VISIBLE);
                        createprofile.setVisibility(View.GONE);

                        name.setText(documentSnapshot.getString("Name"));
                        phonenumber.setText(documentSnapshot.getString("Phone_Number"));
                        bloodgroup.setText(documentSnapshot.getString("Blood_Group"));
                        Lastdate = documentSnapshot.getString("LAST_DATE_OF_DONATION");
                        if (!Lastdate.equals("NULL")) {


                            textView12.setText(Lastdate);
                            textView10.setVisibility(View.GONE);
                            textView11.setVisibility(View.VISIBLE);
                            textView12.setVisibility(View.VISIBLE);


                        }


                    } else {
                        progressBar4.setVisibility(View.GONE);

                        updateprofile.setVisibility(View.GONE);
                        createprofile.setVisibility(View.VISIBLE);


                    }

                }
            });


        }

    }


}
