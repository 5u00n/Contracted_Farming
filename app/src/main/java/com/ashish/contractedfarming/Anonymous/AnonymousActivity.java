package com.ashish.contractedfarming.Anonymous;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ashish.contractedfarming.R;

public class AnonymousActivity extends AppCompatActivity implements View.OnClickListener {


    ImageButton back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonymous);

        back_button= findViewById(R.id.anonymous_imagebutton_back);

        back_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}