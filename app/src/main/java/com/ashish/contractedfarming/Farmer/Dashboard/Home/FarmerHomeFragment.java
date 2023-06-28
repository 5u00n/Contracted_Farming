package com.ashish.contractedfarming.Farmer.Dashboard.Home;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.contractedfarming.Admin.Dashboard.Plant.AdminPlantsModel;
import com.ashish.contractedfarming.Farmer.Dashboard.ExplorePlants.FarmerExploreplantsAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.MyFarm.FarmerMyfarmAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.MyFarm.FarmerMyfarmModel;
import com.ashish.contractedfarming.Farmer.Dashboard.MyPlants.FarmerMyplantsAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.FarmerStoryAdapter;
import com.ashish.contractedfarming.Farmer.Dashboard.Story.FarmerStoryModel;
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

public class FarmerHomeFragment extends Fragment {

    ViewPager viewPager;

    private static final int ADD_STORY_IMG = 2;
    private static int PICK_IMAGE = 123;
    Uri imageUri = null;

    Context context;

    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseAuth auth;


    ImageView story_img;

    TextView username, user_img;


    RecyclerView farmerstoryRV, explorePlantsRv, myPlantsRv, myFarmRv;
    //Story Section
    ConstraintLayout constraintLayout;






    ArrayList<FarmerStoryModel> farmerstoryList;
    ArrayList<FarmerMyfarmModel> myfarmList;

