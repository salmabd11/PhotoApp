package com.numan.testfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;


public class HomeActivity extends AppCompatActivity  {
    RecyclerView recyclerView;
    DatabaseReference myRef;
    private ArrayList<Car> carArrayList;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.RecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);



        //Firebase
        myRef = FirebaseDatabase.getInstance().getReference();
        //Clear List
        ClearAll();
        carArrayList = new ArrayList<>();
        //Get deta method
        GetDetaFromFirebase();

    }

    private void GetDetaFromFirebase() {
        Query query = myRef.child("Car");
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Car car = new Car();
                    car.setImageUri(snapshot.child("imageUri").getValue().toString());
                    car.setName(snapshot.child("Name").getValue().toString());
                    car.setEmail(snapshot.child("Email").getValue().toString());
                    carArrayList.add(car);
                }
                recyclerAdapter = new RecyclerAdapter(getApplicationContext(),carArrayList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //in case of error
                Toast.makeText(HomeActivity.this, ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }

        });



    }
    private void ClearAll(){
        if (carArrayList !=null){
            carArrayList.clear();
            if (recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }
        carArrayList = new ArrayList<>();
    }



}

/*
@Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeActivity.this,ViewActivity.class);
                intent.putExtra("CarKye",getRef(position).getKey());
                startActivity(intent);


public class HomeActivity extends AppCompatActivity {
    EditText inputTxtSearch;
    RecyclerView recyclerView;
    DatabaseReference DataRef;
    FirebaseRecyclerOptions<Car>options;
    FirebaseRecyclerAdapter<Car,MyViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        inputTxtSearch = findViewById(R.id.inputSerch);

        recyclerView = findViewById(R.id.RecyclerView);
        DataRef = FirebaseDatabase.getInstance().getReference().child("Car");
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);


    }
    private void LoadData() {
        options = new FirebaseRecyclerOptions.Builder<Car>().setQuery(DataRef,Car.class).build();
        adapter = new FirebaseRecyclerAdapter<Car, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull Car model) {
                holder.Nametxt.setText(model.getName());
                holder.Emailtxt.setText(model.getEmail());
                Picasso.get().load(model.getImageUri()).into(holder.imageView);
                holder.view.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(HomeActivity.this,ViewActivity.class);
                        intent.putExtra("CarKye",getRef(position).getKey());
                        startActivity(intent);

                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
 */
