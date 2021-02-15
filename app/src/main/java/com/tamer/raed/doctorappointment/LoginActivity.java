package com.tamer.raed.doctorappointment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tamer.raed.doctorappointment.patient.ui.activities.PatientDashboardActivity;

public class LoginActivity extends AppCompatActivity {
    private TextView tvSignUp;
    private Button login_btn;
    private TextInputEditText et_email, et_password;
    private String email, password;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private ImageView doctor, patient;
    private boolean isDoctor = true;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDoctor = true;
                doctor.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_account));
                patient.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.not_selected_account));
            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDoctor = false;
                patient.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.selected_account));
                doctor.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.not_selected_account));
            }
        });
        tvSignUp.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, WelcomeActivity.class)));
        login_btn.setOnClickListener(view -> {
            if (checkFields()) {
                login();
            }

        });
    }

    private boolean checkFields() {
        if (!TextUtils.isEmpty(et_email.getText().toString())) {
            email = et_email.getText().toString();
            if (!TextUtils.isEmpty(et_password.getText().toString())) {
                password = et_password.getText().toString();
                return true;
            } else {
                et_password.setError(getString(R.string.password_error));
            }
        } else {
            et_email.setError(getString(R.string.email_error));
        }
        return false;
    }

    private void initViews() {
        tvSignUp = findViewById(R.id.login_tv_sign_up);
        login_btn = findViewById(R.id.login_btn_login);
        et_email = findViewById(R.id.login_et_email);
        et_password = findViewById(R.id.login_et_password);
        progressBar = findViewById(R.id.login_progressBar);
        doctor = findViewById(R.id.doctor_type);
        patient = findViewById(R.id.patient_type);
    }

    private void login() {
        login_btn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    db = FirebaseFirestore.getInstance();

                    progressBar.setVisibility(View.GONE);
                    login_btn.setVisibility(View.VISIBLE);
                    startActivity(new Intent(LoginActivity.this, PatientDashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.login_error), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    login_btn.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}