    public FarmerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_farmer_home, container, false);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();

        viewPager=getActivity().findViewById(R.id.farmer_view_pager);


        farmerstoryRV = v.findViewById(R.id.farmerstoryRV);
        explorePlantsRv = v.findViewById(R.id.farmer_explore_plant);
        myPlantsRv = v.findViewById(R.id.farmer_myplantsrv);
        myFarmRv = v.findViewById(R.id.farmer_myfarmRv);
        constraintLayout = v.findViewById(R.id.addStory_layout);
        username = v.findViewById(R.id.username_fragment_farmer_home);
        user_img = v.findViewById(R.id.user_img_fragment_farmer_home);

        v.findViewById(R.id.farmer_dash_see_all_story_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });

        v.findViewById(R.id.addPlantImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
        v.findViewById(R.id.farmer_dash_explore_expand_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });

        v.findViewById(R.id.farmer_dash_my_farm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
            }
        });

        v.findViewById(R.id.farmer_dash_my_plant_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(4);
            }
        });





        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.prompt_add_story);

                EditText story_disc;
                Button addStory, cancel;
                story_disc = dialog.findViewById(R.id.prompt_add_story_disc);
                story_img = dialog.findViewById(R.id.prompt_add_story_img);

                story_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        startActivityForResult(intent, ADD_STORY_IMG);


                    }
                });


                addStory = dialog.findViewById(R.id.prompt_add_story_button_post);
                cancel = dialog.findViewById(R.id.prompt_add_story_button_cancel);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                addStory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String time_stamp = String.valueOf((System.currentTimeMillis() / 1000));
                        if (story_disc.getText().toString().isEmpty()) {
                            story_disc.setHint("Please Enter Description !");
                            story_disc.setHintTextColor(Color.RED);
                        } else {
                            sendImageToStorage(new FarmerStoryModel(auth.getUid(), username.getText().toString(), user_img.getText().toString(), "", story_disc.getText().toString(), time_stamp), imageUri);
                        }

                        dialog.dismiss();

                    }
                });

                dialog.show();
            }
        });

        //initMyPlants();
        updateView();
        return v;
    }


    void updateView() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()) {
                    username.setText(snapshot.child("users").child("farmer").child(auth.getUid()).child("username").getValue().toString());
                    user_img.setText(snapshot.child("users").child("farmer").child(auth.getUid()).child("img_url").getValue().toString());
                    if (snapshot.child("plants").exists()) {
                        initExplorePlants(snapshot.child("plants"));
                    }
                    if (snapshot.child("users").child("farmer").child(auth.getUid()).child("plot").exists()) {
                        initMyFarm(snapshot.child("users").child("farmer").child(auth.getUid()).child("plot"));
                    }
                    if (snapshot.child("users").child("farmer").child(auth.getUid()).child("my_plants").child("accepted").exists()) {
                        initMyPlants(snapshot.child("users").child("farmer").child(auth.getUid()).child("my_plants").child("accepted"));
                    }
                    if (snapshot.child("Story").child(auth.getUid()).exists()) {
                        initStory(snapshot.child("Story").child(auth.getUid()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void initMyPlants(DataSnapshot myPlants) {
        ArrayList<AdminPlantsModel> myplantList = new ArrayList<>();

        for (DataSnapshot p_ds : myPlants.getChildren()) {
            myplantList.add(new AdminPlantsModel(p_ds.child("id").getValue().toString(), p_ds.child("name").getValue().toString(), p_ds.child("imgurl").getValue().toString()));

        }
        FarmerMyplantsAdapter adapter3 = new FarmerMyplantsAdapter(myplantList, context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        myPlantsRv.setLayoutManager(layoutManager);
        myPlantsRv.setNestedScrollingEnabled(false);
        myPlantsRv.setAdapter(adapter3);
    }


    public void initExplorePlants(DataSnapshot allPlants) {

        ArrayList<AdminPlantsModel> exploreplantList = new ArrayList<>();

        for (DataSnapshot p_ds : allPlants.getChildren()) {
            exploreplantList.add(new AdminPlantsModel(p_ds.child("id").getValue().toString(), p_ds.child("name").getValue().toString(), p_ds.child("imgurl").getValue().toString()));

        }
        FarmerExploreplantsAdapter adapter2 = new FarmerExploreplantsAdapter(exploreplantList, context);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        explorePlantsRv.setLayoutManager(layoutManager2);
        explorePlantsRv.setNestedScrollingEnabled(false);
        explorePlantsRv.setAdapter(adapter2);

    }

    public void initMyFarm(DataSnapshot myFarm) {
        myfarmList = new ArrayList<>();

        for (DataSnapshot fds : myFarm.getChildren()) {
            FarmerMyfarmModel fm = new FarmerMyfarmModel(fds.child("plotID").getValue().toString(), fds.child("userUID").getValue().toString(), fds.child("area").getValue().toString(), fds.child("village").getValue().toString(), fds.child("taluka").getValue().toString(), fds.child("dist").getValue().toString(), fds.child("state").getValue().toString(), fds.child("gat_no").getValue().toString(), fds.child("sarvay_no").getValue().toString(), "Nil");
            if (fds.child("plant").exists()) {
                fm.setPlant(fds.child("plant").getValue().toString());
            }
            myfarmList.add(fm);
            // myfarmList.add(new FarmerMyfarmModel("1", "11", "1", "pune", "pune", "pune", "maha", "55", "10", "tree"));
        }
        FarmerMyfarmAdapter adapter4 = new FarmerMyfarmAdapter(myfarmList, context);
        LinearLayoutManager layoutManager4 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        myFarmRv.setLayoutManager(layoutManager4);
        myFarmRv.setAdapter(adapter4);
    }

    public void initStory(DataSnapshot storyDS) {


        farmerstoryList = new ArrayList<>();
        if (storyDS.hasChildren())
            for (DataSnapshot ds : storyDS.getChildren()) {
                farmerstoryList.add(new FarmerStoryModel(ds.child("userUID").getValue().toString(), ds.child("username").getValue().toString(), ds.child("user_img_url").getValue().toString(), ds.child("img_url").getValue().toString(), ds.child("story_text").getValue().toString(), ds.child("story_time").getValue().toString()));
            }

        FarmerStoryAdapter adapter1 = new FarmerStoryAdapter(farmerstoryList, context);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        farmerstoryRV.setLayoutManager(layoutManager1);
        farmerstoryRV.setNestedScrollingEnabled(false);
        farmerstoryRV.setAdapter(adapter1);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // In fragment class callback

        if (requestCode == ADD_STORY_IMG && resultCode == RESULT_OK) {
            imageUri = data.getData();
            Log.d("Img Uri ------", data.getData().toString());
            story_img.setImageURI(imageUri);
        }


    }

    private void sendImageToStorage(FarmerStoryModel sm, Uri imguri) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        StorageReference imageref = storageRef.child("users").child("farmer").child(auth.getUid()).child("story").child(sm.getStory_time()).child("story_img");

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
                    sm.setImg_url(downloadUri[0].toString());
                    sendToRealtimeDatabase(sm);

                } else {
                    // Handle failures
                    // ...
                }
            }
        });


    }

    public void sendToRealtimeDatabase(FarmerStoryModel sm) {
        FirebaseDatabase storage = FirebaseDatabase.getInstance();
        DatabaseReference reference = storage.getReference();
        reference.child("users").child("farmer").child(auth.getUid()).child("story").child(sm.getStory_time()).setValue(sm);
        reference.child("Story").child(auth.getUid()).child(sm.getStory_time()).setValue(sm);

        imageUri = null;

    }


}