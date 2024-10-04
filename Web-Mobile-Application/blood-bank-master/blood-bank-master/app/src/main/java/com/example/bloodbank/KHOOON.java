package com.example.bloodbank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class KHOOON extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser firebaseUser = mAuth.getCurrentUser();
    private USERADAPTER useradapter;
    RecyclerView recyclerView;
    String bloodgroup;


    String cityyy;
    EditText edcity2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khooon);
        edcity2=(EditText) findViewById(R.id.edcity2);
        recyclerView=(RecyclerView) findViewById(R.id.reccccccc);




        Intent intent = getIntent();
        String str = intent.getStringExtra("message_key");
        cityyy = intent.getStringExtra("message_key2");
        edcity2.setText(cityyy);


      if (str.equals("1")){
          bloodgroup="A+";


      }
      else if (str.equals("2")){
          bloodgroup="AB+";

      }
      else if (str.equals("3")){
          bloodgroup="B-";


      }
      else if (str.equals("4")){
          bloodgroup="AB-";

      }
      else if (str.equals("5")){
          bloodgroup="B+";

      }
      else if (str.equals("6")){
          bloodgroup="A-";

      }
      else if (str.equals("7")){
          bloodgroup="O+";

      }
      else if (str.equals("8")){
          bloodgroup="Exchangleable";

      }
      else if (str.equals("9")){
          bloodgroup="O-";

      }
        setUprecyclerview();





    }



    private void setUprecyclerview() {

        Query query=db.collection("USERS").whereEqualTo("Donar","YES").whereEqualTo("Blood_Group",bloodgroup).whereEqualTo("City",cityyy);
        FirestoreRecyclerOptions<USER> options= new FirestoreRecyclerOptions.Builder<USER>()
                .setQuery(query,USER.class).build();
        useradapter= new USERADAPTER(options);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(useradapter);


    }



    @Override
    protected void onStart() {
        super.onStart();
        useradapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        useradapter.startListening();
    }
}