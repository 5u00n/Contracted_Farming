package com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.PlantAddition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashish.contractedfarming.Farmer.NewFarmer.AddPlotActivity;
import com.ashish.contractedfarming.Models.PlotModel;
import com.ashish.contractedfarming.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textview.MaterialTextView;
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
import java.util.Date;
import java.util.List;

public class FarmerSelectFarmActivity extends AppCompatActivity {



    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    Button next_button,add_newFarm;
    ImageButton back_button;
    ListView listView;

    String plant_id;




    EditText plotName,_7_12_edittext, _8a_edittext, area, village, taluka, dist, gat_no, sarvay_no;

    ImageButton upload_712,upload_8a,upload_plot_img;
    MaterialTextView plot_img_edittext;


    TextView _712_url,_8a_url,plot_img_url;
    Button submit,addPlot,cancel;

    String plotID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_select_farm);

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference= database.getReference();

        plant_id= getIntent().getExtras().getString("plant_id");
        next_button= findViewById(R.id.farmer_select_farm_popup_select_button_next);
        back_button= findViewById(R.id.farmer_select_farm_popup_back_button);

        listView= findViewById(R.id.farmer_select_farm_popup_listView);

        add_newFarm= findViewById(R.id.farmer_select_farm_popup_add_farm);



        ArrayList<PlotModel> plotModels = new ArrayList<>();
        reference.child("users").child("farmer").child(auth.getUid()).child("plot").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snap) {
                plotModels.clear();
                if(snap.exists()){
                    for(DataSnapshot snapshot:snap.getChildren() ) {
                        if (snapshot.child("approval").exists()) {
                            plotModels.add(new PlotModel(snapshot.child("plot_img_url").getValue().toString(), snapshot.child("plotID").getValue().toString(), snapshot.child("plotName").getValue().toString(), snapshot.child("area").getValue().toString(), snapshot.child("village").getValue().toString(), snapshot.child("approval").getValue().toString()));
                        } else {
                            plotModels.add(new PlotModel(snapshot.child("plot_img_url").getValue().toString(), snapshot.child("plotID").getValue().toString(), snapshot.child("plotName").getValue().toString(), snapshot.child("area").getValue().toString(), snapshot.child("village").getValue().toString(), "null"));

                        }
                    }

                    listView.setAdapter(new FarmerSelectFarmListViewAdapter(FarmerSelectFarmActivity.this,plotModels));
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(FarmerSelectFarmActivity.this, i, Toast.LENGTH_SHORT).show();
                FarmerSelectFarmListViewAdapter adapter= (FarmerSelectFarmListViewAdapter) adapterView.getAdapter();
                //CheckBox ck=view.findViewById(R.id.view_select_farm_listview_checkbox);

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FarmerSelectFarmActivity.this,FarmerExplorePlantsActivity.class).putExtra("plant_id",plant_id));
                finish();
            }
        });


        add_newFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(FarmerSelectFarmActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.prompt_add_plot);

                String time_stamp=String.valueOf((System.currentTimeMillis() / 1000));
                plotID=auth.getUid()+"__"+time_stamp;


                plotName = dialog.findViewById(R.id.prompt_add_plot_name);
                plot_img_edittext=dialog.findViewById(R.id.prompt_add_plot_edittext);
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


                        if(plotName.getText().toString().isEmpty()|| plot_img_edittext.getText().toString().isEmpty() || _7_12_edittext.getText().toString().isEmpty() || _8a_edittext.getText().toString().isEmpty() || area.getText().toString().isEmpty() || village.getText().toString().isEmpty() || taluka.getText().toString().isEmpty() || dist.getText().toString().isEmpty() || gat_no.getText().toString().isEmpty() || sarvay_no.getText().toString().isEmpty()){
                            Toast.makeText(FarmerSelectFarmActivity.this, "Field Empty Please Fill!", Toast.LENGTH_SHORT).show();
                        }else {
                            reference.child("users").child("farmer").child(auth.getUid()).child("plot").child(plotID).setValue(new PlotModel(plotID,plotName.getText().toString(),auth.getUid(),plot_img_url.getText().toString(),area.getText().toString(),village.getText().toString(),taluka.getText().toString(),dist.getText().toString(),"Maharashtra",gat_no.getText().toString(),sarvay_no.getText().toString(),_7_12_edittext.getText().toString(),_8a_edittext.getText().toString(),_712_url.getText().toString(),_8a_url.getText().toString()));
                            reference.child("plots").child(plotID).setValue(new PlotModel(plotID,plotName.getText().toString(),auth.getUid(),plot_img_url.getText().toString(),area.getText().toString(),village.getText().toString(),taluka.getText().toString(),dist.getText().toString(),"Maharashtra",gat_no.getText().toString(),sarvay_no.getText().toString(),_7_12_edittext.getText().toString(),_8a_edittext.getText().toString(),_712_url.getText().toString(),_8a_url.getText().toString()));


                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }
        });


        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(FarmerSelectFarmActivity.this, listView.getCount(), Toast.LENGTH_SHORT).show();


                FarmerSelectFarmListViewAdapter adapter= (FarmerSelectFarmListViewAdapter) listView.getAdapter();


                List<Boolean> checkboxStates = adapter.getCheckboxStates();

                for(int i=0;i<adapter.getCount();i++){
                    Log.d("List count", String.valueOf(checkboxStates.get(i)));
                }

                //Log.d("List count", String.valueOf(listView.getAdapter().getCount()));



            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FarmerSelectFarmActivity.this,FarmerExplorePlantsActivity.class).putExtra("plant_id",plant_id));
        finish();
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
            plot_img_edittext.setText((System.currentTimeMillis() / 1000)+" Farm img");
        }





    }

    private void sendImagetoStorage(String filetype, Uri imguri) {


        FirebaseStorage storage= FirebaseStorage.getInstance();
        StorageReference storageReference= storage.getReference("users").child("farmer").child("plot").child(plotID);
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