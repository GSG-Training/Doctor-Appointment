package com.tamer.raed.doctorappointment.users.patient.ui.activities;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.common.collect.ImmutableBiMap;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Appointment;
import com.tamer.raed.doctorappointment.model.Doctor;
import com.tapadoo.alerter.Alerter;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import com.vivekkaushik.datepicker.OnDateSelectedListener;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class BookAppointmentActivity extends AppCompatActivity {
    private Doctor doctor;
    private TextView tv_username, tv_specialization, tv_day_start, tv_day_end, tv_hour_start, tv_hour_end;
    private ImageView imageView;
    private ProgressBar progressBar;
    private Button confirm;
    private TextView tv_time;
    private Calendar calendar;
    private String dayString;
    private String monthString;
    private String yearString;
    private String timeString;
    private String firstDay;
    private String endDay;
    private int intDay;

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
            firstDay = tv_day_start.getText().toString();
            endDay = tv_day_end.getText().toString();
        }

        getImageProfile();

        Date date = new Date();
        calendar = new GregorianCalendar();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerTimeline datePickerTimeline = findViewById(R.id.datePickerTimeline);
        datePickerTimeline.setInitialDate(year, month, day);

        datePickerTimeline.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int dayOfWeek) {
                intDay = day;
                setDateString(year, month, day);
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
        progressBar = findViewById(R.id.bookAppointmentProgressBar);
        confirm = findViewById(R.id.patient_book_appointment_btn_confirm);
    }

    public void backToDetailsActivity(View view) {
        onBackPressed();
    }

    public void confirmAppointment(View view) {
        if (checkFields()) {
            if (checkIsValidDay(getDayInNumber())) {
                if (checkIsValidTime(timeString)) {
                    setAppointmentToFirebase();
                } else {
                    Alerter.create(this)
                            .setText(getString(R.string.time_not_valid))
                            .setDuration(3000)
                            .setBackgroundColorRes(R.color.teal_200)
                            .show();
                }
            } else {
                Alerter.create(this)
                        .setText(getString(R.string.day_not_valid))
                        .setDuration(3000)
                        .setBackgroundColorRes(R.color.teal_200)
                        .show();
            }
        }
    }

    private void setAppointmentToFirebase() {
        progressBar.setVisibility(View.VISIBLE);
        confirm.setVisibility(View.GONE);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String patientId = firebaseUser.getUid();
        String doctorId = doctor.getId();
        String month = monthString.substring(0, 3);
        Appointment appointment = new Appointment(doctorId, patientId, intDay, month, yearString, timeString);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Orders").document(doctorId).collection("doctorOrders").document(patientId).set(appointment).addOnCompleteListener(task2 -> {
            if (task2.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                confirm.setVisibility(View.VISIBLE);
                Alerter.create(this)
                        .setTitle(R.string.success_book_appointment_title)
                        .setText(getString(R.string.success_book_appointment))
                        .setDuration(5000)
                        .setBackgroundColorRes(R.color.teal_200)
                        .show();

                Handler handler = new Handler();
                handler.postDelayed(() -> {
                    Intent intent = new Intent(BookAppointmentActivity.this, PatientDashboardActivity.class);
                    startActivity(intent);
                    finish();
                }, 5000);
            } else {
                Alerter.create(this)
                        .setText(getString(R.string.general_error))
                        .setDuration(3000)
                        .setBackgroundColorRes(R.color.teal_200)
                        .show();
            }
        });
    }

    private boolean checkFields() {
        if (dayString == null || monthString == null || yearString == null) {
            Alerter.create(this)
                    .setText(getString(R.string.date_error))
                    .setDuration(3000)
                    .setBackgroundColorRes(R.color.teal_200)
                    .show();
            return false;
        } else if (!TextUtils.isEmpty(tv_time.getText().toString())) {
            timeString = tv_time.getText().toString();
            return true;
        } else {
            Alerter.create(this)
                    .setText(getString(R.string.time_error))
                    .setDuration(3000)
                    .setBackgroundColorRes(R.color.teal_200)
                    .show();
            return false;
        }
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
                .addOnFailureListener(exception -> imageView.setImageResource(R.drawable.ic_user_account));

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

    private void setDateString(int year, int month, int day) {
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.set(year, month, day, 0, 0, 0);
        dayString = String.format("%tA", calendar);
        monthString = String.format("%tB", calendar);
        yearString = String.valueOf(year);
    }

    @SuppressLint("SimpleDateFormat")
    private boolean checkIsValidTime(String time) {
        String startTime = tv_hour_start.getText().toString();
        String endTime = tv_hour_end.getText().toString();
        try {
            Date time1 = new SimpleDateFormat("hh:mm aa").parse(startTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);
            calendar1.add(Calendar.DATE, 1);


            Date time2 = new SimpleDateFormat("hh:mm aa").parse(endTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            Date d = new SimpleDateFormat("hh:mm aa").parse(time);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            return x.after(calendar1.getTime()) && x.before(calendar2.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }


    private boolean checkIsValidDay(int day) {

        final ImmutableBiMap<String, Integer> DaysToNo =
                new ImmutableBiMap.Builder<String, Integer>()
                        .put("Saturday", 1)
                        .put("Sunday", 2)
                        .put("Monday", 3)
                        .put("Tuesday", 4)
                        .put("Wednesday", 5)
                        .put("Thursday", 6)
                        .put("Friday", 7)
                        .build();

        int startDateInNo = DaysToNo.get(firstDay);
        int endDateInNo = DaysToNo.get(endDay);
        return day <= endDateInNo && day >= startDateInNo;
    }

    private int getDayInNumber() {
        switch (dayString) {
            case "Saturday":
                return 1;
            case "Sunday":
                return 2;
            case "Monday":
                return 3;
            case "Tuesday":
                return 4;
            case "Wednesday":
                return 5;
            case "Thursday":
                return 6;
            case "Friday":
                return 7;
            default:
                return 0;
        }
    }
}