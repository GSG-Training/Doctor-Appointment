package com.tamer.raed.doctorappointment.users.doctor.ui.activities.signUpActivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

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

public class AddressActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText country_et, city_et, street_et;
    private String country, city, street;
    private ProgressBar progressBar;
    private Group group;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        initViews();
        setupToolbar();

        userId = getIntent().getStringExtra("id");
    }

    private void initViews() {
        country_et = findViewById(R.id.country_et);
        city_et = findViewById(R.id.city_et);
        street_et = findViewById(R.id.street_et);
        toolbar = findViewById(R.id.sign_up_toolbar);
        progressBar = findViewById(R.id.addressProgressBar);
        group = findViewById(R.id.addressGroup);
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
        if (!TextUtils.isEmpty(country_et.getText().toString())) {
            country = country_et.getText().toString();
            if (!TextUtils.isEmpty(city_et.getText().toString())) {
                city = city_et.getText().toString();
                if (!TextUtils.isEmpty(street_et.getText().toString())) {
                    street = street_et.getText().toString();
                    return true;
                } else {
                    street_et.setError(getString(R.string.street_error));
                    return false;
                }
            } else {
                city_et.setError(getString(R.string.city_error));
                return false;
            }
        } else {
            country_et.setError(getString(R.string.country_error));
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
        map.put("country", country);
        map.put("city", city);
        map.put("street", street);

        db.collection("Doctors").document(userId).set(map, SetOptions.merge()).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                group.setVisibility(View.VISIBLE);
                Intent intent = new Intent(AddressActivity.this, WorkHoursActivity.class);
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