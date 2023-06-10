package com.ashish.contractedfarming.AppInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.ashish.contractedfarming.Login.LoginActivity;
import com.ashish.contractedfarming.R;
import com.google.android.material.tabs.TabLayout;


public class AppInfoActivity extends AppCompatActivity {


    ViewPager viewPager;
    TabLayout tabLayout;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        VideoView vv = findViewById(R.id.info_video);


        String path = "android.resource://" + getPackageName() + "/" + R.raw.full;
        vv.setVideoURI(Uri.parse(path));
        vv.start();
       // viewPager= findViewById(R.id.app_info_viewpager);
        //tabLayout= findViewById(R.id.app_info_tablayout);
        button= findViewById(R.id.app_info_login_button);
//

     // viewPager.setAdapter(new AppInfoAdapter(getSupportFragmentManager()));

       // tabLayout.setupWithViewPager(viewPager, true);

//
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppInfoActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
}