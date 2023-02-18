package com.ashish.contractedfarming.Admin.Dashboard.News;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ashish.contractedfarming.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;

public class
NewsFragment extends Fragment {
    private static int PICK_IMAGE = 123;
    Uri imageUri = null;

    public NewsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("news");
        Context context = getContext();
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        FloatingActionButton addPlantButton = v.findViewById(R.id.admin_dash_news_floatinf_add);
        ListView listView = v.findViewById(R.id.admin_dash_news_list);
        SearchView searchView = v.findViewById(R.id.admin_dash_news_search);

        ArrayList<AdminNewsModel> arrayList = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.removeAll(arrayList);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    arrayList.add(new AdminNewsModel(ds.child("id").getValue().toString(), ds.child("topic").getValue().toString(), ds.child("date").getValue().toString(), ds.child("data").getValue().toString(), ds.child("imgurl").getValue().toString()));
                }
                if(getContext()!=null) {
                    AdminNewsAdapter adapter = new AdminNewsAdapter(getContext(), arrayList);
                    if (adapter != null) {
                        listView.setAdapter(adapter);
                    }
                }
                ;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        addPlantButton.setOnClickListener(new View.OnClickListener() {
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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AdminNewsAdapter ob = (AdminNewsAdapter) adapterView.getAdapter();

                reference.child(ob.getItem(i).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LayoutInflater li = LayoutInflater.from(getContext());
                        View promptsView = li.inflate(R.layout.popup_news, null);


                        TextView id, topic, date, data, imgurl;
                        ImageView imageView;
                        id = promptsView.findViewById(R.id.popup_news_id);
                        topic = promptsView.findViewById(R.id.popup_news_title);
                        date = promptsView.findViewById(R.id.popup_news_time);
                        data = promptsView.findViewById(R.id.popup_news_description);


                        imageView = promptsView.findViewById(R.id.popup_news_img);


                        id.setText(snapshot.child("id").getValue().toString());
                        data.setText(snapshot.child("data").getValue().toString());
                        date.setText(getDate(Long.parseLong(String.valueOf(snapshot.child("date").getValue().toString()))));
                        if (snapshot.child("imgurl").getValue() != null) {
                            Picasso.get().load(snapshot.child("imgurl").getValue().toString()).into(imageView);
                        }

                        topic.setText(snapshot.child("topic").getValue().toString());


                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                        // set prompts.xml to alertdialog builder
                        alertDialogBuilder.setView(promptsView);

                        // set dialog message
                        alertDialogBuilder.setCancelable(false).setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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

        StorageReference imageref = storageRef.child("News").child(pm.getId()).child("image");

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