package com.tamer.raed.doctorappointment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    private Button btnSignUp;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initViews();

        tvLogin.setOnClickListener(view -> startActivity(new Intent(WelcomeActivity.this, LoginActivity.class)));
        btnSignUp.setOnClickListener(view -> startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class)));
    }

    private void initViews() {
        btnSignUp = findViewById(R.id.welcome_btn_sign_up);
        tvLogin = findViewById(R.id.welcome_tv);
    }
}