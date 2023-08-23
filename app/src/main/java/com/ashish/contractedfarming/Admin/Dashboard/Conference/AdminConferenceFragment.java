package com.ashish.contractedfarming.Admin.Dashboard.Conference;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.ashish.contractedfarming.Admin.Dashboard.News.AdminNewsModel;
import com.ashish.contractedfarming.Farmer.ConferenceAndWorkShop.FarmerCandWAdapter;
import com.ashish.contractedfarming.Models.ConferenceModel;
import com.ashish.contractedfarming.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class AdminConferenceFragment extends Fragment {
    Context context;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference,referenceUsers;

    Calendar dateToday;


    RecyclerView recyclerViewToday,recyclerViewUpcoming,recyclerViewPast;
    ArrayList<ConferenceModel> listToday,listUpcoming,listPast ;


    private static int PICK_IMAGE = 123;
    Uri imageUri = null;

    public AdminConferenceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_admin_conference, container, false);



        database = FirebaseDatabase.getInstance();

        reference = database.getReference("conf-workshop");
        referenceUsers=database.getReference("users");

        context = getContext();

        dateToday= Calendar.getInstance();


        recyclerViewToday = v.findViewById(R.id.admin_conf_rv_current);
        recyclerViewUpcoming = v.findViewById(R.id.admin_conf_rv_future);
        recyclerViewPast = v.findViewById(R.id.admin_conf_rv_over);

        FloatingActionButton addConfButton = v.findViewById(R.id.admin_dash_conf_floatinf_add);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listToday = new ArrayList<>();
                listUpcoming = new ArrayList<>();
                listPast = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //Log.d("Date comp",(new SimpleDateFormat("dd MMM yyyy").format(Long.parseLong(ds.child("conf_date").getValue().toString()) * 1000L))+ "    " +(new SimpleDateFormat("dd MMM yyyy").format(dateToday.getTime())));
                    if ((new SimpleDateFormat("dd MMM yyyy").format(Long.parseLong(ds.child("conf_date").getValue().toString()) * 1000L)).equals(new SimpleDateFormat("dd MMM yyyy").format(dateToday.getTime()))) {

                        listToday.add(new ConferenceModel(ds.child("conf_id").getValue().toString(), ds.child("conf_topic").getValue().toString(), ds.child("by_name").getValue().toString(), ds.child("conf_date").getValue().toString(), ds.child("conf_venue").getValue().toString(), ds.child("conf_img_url").getValue().toString(), ds.child("created_date").getValue().toString()));
                    } else if ((new Date(Long.parseLong(ds.child("conf_date").getValue().toString()) * 1000L)).before(dateToday.getTime())) {
                        listPast.add(new ConferenceModel(ds.child("conf_id").getValue().toString(), ds.child("conf_topic").getValue().toString(), ds.child("by_name").getValue().toString(), ds.child("conf_date").getValue().toString(), ds.child("conf_venue").getValue().toString(), ds.child("conf_img_url").getValue().toString(), ds.child("created_date").getValue().toString()));
                    } else {
                        listUpcoming.add(new ConferenceModel(ds.child("conf_id").getValue().toString(), ds.child("conf_topic").getValue().toString(), ds.child("by_name").getValue().toString(), ds.child("conf_date").getValue().toString(), ds.child("conf_venue").getValue().toString(), ds.child("conf_img_url").getValue().toString(), ds.child("created_date").getValue().toString()));
                    }
                }


                Collections.sort(listToday, new Comparator<ConferenceModel>() {
                    @Override
                    public int compare(ConferenceModel o1, ConferenceModel o2) {
                        return o1.getConf_date().compareTo(o2.getConf_date());
                    }
                });

                Collections.sort(listUpcoming, new Comparator<ConferenceModel>() {
                    @Override
                    public int compare(ConferenceModel o1, ConferenceModel o2) {
                        return o1.getConf_date().compareTo(o2.getConf_date());
                    }
                });

                Collections.sort(listPast, new Comparator<ConferenceModel>() {
                    @Override
                    public int compare(ConferenceModel o1, ConferenceModel o2) {
                        return o1.getConf_date().compareTo(o2.getConf_date());
                    }
                });



                FarmerCandWAdapter adapterToday = new FarmerCandWAdapter(context, listToday);
                FarmerCandWAdapter adapterUpcoming = new FarmerCandWAdapter(context, listUpcoming);
                FarmerCandWAdapter adapterPast = new FarmerCandWAdapter(context, listPast);

                LinearLayoutManager layoutManagerToday = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                LinearLayoutManager layoutManagerUpcoming = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                LinearLayoutManager layoutManagerPast = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);


                recyclerViewToday.setLayoutManager(layoutManagerToday);
                recyclerViewUpcoming.setLayoutManager(layoutManagerUpcoming);
                recyclerViewPast.setLayoutManager(layoutManagerPast);

                recyclerViewToday.setAdapter(adapterToday);
                recyclerViewUpcoming.setAdapter(adapterUpcoming);
                recyclerViewPast.setAdapter(adapterPast);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addConfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.prompt_add_news, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                //final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

                final EditText title, disc;
                final ImageView imguri;


                title = promptsView.findViewById(R.id.prompt_add_news_title);
                disc = promptsView.findViewById(R.id.prompt_add_news_disc);

                imguri = promptsView.findViewById(R.id.prompt_add_news_image);


                imguri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (imageUri != null) {
                            imguri.setImageURI(imageUri);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            startActivityForResult(intent, PICK_IMAGE);
                        }
                    }
                });


                // set dialog message
                alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                        //result.setText(userInput.getText());
                        if ((title.getText().toString().isEmpty() || disc.getText().toString().isEmpty())) {
                            // disable positive button
                            //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                        } else {
                            // do something with the input

                            Log.d("Dialog ", "__" + title.getText().length() + "--");
                            sendImagetoStorage(new AdminNewsModel(String.valueOf((System.currentTimeMillis() / 1000)), title.getText().toString(), String.valueOf((System.currentTimeMillis() / 1000)), disc.getText().toString(), ""), imageUri);
                        }

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // show it
                alertDialog.show();
            }
        });



        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // In fragment class callback

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            imageUri = data.getData();
            Log.d("Img Uri ------", data.getData().toString());
        }


    }

    private void sendImagetoStorage(AdminNewsModel pm, Uri imguri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        //StorageReference imageref = storageRef.child("Images").child(firebaseAuth.getUid()).child("Profile Pic");

        StorageReference imageref = storageRef.child("ConferenceAndWorkshop").child(pm.getId()).child("image");

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
                    pm.setImgurl(downloadUri[0].toString());
                    sendToRealtimeDatabase(pm);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });


    }

    public void sendToRealtimeDatabase(AdminNewsModel pm) {
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference reference = storage.getReference("news");
        reference.child(pm.getId()).setValue(pm);

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

}
}