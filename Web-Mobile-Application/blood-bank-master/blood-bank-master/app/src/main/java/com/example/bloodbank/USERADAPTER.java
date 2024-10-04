package com.example.bloodbank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.transition.Hold;

public class USERADAPTER extends FirestoreRecyclerAdapter <USER,USERADAPTER.userholder>{


    public USERADAPTER(@NonNull FirestoreRecyclerOptions<USER> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull userholder holder, int position, @NonNull USER model) {
        holder.namee.setText(model.getName());
        holder.lastdate.setText(model.getLAST_DATE_OF_DONATION());
        holder.lastdate.setText(model.getLAST_DATE_OF_DONATION());
        holder.city.setText(model.getCity());
        holder.bloodgroup.setText(model.getBlood_Group());



    }

    @NonNull
    @Override
    public userholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);

        return new userholder(v);
    }

    class userholder extends RecyclerView.ViewHolder{

    TextView namee,lastdate,contact,city,bloodgroup;
        public userholder(@NonNull View itemView) {
            super(itemView);
            namee=itemView.findViewById(R.id.nameeeeee);
            contact=itemView.findViewById(R.id.nameeeeee2);
            lastdate=itemView.findViewById(R.id.textView26);
            city=itemView.findViewById(R.id.nameeeeee3);
            bloodgroup=itemView.findViewById(R.id.bloodgrupp);



        }
    }
}
