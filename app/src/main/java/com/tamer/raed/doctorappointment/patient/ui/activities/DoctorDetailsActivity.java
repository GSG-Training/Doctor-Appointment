package com.tamer.raed.doctorappointment.patient.ui.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Doctor;

public class DoctorDetailsActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView tv_username, tv_specialization, tv_number_of_patient,
            tv_rating, tv_experience, tv_address, tv_phone, tv_biography;
    private Doctor doctor;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        initViews();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Doctor")) {
            doctor = intent.getParcelableExtra("Doctor");
            tv_username.setText(doctor.getUsername());
            tv_specialization.setText(doctor.getSpecialization());
            tv_number_of_patient.setText(String.valueOf(doctor.getNumberOfPatient()));
            tv_rating.setText(String.valueOf(doctor.getRating()));
            tv_experience.setText(String.valueOf(doctor.getExperience()));
            tv_address.setText(doctor.getCountry() + "-" + doctor.getCity() + "-" + doctor.getStreet());
            tv_phone.setText(doctor.getPhone());
            tv_biography.setText(doctor.getBiography());
        }
    }

    private void initViews() {
        imageView = findViewById(R.id.patient_doctor_details_image_view);
        tv_username = findViewById(R.id.patient_doctor_details_tv_username);
        tv_specialization = findViewById(R.id.patient_doctor_details_tv_specialization);
        tv_number_of_patient = findViewById(R.id.patient_doctor_details_tv_number_of_patient);
        tv_rating = findViewById(R.id.patient_doctor_details_tv_rating);
        tv_experience = findViewById(R.id.patient_doctor_details_tv_experience);
        tv_address = findViewById(R.id.patient_doctor_details_tv_address);
        tv_phone = findViewById(R.id.patient_doctor_details_tv_phone);
        tv_biography = findViewById(R.id.patient_doctor_details_tv_biography);
    }

    public void bookAnAppointment(View view) {
        Intent intent = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class);
        intent.putExtra("Doctor", doctor);
        startActivity(intent);
    }

    public void backToHomepage(View view) {
        onBackPressed();
    }
}