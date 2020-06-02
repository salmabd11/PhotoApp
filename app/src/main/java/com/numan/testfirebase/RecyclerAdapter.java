package com.numan.testfirebase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHoldeer> {
    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<Car> carArrayList;

    public RecyclerAdapter(Context mContext, ArrayList<Car> carArrayList) {
        this.mContext = mContext;
        this.carArrayList = carArrayList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHoldeer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
         .inflate(R.layout.caritem,parent,false);
        return new ViewHoldeer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoldeer holder, int position) {
       holder.textViewname.setText(carArrayList.get(position).getName());
       holder.textViewemail.setText(carArrayList.get(position).getEmail());

       //Image Load
       // Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView);
        Glide.with(mContext)
                .load(carArrayList.get(position).getImageUri())
                .into(holder.imageView);


    }


    @Override
    public int getItemCount() {
        return carArrayList.size();
    }

    public class ViewHoldeer extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewname;
        TextView textViewemail;

        public ViewHoldeer(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.CImageView);
            textViewname = itemView.findViewById(R.id.txtCName);
            textViewemail = itemView.findViewById(R.id.txtCEmail);

        }
    }


}

