package com.tamer.raed.doctorappointment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tamer.raed.doctorappointment.patient.ui.activities.PatientDashboardActivity;

public class LoginActivity extends AppCompatActivity {
    private TextView tvSignUp;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();

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