package com.tamer.raed.doctorappointment.patient.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.tamer.raed.doctorappointment.R;

import java.util.Objects;

public class PatientSignUpActivity extends AppCompatActivity {
    private Toolbar toolbar;
    String username, email, phone, password, gender;
    private TextInputEditText et_username, et_phone, et_password, et_email;
    private RadioButton rb_male, rb_female;

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
        et_email = findViewById(R.id.patient_sign_up_et_email);
        rb_male = findViewById(R.id.patient_rb_male);
        rb_female = findViewById(R.id.patient_rb_female);
    }

    public void signUp(View view) {
        if (!TextUtils.isEmpty(et_username.getText().toString())) {
            username = et_username.getText().toString();
            if (!TextUtils.isEmpty(et_email.getText().toString())) {
                email = et_email.getText().toString();
                if (!TextUtils.isEmpty(et_phone.getText().toString())) {
                    phone = et_phone.getText().toString();
                    if (!TextUtils.isEmpty(et_password.getText().toString())) {
                        password = et_password.getText().toString();
                        getGender();
                        Toast.makeText(this, "Gender: " + gender, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PatientSignUpActivity.this, PatientDashboardActivity.class));
                        finish();
                    } else {
                        et_password.setError(getString(R.string.password_error));
                    }
                } else {
                    et_phone.setError(getString(R.string.phone_error));
                }
            } else {
                et_email.setError(getString(R.string.email_error));
            }
        } else {
            et_username.setError(getString(R.string.username_error));
        }
    }

    private void getGender() {
        if (rb_male.isChecked()) {
            gender = getString(R.string.male);
        } else if (rb_female.isChecked()) {
            gender = getString(R.string.female);
        }
    }
}