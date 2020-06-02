package com.numan.testfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ViewActivity extends AppCompatActivity {
    ImageView imageView;
    TextView Nametxt,Emailtxt;
    Button BtnDelete;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        imageView = findViewById(R.id.singleimageviewVA);
        Nametxt = findViewById(R.id.txtNameVA);
        Emailtxt = findViewById(R.id.txtEmailVA);
        BtnDelete = findViewById(R.id.ButtonDelete);

        ref = FirebaseDatabase.getInstance().getReference().child("Car");
        String CarKye = getIntent().getStringExtra("CarKye");
        ref.child(CarKye).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String Email = dataSnapshot.child("Email").getValue().toString();
                    String Name = dataSnapshot.child("Name").getValue().toString();
                    String imageUri = dataSnapshot.child("imageUri").getValue().toString();
                    Picasso.get().load(imageUri).into(imageView);
                    Nametxt.setText(Name);
                    Emailtxt.setText(Email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
