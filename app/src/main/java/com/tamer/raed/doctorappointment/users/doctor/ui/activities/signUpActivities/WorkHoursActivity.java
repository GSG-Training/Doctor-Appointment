package com.tamer.raed.doctorappointment.users.doctor.ui.activities.signUpActivities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.activity.LoginActivity;
import com.tapadoo.alerter.Alerter;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class WorkHoursActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView start_hour_tv, end_hour_tv;
    private Spinner start_day_spinner, end_day_spinner;
    private String start_day, end_day, start_hour, end_hour;
    private ProgressBar progressBar;
    private Group group;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_hours);

        initViews();

        setupToolbar();

        userId = getIntent().getStringExtra("id");

    }

    private void initViews() {
        start_day_spinner = findViewById(R.id.start_day_spinner);
        end_day_spinner = findViewById(R.id.end_day_spinner);
        start_hour_tv = findViewById(R.id.start_hour_tv);
        end_hour_tv = findViewById(R.id.end_hour_tv);
        toolbar = findViewById(R.id.sign_up_toolbar);
        progressBar = findViewById(R.id.workingHourProgressBar);
        group = findViewById(R.id.workingHourGroup);
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
        if (start_day_spinner.getSelectedItemPosition() == 0) {
            Alerter.create(this)
                    .setText(getString(R.string.start_day_error))
                    .setDuration(5000)
                    .setBackgroundColorRes(R.color.teal_200)
                    .show();
            return false;
        } else if (end_day_spinner.getSelectedItemPosition() == 0) {
            Alerter.create(this)
                    .setText(getString(R.string.end_day_error))
                    .setDuration(5000)
                    .setBackgroundColorRes(R.color.teal_200)
                    .show();
            return false;
        } else if (start_day_spinner.getSelectedItemPosition() == end_day_spinner.getSelectedItemPosition()) {
            Alerter.create(this)
                    .setText(getString(R.string.work_day_error))
                    .setDuration(5000)
                    .setBackgroundColorRes(R.color.teal_200)
                    .show();
            return false;
        } else if (!TextUtils.isEmpty(start_hour_tv.getText().toString())) {
            start_day = start_day_spinner.getSelectedItem().toString();
            end_day = end_day_spinner.getSelectedItem().toString();
            start_hour = start_hour_tv.getText().toString();
            if (!TextUtils.isEmpty(end_hour_tv.getText().toString())) {
                end_hour = end_hour_tv.getText().toString();
                return true;
            } else {
                Alerter.create(this)
                        .setText(getString(R.string.end_hour_error))
                        .setDuration(5000)
                        .setBackgroundColorRes(R.color.teal_200)
                        .show();
                return false;
            }
        } else {
            Alerter.create(this)
                    .setText(getString(R.string.start_hour_error))
                    .setDuration(5000)
                    .setBackgroundColorRes(R.color.teal_200)
                    .show();
            return false;
        }

    }

    public void back(View view) {
        onBackPressed();
    }

    public void selectStartHour(View view) {
        setTimeFromTimePicker(this, start_hour_tv);
    }

    public void selectEndHour(View view) {
        setTimeFromTimePicker(this, end_hour_tv);
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

    private void signUpFirebase() {
        group.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> map = new HashMap<>();
        map.put("startDayWork", start_day);
        map.put("endDayWork", end_day);
        map.put("startHourWork", start_hour);
        map.put("endHourWork", end_hour);
        map.put("numberOfPatient", 0);

        db.collection("Doctors").document(userId).set(map, SetOptions.merge()).addOnCompleteListener(task1 -> {
            if (task1.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                group.setVisibility(View.VISIBLE);
                Intent intent = new Intent(WorkHoursActivity.this, LoginActivity.class);
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