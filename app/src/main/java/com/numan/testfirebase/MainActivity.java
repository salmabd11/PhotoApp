package com.numan.testfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGE= 1;
    private ImageView mprofilImage;
    private EditText Name , Email;
    private TextView proggers;
    private Button Save;
    private ProgressBar mProgressbar;
    Uri imageUri;
    boolean imageAdded = false;
    DatabaseReference DataRef;
    StorageReference StorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mprofilImage = findViewById(R.id.profilephoto);
        Name = findViewById(R.id.EtName);
        Email = findViewById(R.id.EtEmail);
        proggers = findViewById(R.id.textviewprogress);
        mProgressbar = findViewById(R.id.Progressbar);
        Save = findViewById(R.id.SaveBTN);
        DataRef = FirebaseDatabase.getInstance().getReference().child("Car");
        StorageRef = FirebaseStorage.getInstance().getReference().child("Photo Car");

        proggers.setVisibility(View.GONE);
        mProgressbar.setVisibility(View.GONE);

        mprofilImage.setOnClickListener(new View.OnClickListener() {
            class REQUEST_CODE_IMAGE {
            }

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE);
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ImageName = Name.getText().toString();

                if (imageAdded !=false && ImageName !=null )
                {
                 UploadImage(ImageName);
                }

            }
        });
    }

    private void UploadImage(final String imageName) {
        final String ImageEmail = Email.getText().toString();
        proggers.setVisibility(View.VISIBLE);
        mProgressbar.setVisibility(View.VISIBLE);
        final String kye = DataRef.push().getKey();
        // Upload Image Firebase Storage
        StorageRef.child(kye+".jpg").putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageRef.child(kye+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //Upload Data Firebase Database
                        HashMap hashMap = new HashMap();
                        hashMap.put("Name", imageName);
                        hashMap.put("Email", ImageEmail);
                        hashMap.put("imageUri", uri.toString());
                        DataRef.child(kye).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                Toast.makeText(MainActivity.this,"Deta Successfully Uploaded",Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (taskSnapshot.getBytesTransferred()*100)/taskSnapshot.getTotalByteCount();
                mProgressbar.setProgress((int) progress);
                proggers.setText(progress+" % ");

            }
        });
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE || resultCode == RESULT_OK ||
                data != null || data.getData() != null){
            imageUri = data.getData();
            imageAdded=true;
            mprofilImage.setImageURI(imageUri);

        }



    }
}
