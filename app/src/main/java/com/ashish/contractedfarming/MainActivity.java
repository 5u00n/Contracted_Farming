package com.ashish.contractedfarming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ashish.contractedfarming.AppInfo.AppInfoActivity;
import com.ashish.contractedfarming.Login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences sharedPreferences = getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE);

        boolean isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true);

        if (isFirstLaunch) {
            isFirstLaunch = false;
            sharedPreferences.edit().putBoolean("isFirstLaunch", isFirstLaunch).apply();

            startActivity(new Intent(this, AppInfoActivity.class));
            finish();
        }else{
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        // ...
    }
}
