package com.tamer.raed.doctorappointment.patient.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Patient;

import java.util.Objects;

public class PatientSignUpActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String username, email, phone, password, gender;
    private TextInputEditText et_username, et_phone, et_password, et_email;
    private RadioButton rb_male, rb_female;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private Button signUpBtn;

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
        progressBar = findViewById(R.id.sign_up_progressBar);
        signUpBtn = findViewById(R.id.patient_sign_up_btn);
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
                        signUpFirebase();

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

    private void signUpFirebase() {
        signUpBtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuth.getCurrentUser().getUid();
                    Patient patient = new Patient(id, username, email, gender, "", email);
                    db = FirebaseFirestore.getInstance();
                    db.collection("Patients").document(mAuth.getCurrentUser().getUid().toString()).set(patient).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(PatientSignUpActivity.this, getString(R.string.success_sign_up), Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(PatientSignUpActivity.this, getString(R.string.error_sign_up), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    progressBar.setVisibility(View.GONE);
                    signUpBtn.setVisibility(View.VISIBLE);
                    startActivity(new Intent(PatientSignUpActivity.this, PatientDashboardActivity.class));
                    finish();
                } else {
                    progressBar.setVisibility(View.GONE);
                    signUpBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(PatientSignUpActivity.this, getString(R.string.error_sign_up), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getGender() {
        if (rb_male.isChecked()) {
            gender = getString(R.string.male);
        } else if (rb_female.isChecked()) {
            gender = getString(R.string.female);
        }
    }
}