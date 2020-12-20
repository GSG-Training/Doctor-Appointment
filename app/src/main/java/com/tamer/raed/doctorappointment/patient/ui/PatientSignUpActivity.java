package com.tamer.raed.doctorappointment.patient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.patient.ui.patientDashboard.PatientDashboardActivity;

import java.util.Objects;

public class PatientSignUpActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextInputEditText et_username, et_phone, et_password;
    Button btn_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);
        initViews();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow));
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void initViews() {
        toolbar = findViewById(R.id.patient_sign_up_toolbar);
        et_username = findViewById(R.id.patient_sign_up_et_username);
        et_phone = findViewById(R.id.patient_sign_up_et_phone);
        et_password = findViewById(R.id.patient_sign_up_et_password);
        btn_sign_up = findViewById(R.id.patient_sign_up_btn);
    }

    public void signUp(View view) {
        startActivity(new Intent(PatientSignUpActivity.this, PatientDashboardActivity.class));
        finish();
    }
}