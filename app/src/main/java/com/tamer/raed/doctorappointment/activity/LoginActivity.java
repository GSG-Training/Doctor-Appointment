package com.tamer.raed.doctorappointment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.users.doctor.ui.activities.DoctorDashboardActivity;
import com.tamer.raed.doctorappointment.users.patient.ui.activities.PatientDashboardActivity;
import com.tapadoo.alerter.Alerter;

public class LoginActivity extends AppCompatActivity {
    private TextView tvSignUp;
    private Button login_btn;
    private TextInputEditText et_email, et_password;
    private String email, password;
    private ProgressBar progressBar;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

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
    }

    private void login() {
        login_btn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (checkIfEmailVerified()) {
                    db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("Users").document(firebaseUser.getUid());
                    docRef.get().addOnCompleteListener(task2 -> {
                        if (task2.isSuccessful()) {
                            DocumentSnapshot document = task2.getResult();
                            if (document.exists()) {
                                String accountType = (String) document.get("accountType");
                                if (accountType.equalsIgnoreCase("doctor")) {
                                    progressBar.setVisibility(View.GONE);
                                    login_btn.setVisibility(View.VISIBLE);
                                    startActivity(new Intent(LoginActivity.this, DoctorDashboardActivity.class));
                                    finish();
                                } else if (accountType.equalsIgnoreCase("patient")) {
                                    progressBar.setVisibility(View.GONE);
                                    login_btn.setVisibility(View.VISIBLE);
                                    startActivity(new Intent(LoginActivity.this, PatientDashboardActivity.class));
                                    finish();
                                }
                            }
                        } else {
                            Alerter.create(this)
                                    .setText(getString(R.string.general_error))
                                    .setDuration(3000)
                                    .setBackgroundColorRes(R.color.teal_200)
                                    .show();
                            progressBar.setVisibility(View.GONE);
                            login_btn.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    Alerter.create(this)
                            .setTitle(getString(R.string.verify_email))
                            .setText(getString(R.string.check_email))
                            .setDuration(5000)
                            .setBackgroundColorRes(R.color.teal_200)
                            .show();
                    firebaseUser.sendEmailVerification()
                            .addOnCompleteListener(task1 -> {

                            });
                    progressBar.setVisibility(View.GONE);
                    login_btn.setVisibility(View.VISIBLE);
                }

            } else {
                Alerter.create(this)
                        .setText(getString(R.string.login_error))
                        .setDuration(5000)
                        .setBackgroundColorRes(R.color.teal_200)
                        .show();
                progressBar.setVisibility(View.GONE);
                login_btn.setVisibility(View.VISIBLE);
            }
        });
    }

    public void forgetPassword(View view) {
        startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
    }

    private boolean checkIfEmailVerified() {
        return firebaseUser.isEmailVerified();
    }
}