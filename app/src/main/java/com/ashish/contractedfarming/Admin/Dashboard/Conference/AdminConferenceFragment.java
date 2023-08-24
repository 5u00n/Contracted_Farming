package com.ashish.contractedfarming.Admin.Dashboard.Conference;

import static android.app.Activity.RESULT_OK;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import com.ashish.contractedfarming.Farmer.ConferenceAndWorkShop.FarmerCandWAdapter;
import com.ashish.contractedfarming.Models.NotificationModel;
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

import java.text.ParseException;
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

    ImageView imguri;

    public AdminConferenceFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_admin_conference, container, false);

        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("conf-workshop");


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
                View promptsView = li.inflate(R.layout.prompt_add_conf, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                //final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

                final EditText title, venue,host_name;
                final Button select_date,select_time;

                select_date =promptsView.findViewById(R.id.prompt_add_conf_date);
                select_time =promptsView.findViewById(R.id.prompt_add_conf_time);


                title = promptsView.findViewById(R.id.prompt_add_conf_title);
                venue = promptsView.findViewById(R.id.prompt_add_conf_venue);
                host_name = promptsView.findViewById(R.id.prompt_add_conf_host_name);

                imguri = promptsView.findViewById(R.id.prompt_add_conf_image);


                imguri.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  if (imageUri != null) {
                        //    imguri.setImageURI(imageUri);
                       // } else {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            startActivityForResult(intent, PICK_IMAGE);
                        //}
                    }
                });
                select_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();

                        // on below line we are getting
                        // our day, month and year.
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        // on below line we are creating a variable for date picker dialog.
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                // on below line we are passing context.
                                context,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        // on below line we are setting date to our edit text.
                                        select_date.setText(dayOfMonth + "-" + ((monthOfYear + 1)<10?"0"+(monthOfYear + 1):(monthOfYear + 1)) + "-" + year);

                                    }
                                },
                                // on below line we are passing year,
                                // month and day for selected date in our date picker.
                                year, month, day);
                        // at last we are calling show to
                        // display our date picker dialog.
                        datePickerDialog.show();
                    }
                });

                select_time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar c = Calendar.getInstance();

                        // on below line we are getting
                        // our day, month and year.
                        int hour = c.get(Calendar.HOUR);
                        int min = c.get(Calendar.MINUTE);


                        TimePickerDialog timePickerDialog= new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                if(i>12) {
                                    select_time.setText((i-12<10?"0"+(i-12):(i-12)) + " : "+(i1<10?"0" + i1:i1)+ " PM");
                                }else {
                                    select_time.setText((i<10?"0"+1:i) + " : "+(i1<10?"0" + i1:i1)+ " AM");
                                }
                            }
                        },hour,min,false);
                        // on below line we are creating a variable for date picker dialog.

                        timePickerDialog.show();
                    }
                });


                // set dialog message
                alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if ((title.getText().toString().isEmpty() || venue.getText().toString().isEmpty()) || (host_name.getText().toString().isEmpty() || select_date.getText().toString().isEmpty()) || select_time.getText().toString().isEmpty()) {

                        } else {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH : mm a");
                            long timestampSec=0;
                            try {
                                Date date = dateFormat.parse(select_date.getText().toString()+" "+select_time.getText().toString());
                                timestampSec = date.getTime()/1000;
                                sendImagetoStorage(new ConferenceModel("conf_"+auth.getUid()+"_"+String.valueOf((System.currentTimeMillis() / 1000)), title.getText().toString(), host_name.getText().toString(),String.valueOf(timestampSec),venue.getText().toString(),"",String.valueOf((System.currentTimeMillis() / 1000))), imageUri);
                            } catch (ParseException e) {
                                Log.d("Parsing Error ",e.toString());
                            }

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
            imguri.setImageURI(imageUri);
            Log.d("Img Uri ------", data.getData().toString());
        }
    }

    private void sendImagetoStorage(ConferenceModel pm, Uri imguri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference imageref = storageRef.child("ConferenceAndWorkshop").child(pm.getConf_id()).child("image");

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
                    pm.setConf_img_url(downloadUri[0].toString());
                    sendToRealtimeDatabase(pm);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });


    }

    public void sendToRealtimeDatabase(ConferenceModel pm) {
       reference.child(pm.getConf_id()).setValue(pm);
       referenceUsers=database.getReference();
        referenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshotAll) {
                DataSnapshot snapshot=snapshotAll.child("users");
                if(snapshot.exists()){
                    for(DataSnapshot snapshotUsers: snapshot.getChildren()){
                        if(snapshotUsers.hasChildren()){
                            if(!snapshotUsers.getKey().contains("new-")) {
                                //Log.d("Notifications Logs to all users","No new users ---  "+snapshotUsers.getKey());
                                for (DataSnapshot snapshotI : snapshotUsers.getChildren()) {
                                    if (!auth.getUid().equals(snapshotI.child("userUID").getValue().toString())) {
                                        referenceUsers.child("users").child(snapshotUsers.getKey()).child(snapshotI.child("userUID").getValue().toString()).child("notifications").child("noti_" + pm.getConf_id()).setValue(new NotificationModel("noti_" + pm.getConf_id(), "", auth.getUid(), "created a conference for that date : " + new SimpleDateFormat("dd MMM YYYY HH:mm a").format(Long.parseLong(pm.getConf_date()) * 1000) + " click to check ! ", String.valueOf(Calendar.getInstance().getTime().getTime() / 1000), "conference", "false"));
                                        //Log.d("Notifications Logs to all users",snapshotI.getKey());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }


}