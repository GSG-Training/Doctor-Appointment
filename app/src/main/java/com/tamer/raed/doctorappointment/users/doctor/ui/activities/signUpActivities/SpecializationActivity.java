package com.tamer.raed.doctorappointment.users.doctor.ui.activities.signUpActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.tamer.raed.doctorappointment.R;
import com.tapadoo.alerter.Alerter;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SpecializationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText biography_et, experience_et;
    private Spinner specializationSpinner;
    private ProgressBar progressBar;
    private Group group;
    private String specialization, biography;
    private int experience;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialization);

        initViews();

        setupToolbar();

        userId = getIntent().getStringExtra("id");
    }

    private void initViews() {
        specializationSpinner = findViewById(R.id.specialization_spinner);
        biography_et = findViewById(R.id.biography_et);
        experience_et = findViewById(R.id.experience_et);
        toolbar = findViewById(R.id.sign_up_toolbar);
        progressBar = findViewById(R.id.specializationProgressBar);
        group = findViewById(R.id.specializationGroup);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow));
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    public void next(View view) {
        if (checkFields()) {
            signUpFirebase();
        }
    }

    private boolean checkFields() {
        if (specializationSpinner.getSelectedItemPosition() > 0) {
            specialization = specializationSpinner.getSelectedItem().toString();
            if (!TextUtils.isEmpty(experience_et.getText().toString())) {
                experience = Integer.parseInt(experience_et.getText().toString());
                if (!TextUtils.isEmpty(biography_et.getText().toString())) {
                    biography = biography_et.getText().toString();
                    return true;
                } else {
                    biography_et.setError(getString(R.string.biography_error));
                    return false;
                }
            } else {
                experience_et.setError(getString(R.string.experience_error));
                return false;
            }
        } else {
            Alerter.create(this)
                    .setText(getString(R.string.specialization_error))
                    .setDuration(5000)
                    .setBackgroundColorRes(R.color.teal_200)
                    .show();
            return false;
        }
    }

    public void back(View view) {
        onBackPressed();
    }

    private void signUpFirebase() {
        group.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> map = new HashMap<>();
        map.put("specialization", specialization);
        map.put("biography", biography);
        map.put("experience", experience);

        db.collection("Doctors").document(userId).set(map, SetOptions.merge()).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                group.setVisibility(View.VISIBLE);
                Intent intent = new Intent(SpecializationActivity.this, AddressActivity.class);
                intent.putExtra("id", userId);
                startActivity(intent);
            } else {
                Alerter.create(this)
                        .setText(getString(R.string.error_sign_up))
                        .setDuration(5000)
                        .setBackgroundColorRes(R.color.teal_200)
                        .show();
            }
        });
    }
}