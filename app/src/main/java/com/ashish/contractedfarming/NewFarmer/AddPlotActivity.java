package com.ashish.contractedfarming.NewFarmer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.contractedfarming.Models.PlotModel;
import com.ashish.contractedfarming.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddPlotActivity extends AppCompatActivity {


    private static final int PICK_IMAGE = 123;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    FirebaseStorage storage;
    StorageReference storageReference;


    EditText plotName,_7_12_edittext, _8a_edittext, area, village, taluka, dist, gat_no, sarvay_no;


    ImageButton upload_712,upload_8a,upload_plot_img;


    TextView _712_url,_8a_url,plot_img_url,plot_img_text;;
    Button submit,addPlot,cancel;
    ImageButton add;
    ListView lv;

    Context context;

    String plotID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plot);

        firebaseAuth= FirebaseAuth.getInstance();
        database= FirebaseDatabase.getInstance();
        databaseReference= database.getReference();

        storage= FirebaseStorage.getInstance();
        storageReference= storage.getReference("users").child("farmer").child(firebaseAuth.getUid());


        context = getBaseContext();

        add = findViewById(R.id.plotadd_imageview_add);
        lv = findViewById(R.id.plotadd_listview);
        submit = findViewById(R.id.plotadd_button_submit);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time_stamp=String.valueOf((System.currentTimeMillis() / 1000));
                final Dialog dialog = new Dialog(AddPlotActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.prompt_add_plot);

                plotID=firebaseAuth.getUid()+"__"+time_stamp;


                //plot_img_text=dialog.findViewById(R.id.prompt_add_plot_text)

                plotName = dialog.findViewById(R.id.prompt_add_plot_name);
                plot_img_text=dialog.findViewById(R.id.prompt_add_plot_text);
                _7_12_edittext = dialog.findViewById(R.id.prompt_add_7_12);
                _8a_edittext = dialog.findViewById(R.id.prompt_add_8a);
                area = dialog.findViewById(R.id.prompt_addplot_area);
                village = dialog.findViewById(R.id.prompt_addplot_Village);
                taluka = dialog.findViewById(R.id.prompt_addplot_taluka);
                dist = dialog.findViewById(R.id.prompt_addplot_District);
                gat_no = dialog.findViewById(R.id.prompt_addplot_Gatno);
                sarvay_no = dialog.findViewById(R.id.prompt_addplot_Survayno);

                _712_url = dialog.findViewById(R.id.prompt_7_12_url);
                _8a_url = dialog.findViewById(R.id.prompt_8a_url);
                plot_img_url=dialog.findViewById(R.id.prompt_plot_img_url);

                upload_712= dialog.findViewById(R.id.farmer_addplot_imageview_7_12_selimage);
                upload_8a=dialog.findViewById(R.id.farmer_addplot_imageview_8a_selimage);

                upload_plot_img=dialog.findViewById(R.id.farmer_addplot_imageview_plot_selimage);


                upload_8a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }
                });

                upload_712.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 1);
                    }
                });

                upload_plot_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 3);
                    }
                });


                addPlot= dialog.findViewById(R.id.prompt_addbutton);
                cancel= dialog.findViewById(R.id.prompt_cancel_button);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                addPlot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(plotName.getText().toString().isEmpty()||plot_img_text.getText().toString().isEmpty()|| _7_12_edittext.getText().toString().isEmpty() || _8a_edittext.getText().toString().isEmpty() || area.getText().toString().isEmpty() || village.getText().toString().isEmpty() || taluka.getText().toString().isEmpty() || dist.getText().toString().isEmpty() || gat_no.getText().toString().isEmpty() || sarvay_no.getText().toString().isEmpty()){
                            Toast.makeText(AddPlotActivity.this, "Field Empty Please Fill!", Toast.LENGTH_SHORT).show();
                        }else {
                            databaseReference.child("users").child("new-farmer").child(firebaseAuth.getUid()).child("plot").child(plotID).setValue(new PlotModel(plotID,plotName.getText().toString(),firebaseAuth.getUid(),plot_img_url.getText().toString(),area.getText().toString(),village.getText().toString(),taluka.getText().toString(),dist.getText().toString(),"Maharashtra",gat_no.getText().toString(),sarvay_no.getText().toString(),_7_12_edittext.getText().toString(),_8a_edittext.getText().toString(),_712_url.getText().toString(),_8a_url.getText().toString()));
                            databaseReference.child("plots").child(plotID).setValue(new PlotModel(plotID,plotName.getText().toString(),firebaseAuth.getUid(),plot_img_url.getText().toString(),area.getText().toString(),village.getText().toString(),taluka.getText().toString(),dist.getText().toString(),"Maharashtra",gat_no.getText().toString(),sarvay_no.getText().toString(),_7_12_edittext.getText().toString(),_8a_edittext.getText().toString(),_712_url.getText().toString(),_8a_url.getText().toString()));


                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }
        });



        databaseReference.child("users").child("new-farmer").child(firebaseAuth.getUid()).child("plot").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    submit.setEnabled(true);
                    ArrayList<String> list= new ArrayList<>();
                    int count=1;
                    for(DataSnapshot ds: snapshot.getChildren()){
                        list.add("Plot : "+count+"  |  Gat No : "+ds.child("gat_no").getValue().toString()+"  |  Sarvay No : "+ds.child("sarvay_no").getValue().toString());
                        count++;
                    }
                    lv.setAdapter(new ArrayAdapter<String>(AddPlotActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("all-users").child(firebaseAuth.getUid()).child("approved_num").setValue("3");
                databaseReference.child("users").child("new-farmer").child(firebaseAuth.getUid()).child("approved_num").setValue("3");

                startActivity(new Intent(AddPlotActivity.this,NewFarmerApprovalWaitActivity.class));
                finish();

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // In fragment class callback

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            sendImagetoStorage("_7_12",data.getData());
        }
        if (requestCode == 2 && resultCode == RESULT_OK) {
            sendImagetoStorage("_8a",data.getData());
        }
        if (requestCode == 3 && resultCode == RESULT_OK) {
            sendImagetoStorage("plot_img",data.getData());
            plot_img_text.setText((System.currentTimeMillis() / 1000)+" plot img");
        }





    }

    private void sendImagetoStorage(String filetype, Uri imguri) {

        FirebaseStorage storage= FirebaseStorage.getInstance();
        StorageReference storageReference= storage.getReference("users").child("farmer").child(firebaseAuth.getUid()).child("plot").child(plotID);
        StorageReference imageref = storageReference.child("image"+filetype+".png");


        UploadTask uploadTask = imageref.putFile(imguri);
        final Uri[] downloadUri = new Uri[1];
        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return imageref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    downloadUri[0] = task.getResult();
                   if(filetype.equals("_7_12")){
                    _712_url.setText(downloadUri[0].toString());
                    upload_712.setImageResource(R.drawable.ic_baseline_check_24);
                   }
                    if(filetype.equals("_8a")){
                        _8a_url.setText(downloadUri[0].toString());
                        upload_8a.setImageResource(R.drawable.ic_baseline_check_24);
                    }
                    if(filetype.equals("plot_img")){
                        plot_img_url.setText(downloadUri[0].toString());
                        upload_plot_img.setImageResource(R.drawable.ic_baseline_check_24);
                    }

                } else {
                    // Handle failures
                    // ...
                }
            }
        });


    }


}
