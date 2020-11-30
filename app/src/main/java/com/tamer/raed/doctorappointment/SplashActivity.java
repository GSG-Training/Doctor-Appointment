package com.tamer.raed.doctorappointment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FrameLayout frameLayout = findViewById(R.id.splash_container);
        frameLayout.setOnClickListener(view -> startActivity(new Intent(SplashActivity.this, LoginActivity.class)));

    }
}