package com.ashish.contractedfarming.Admin.Dashboard.Plant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.PlantPopUp.PlantPopViewPagerAdapter;
import com.ashish.contractedfarming.Models.PlantModel;
import com.ashish.contractedfarming.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import static android.app.Activity.RESULT_OK;

public class PlantFragment extends Fragment {
    private static int PICK_IMAGE = 123;
    Uri imageUri = null;

    public PlantFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("plants");
        Context context = getContext();
        View v = inflater.inflate(R.layout.fragment_admin_dashboard_plants, container, false);
        FloatingActionButton addPlantButton = v.findViewById(R.id.admin_dash_plant_floatinf_add);
        ListView listView = v.findViewById(R.id.admin_dash_plant_list);
        SearchView searchView = v.findViewById(R.id.admin_dash_plant_search);

        //on click popup


        final AdminPlantsAdapter[] adapter = new AdminPlantsAdapter[1];

        ArrayList<AdminPlantsModel> arrayList = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (!searchView.hasFocus()) {
                    arrayList.removeAll(arrayList);
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        arrayList.add(new AdminPlantsModel(ds.child("id").getValue().toString(), ds.child("name").getValue().toString(), ds.child("imgurl").getValue().toString()));
                    }
                    if (context!=null) {
                        adapter[0] = new AdminPlantsAdapter(getContext(), arrayList);
                    }
                    if (adapter[0] != null) {
                        listView.setAdapter(adapter[0]);
                    }
                }
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        arrayList.removeAll(arrayList);
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if (newText.length() <= ds.child("name").toString().length()) {
                                if (ds.child("name").toString().toLowerCase().contains(newText.toString().toLowerCase())) {
                                    arrayList.add(new AdminPlantsModel(ds.child("id").getValue().toString(), ds.child("name").getValue().toString(), ds.child("imgurl").getValue().toString()));
                                }
                            }

                        }
                        adapter[0] = new AdminPlantsAdapter(getContext(), arrayList);
                        if (adapter[0] != null) {
                            listView.setAdapter(adapter[0]);
                        }
                        return false;
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AdminPlantsAdapter ob = (AdminPlantsAdapter) adapterView.getAdapter();


                reference.child(ob.getItem(i).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        final Dialog dialog = new Dialog(getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.popup_plant);
                        dialog.setCanceledOnTouchOutside(false);

                        PlantPopViewPagerAdapter adapter = new PlantPopViewPagerAdapter(getContext(), snapshot);
                        ViewPager pager = (ViewPager) dialog.findViewById(R.id.popup_admin_plant_viewpager);
                        TabLayout tabLayout = dialog.findViewById(R.id.popup_admin_plant_tablayout);
                        tabLayout.setupWithViewPager(pager);
                        pager.setAdapter(adapter);
                        dialog.show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        addPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(getContext());
                View promptsView = li.inflate(R.layout.prompt_add_plant, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                //final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);

                final EditText name, disc, life_span, humidity, fertilizer, water_time, light, temp;
                final ImageView imguri;


                name = promptsView.findViewById(R.id.prompt_add_plant_name);
                disc = promptsView.findViewById(R.id.prompt_add_plant_disc);
                life_span = promptsView.findViewById(R.id.prompt_add_plant_lifespan);
                humidity = promptsView.findViewById(R.id.prompt_add_plant_humidity);
                fertilizer = promptsView.findViewById(R.id.prompt_add_plant_fertilizer);
                water_time = promptsView.findViewById(R.id.prompt_add_plant_watertime);
                light = promptsView.findViewById(R.id.prompt_add_plant_light);
                temp = promptsView.findViewById(R.id.prompt_add_plant_temprature);
                imguri = promptsView.findViewById(R.id.prompt_add_plant_image);


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
                        if ((name.getText().toString().isEmpty() || disc.getText().toString().isEmpty()) || (life_span.getText().toString().isEmpty() || humidity.getText().toString().isEmpty()) || (fertilizer.getText().toString().isEmpty() || water_time.getText().toString().isEmpty()) || (light.getText().toString().isEmpty() || temp.getText().toString().isEmpty())) {
                        } else {
                            // do something with the input

                            Log.d("Dialog ", "__" + name.getText().length() + "--");
                            sendImagetoStorage(new PlantModel(String.valueOf((System.currentTimeMillis() / 1000)), name.getText().toString(), disc.getText().toString(), life_span.getText().toString(), humidity.getText().toString(), fertilizer.getText().toString(), water_time.getText().toString(), light.getText().toString(), temp.getText().toString(), ""), imageUri);
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

    private void sendImagetoStorage(PlantModel pm, Uri imguri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        //StorageReference imageref = storageRef.child("Images").child(firebaseAuth.getUid()).child("Profile Pic");

        StorageReference imageref = storageRef.child("Plants").child(pm.getName()).child("image");

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
                    Log.d("Download uri", downloadUri[0].toString());
                    pm.setImgurl(downloadUri[0].toString());
                    sendToRealtimeDatabase(pm);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });


    }

    public void sendToRealtimeDatabase(PlantModel pm) {
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference reference = storage.getReference("plants");
        reference.child(pm.getId()).setValue(pm);

        imageUri = null;

    }

}
