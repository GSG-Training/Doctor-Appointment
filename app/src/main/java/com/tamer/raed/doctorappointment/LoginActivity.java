package com.tamer.raed.doctorappointment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.tamer.raed.doctorappointment.Patient.PatientDashboard.PatientDashboardActivity;

public class LoginActivity extends AppCompatActivity {
    private TextView tvSignUp;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.white));// set status background white

        initViews();

        tvSignUp.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, WelcomeActivity.class)));
        login_btn.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, PatientDashboardActivity.class));
            finish();
        });
    }

    private void initViews() {
        tvSignUp = findViewById(R.id.login_tv_sign_up);
        login_btn = findViewById(R.id.login_btn_login);
    }
}