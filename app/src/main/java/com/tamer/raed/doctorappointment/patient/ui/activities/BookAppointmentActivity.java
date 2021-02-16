package com.tamer.raed.doctorappointment.patient.ui.activities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Doctor;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BookAppointmentActivity extends AppCompatActivity {
    private Doctor doctor;
    private TextView tv_username, tv_specialization, tv_day_start, tv_day_end, tv_hour_start, tv_hour_end;
    private ImageView imageView;
    private TextView tv_time;

    public static void addTime(String a, String b) {
        int minSum = 0;
        int hourSum = 0;

        int hour1 = Integer.parseInt(a.substring(0, 2));
        int hour2 = Integer.parseInt(b.substring(0, 2));

        int min1 = Integer.parseInt(a.substring(3, 5));
        int min2 = Integer.parseInt(b.substring(3, 5));

        minSum = min1 + min2;

        if (minSum > 59) {
            hourSum += 1;
            minSum %= 60;
        }

        hourSum = hourSum + hour1 + hour2;

        System.out.println(hourSum + " Hours : " + minSum + " minutes");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        initViews();

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("Doctor")) {
            doctor = intent.getParcelableExtra("Doctor");
            tv_username.setText(doctor.getUsername());
            tv_specialization.setText(doctor.getSpecialization());
            tv_day_start.setText(doctor.getStartDayWork());
            tv_day_end.setText(doctor.getEndDayWork());
            tv_hour_start.setText(doctor.getStartHourWork());
            tv_hour_end.setText(doctor.getEndHourWork());
        }

        getImageProfile();

        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerTimeline datePickerTimeline = findViewById(R.id.datePickerTimeline);
        datePickerTimeline.setInitialDate(year, month, day);

        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                System.out.println("Year " + year + "    Month  " + month + "      Day  " + day);
            }

            @Override
            public void onDisabledDateSelected(int year, int month, int day, int dayOfWeek, boolean isDisabled) {
                System.out.println("Year " + year + "    Month  " + month + "      Day  " + day);
            }
        });

//        Date[] dates = {Calendar.getInstance().getTime()};
//        datePickerTimeline.deactivateDates(dates);
    }

    private void initViews() {
        imageView = findViewById(R.id.patient_book_appointment_image_view);
        tv_username = findViewById(R.id.patient_book_appointment_tv_username);
        tv_specialization = findViewById(R.id.patient_book_appointment_tv_specialization);
        tv_day_start = findViewById(R.id.patient_book_appointment_tv_day_start);
        tv_day_end = findViewById(R.id.patient_book_appointment_tv_day_end);
        tv_hour_start = findViewById(R.id.patient_book_appointment_tv_time_start);
        tv_hour_end = findViewById(R.id.patient_book_appointment_tv_time_end);
        tv_time = findViewById(R.id.book_appointment_tv_time);
    }

    public void backToDetailsActivity(View view) {
        onBackPressed();
    }

    public void confirmAppointment(View view) {
        if (checkFields()) {

        }
    }

    private boolean checkFields() {
        return false;
    }

    public void chooseTime(View view) {
        setTimeFromTimePicker(this, tv_time);
    }

    private void getImageProfile() {
        imageView.setVisibility(View.GONE);
        String userId = doctor.getId();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        storageReference.child("profileImages/" + userId).getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    Glide.with(getApplicationContext()).load(uri).into(imageView);
                    imageView.setVisibility(View.VISIBLE);
                })
                .addOnFailureListener(exception -> {
                    imageView.setImageResource(R.drawable.ic_user_account);
                    Toast.makeText(getApplicationContext(), getString(R.string.image_error), Toast.LENGTH_SHORT).show();
                });

    }

    public void setTimeFromTimePicker(Context context, TextView textView) {
        int hourOfDay, minute;
        final Calendar c = Calendar.getInstance();
        hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (timePicker, hourOfDay1, minute1) -> {
            Time time = new Time(hourOfDay1, minute1, 0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
            String s = simpleDateFormat.format(time);
            textView.setText(s);
        }, hourOfDay, minute, false);

        timePickerDialog.show();
    }
}