package com.tamer.raed.doctorappointment.patient.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Doctor;
import com.tamer.raed.doctorappointment.patient.adapter.DoctorAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView rv;
    private List<Doctor> doctors;
    private DoctorAdapter doctorAdapter;
    private ProgressBar progressBar;
    private List<Doctor> temp;
    private Toolbar toolbar;
    private SearchView searchView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();

        setupToolbar();

        fillListDoctors();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressBar.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
                temp = new ArrayList<>();
                for (int i = 0; i < doctors.size(); i++) {
                    if (doctors.get(i).getUsername().contains(query)) {
                        temp.add(doctors.get(i));
                    }
                }
                doctorAdapter = new DoctorAdapter(temp,
                        doctor -> {
                            Intent intent = new Intent(getApplicationContext(), DoctorDetailsActivity.class);
                            intent.putExtra("Doctor", doctor);
                            startActivity(intent);
                        }, getApplicationContext());

                rv.setAdapter(doctorAdapter);
                progressBar.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                progressBar.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
                temp = new ArrayList<>();
                for (int i = 0; i < doctors.size(); i++) {
                    if (doctors.get(i).getUsername().contains(newText)) {
                        temp.add(doctors.get(i));
                    }
                }
                doctorAdapter = new DoctorAdapter(temp,
                        doctor -> {
                            Intent intent = new Intent(getApplicationContext(), DoctorDetailsActivity.class);
                            intent.putExtra("Doctor", doctor);
                            startActivity(intent);
                        }, getApplicationContext());


                if (temp.size() > 0) {
                    rv.setAdapter(doctorAdapter);
                    progressBar.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
                return true;
            }
        });

    }

    private void initViews() {
        progressBar = findViewById(R.id.search_progressBar);
        toolbar = findViewById(R.id.patient_search_toolbar);
        searchView = findViewById(R.id.patient_searchView);
        rv = findViewById(R.id.patient_search_rv);
        textView = findViewById(R.id.search_tv_no_doctor);
        doctors = new ArrayList<>();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow));
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    public void back(View view) {
        onBackPressed();
    }

    private void fillListDoctors() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Doctors")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Doctor doctor = document.toObject(Doctor.class);
                            doctors.add(doctor);
                        }
                    } else {
                        Toast.makeText(this, getString(R.string.general_error), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}