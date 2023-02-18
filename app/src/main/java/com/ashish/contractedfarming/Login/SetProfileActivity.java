package com.ashish.contractedfarming.Login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ashish.contractedfarming.Models.UserModel;
import com.ashish.contractedfarming.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetProfileActivity extends AppCompatActivity {

    private CardView mgetuserimage;
    private ImageView mgetuserimageinimageview;
    private static int PICK_IMAGE = 123;
    private Uri imagepath;
    private String userType = "",mob_no="",userUID="";

    private RadioGroup radioGroup;

    private Button msaveprofile;

    private EditText mgetusername;
    private String name;
    private String ImageUriAcessToken;

    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    ProgressBar mprogressbarofsetprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();


        //getting storage read permission
        getPermission();


        mob_no=firebaseAuth.getCurrentUser().getPhoneNumber();
        userUID=firebaseAuth.getUid();

        reference.child("all-users").child(firebaseAuth.getUid()).child("userUID").setValue(userUID);
        reference.child("all-users").child(firebaseAuth.getUid()).child("mob_no").setValue(mob_no);



        mgetusername = findViewById(R.id.set_profile_name);
        mgetuserimage = findViewById(R.id.getuserimage);
        mgetuserimageinimageview = findViewById(R.id.set_profile_image);
        msaveprofile = findViewById(R.id.saveProfile);
        mprogressbarofsetprofile = findViewById(R.id.progressbarofsetProfile);
        radioGroup = findViewById(R.id.set_profile_radiogroup);


        mgetuserimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.set_profile_radio_farmer:
                        userType = "farmer";
                        break;
                    case R.id.set_profile_radio_agent:
                        userType = "manager";
                        break;
                }
            }
        });

        msaveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = mgetusername.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Name is Empty", Toast.LENGTH_SHORT).show();
                }
                else if (imagepath == null) {
                    Toast.makeText(getApplicationContext(), "Image is Empty", Toast.LENGTH_SHORT).show();
                }
                else if (userType.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "User Type is Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    mprogressbarofsetprofile.setVisibility(View.VISIBLE);
                    sendDataForNewUser();
                }
            }
        });
    }

    private void sendDataForNewUser() {
        sendImagetoStorage();
    }

    private void sendDataToRealTimeDatabase() {
        name = mgetusername.getText().toString().trim();

        reference.child("users").child("new-"+userType).child(firebaseAuth.getUid()).setValue(new UserModel(userUID,name,mob_no,ImageUriAcessToken,"0"));
        reference.child("all-users").child(firebaseAuth.getUid()).setValue(new UserModel(firebaseAuth.getUid(), name,mob_no,ImageUriAcessToken,"new-"+userType,"offline", "0"));

        Intent intent = new Intent(SetProfileActivity.this, UserDetailsActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("img_url", ImageUriAcessToken);
        intent.putExtra("usertype", userType);
        startActivity(intent);
        finish();
    }

    private void sendImagetoStorage() {

        StorageReference imageref = storageReference.child("users").child(userType).child(firebaseAuth.getUid()).child("Profile Pic.jpg");

        //Image compresesion

        Bitmap bitmap = null,newbitmap=null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagepath);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap imageAfterRotation = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);



        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageAfterRotation.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();

        ///putting image to storage

        UploadTask uploadTask = imageref.putBytes(data);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                imageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImageUriAcessToken = uri.toString();
                        Picasso.get().load(ImageUriAcessToken).into(mgetuserimageinimageview);
                        sendDataToRealTimeDatabase();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "URI get Failed", Toast.LENGTH_SHORT).show();
                    }


                });
                Toast.makeText(getApplicationContext(), "Image is uploaded", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Image Not Uploaded", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imagepath = data.getData();
            mgetuserimageinimageview.setImageURI(imagepath);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void getPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else {

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //init();
                }
                else {
                    //finish();
                }
                break;
        }
    }






}



