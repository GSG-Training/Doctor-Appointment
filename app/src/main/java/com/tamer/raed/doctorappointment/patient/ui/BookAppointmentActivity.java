package com.tamer.raed.doctorappointment.patient.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Doctor;

public class BookAppointmentActivity extends AppCompatActivity {
    private Doctor doctor;
    private TextView tv_username, tv_specialization;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        initViews();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Doctor")) {
            doctor = intent.getParcelableExtra("Doctor");
            tv_username.setText(doctor.getUsername());
            tv_specialization.setText(doctor.getCategory().getName());
        }
    }

    private void initViews() {
        imageView = findViewById(R.id.patient_book_appointment_image_view);
        tv_username = findViewById(R.id.patient_book_appointment_tv_username);
        tv_specialization = findViewById(R.id.patient_book_appointment_tv_specialization);
    }

    public void backToDetailsActivity(View view) {
        onBackPressed();
    }
}