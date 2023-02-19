package com.ashish.contractedfarming.Manager.NewManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.contractedfarming.Login.LoginActivity;
import com.ashish.contractedfarming.MainActivity;
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

public class NewManagerUploadActivity extends AppCompatActivity implements View.OnClickListener {
    private static int PICK_IMAGE = 123;

    ImageView image = null, icon = null;

    TextView textView = null;
    Uri uri = null;
    String type = null;


    ImageView aadr_img, pan_img;

    ImageButton aadr_up_img, pan_up_img;

    TextView aadr_text, pan_text;

    Button next;

    FirebaseAuth firebaseAuth;


    FirebaseStorage storage;
    StorageReference storageReference;


    FirebaseDatabase database;
    DatabaseReference reference;

    ProgressBar pr;

    String adharurl = "", panurl = "";


    TextView t1, t2;
    Context context = null;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_manager_upload);


        firebaseAuth = FirebaseAuth.getInstance();
        context= getBaseContext();


        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        context = getBaseContext();


        aadr_img = findViewById(R.id.agent_upload_imageview_aadhar_image);
        pan_img = findViewById(R.id.agent_upload_imageview_pan_image);


        aadr_up_img = findViewById(R.id.agent_upload_imageview_aadhar_selimage);
        pan_up_img = findViewById(R.id.agent_upload_imageview_pan_selimage);


        aadr_text = findViewById(R.id.agent_upload_textview_aadhar_filename);
        pan_text = findViewById(R.id.agent_upload_textview_pan_filename);

        pr = findViewById(R.id.farmer_upload_progress3);

        aadr_up_img.setOnClickListener(this);
        pan_up_img.setOnClickListener(this);


        next = findViewById(R.id.agent_upload_button_next);
        next.setOnClickListener(this);
    }

    public void onClick(View view) {
        if(image!=null)
            if (image.getVisibility()==View.VISIBLE) {
                image.setVisibility(View.GONE);
            }

        if (view.getId() == R.id.agent_upload_button_next) {
            //Prompt function to be added
            if(pan_text.getText()!=null) {
                Toast.makeText(context, "Agree to term and condition prompt", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(context, ManagerApprovalWaitActivity.class));

                reference.child("users").child("new-manager").child(firebaseAuth.getUid()).child("approved_num").setValue("2");
                reference.child("all-users").child(firebaseAuth.getUid()).child("approved_num").setValue("2");
                finish();
            }

        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, view.getId());
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {

            uri = data.getData();
            switch (requestCode) {
                case R.id.agent_upload_imageview_aadhar_selimage:
                    image = aadr_img;
                    icon = aadr_up_img;
                    textView = aadr_text;
                    type = "aadhaar";
                    break;
                case R.id.agent_upload_imageview_pan_selimage:
                    image = pan_img;
                    icon = pan_up_img;
                    textView = pan_text;
                    type = "pan";
                    break;
            }
        }


        if (((image != null) || (icon != null)) || ((textView != null) || (type != null)) || (uri != null)) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            StorageReference upRef = storageReference.child("users").child("manager").child(firebaseAuth.getUid()).child(type + ".png");

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 25, byteArrayOutputStream);
            byte[] dataUp = byteArrayOutputStream.toByteArray();

            ///putting image to storage

            UploadTask uploadTask = upRef.putBytes(dataUp);
            pr.setVisibility(View.VISIBLE);


            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    upRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri2) {
                            // Continue with the task to get the download URL
                            //Log.d("Download URL", uri2.toString());

                            Picasso.get().load(uri2.toString()).into(image);
                            textView.setText(type + ".png");
                            image.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.VISIBLE);
                            icon.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.ic_baseline_check_24));

                            reference.child("users").child("new-manager").child(firebaseAuth.getUid()).child("verification").child(type + "_url").setValue(uri2.toString());

                            pr.setVisibility(View.INVISIBLE);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "URI get Failed", Toast.LENGTH_SHORT).show();
                        }


                    });
                    Toast.makeText(getApplicationContext(), type+"Image is uploaded", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), type +"Image Not Uploaded", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


}