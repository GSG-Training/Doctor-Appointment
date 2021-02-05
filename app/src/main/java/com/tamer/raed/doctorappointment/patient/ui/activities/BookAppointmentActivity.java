package com.tamer.raed.doctorappointment.patient.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Doctor;
import com.tamer.raed.doctorappointment.patient.adapter.HoursAdapter;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class BookAppointmentActivity extends AppCompatActivity {
    private Doctor doctor;
    private TextView tv_username, tv_specialization, tv_day_start, tv_day_end, tv_hour_start, tv_hour_end;
    private ImageView imageView;
    private RecyclerView hour_rv;
    private List<String> hours;
    private HoursAdapter hoursAdapter;

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

        hours = new ArrayList<>();
        fillHoursList();
        hoursAdapter = new HoursAdapter(hours, new HoursAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

        hour_rv.setAdapter(hoursAdapter);
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
        hour_rv = findViewById(R.id.patient_book_appointment_hours_rv);
    }

    public void backToDetailsActivity(View view) {
        onBackPressed();
    }

    private void fillHoursList() {
        hours.add("9:00 Am");
        hours.add("10:00 Am");
        hours.add("11:00 Am");
        hours.add("12:00 pm");
        hours.add("01:00 pm");
        hours.add("02:00 pm");
        hours.add("03:00 pm");
        hours.add("04:00 pm");
    }

    public void confirmAppointment(View view) {
    }
}