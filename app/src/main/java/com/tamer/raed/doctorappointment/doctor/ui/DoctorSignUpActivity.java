package com.tamer.raed.doctorappointment.doctor.ui;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.WelcomeActivity;
import com.tamer.raed.doctorappointment.doctor.ui.signUpFragments.AddressFragment;
import com.tamer.raed.doctorappointment.doctor.ui.signUpFragments.PersonalDataFragment;
import com.tamer.raed.doctorappointment.doctor.ui.signUpFragments.SpecializationFragment;
import com.tamer.raed.doctorappointment.doctor.ui.signUpFragments.WorkHoursFragment;

import java.util.Objects;

public class DoctorSignUpActivity extends AppCompatActivity {
    static int position = 0;
    FrameLayout frameLayout;
    Button next;
    Button back;
    PersonalDataFragment personalDataFragment;
    SpecializationFragment specializationFragment;
    AddressFragment addressFragment;
    WorkHoursFragment workHoursFragment;
    TextView text_view_login_data, text_view_specialization, text_view_address, text_view_work_hour;
    View view1, view2, view3;
    TextView tv_specialization, tv_address, tv_work_hour;
    TransitionDrawable doneStep;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_sign_up);
        initViews();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow));
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        initFragments();
        if (savedInstanceState != null) {
            position = savedInstanceState.getInt("position");
            Log.d("dddd", "not null" + position);

        } else {
            Log.d("dddd", "null" + position);
            moveFragment(personalDataFragment);
        }

        back.setOnClickListener(view -> {
            position--;
            switch (position) {
                case 0:
                    notDoneStep(text_view_specialization, view1, tv_specialization, personalDataFragment);
                    break;
                case 1:
                    notDoneStep(text_view_address, view2, tv_address, specializationFragment);
                    break;
                case 2:
                    notDoneStep(text_view_work_hour, view3, tv_work_hour, addressFragment);
                    break;
                default:
                    startActivity(new Intent(DoctorSignUpActivity.this, WelcomeActivity.class));
                    finish();
                    break;
            }
        });
    }

    private void initFragments() {
        personalDataFragment = new PersonalDataFragment();
        specializationFragment = new SpecializationFragment();
        addressFragment = new AddressFragment();
        workHoursFragment = new WorkHoursFragment();
    }

    private void initViews() {
        toolbar = findViewById(R.id.sign_up_toolbar);
        frameLayout = findViewById(R.id.frameLayout);
        next = findViewById(R.id.sign_up_btn_next);
        back = findViewById(R.id.sign_up_btn_back);
        text_view_login_data = findViewById(R.id.sign_up_text_view_login_data);
        text_view_specialization = findViewById(R.id.sign_up_text_view_specialization);
        text_view_address = findViewById(R.id.sign_up_text_view_address);
        text_view_work_hour = findViewById(R.id.sign_up_text_view_work_hour);
        tv_specialization = findViewById(R.id.sign_up_tv_specialization);
        tv_address = findViewById(R.id.sign_up_tv_address);
        tv_work_hour = findViewById(R.id.sign_up_tv_work_hour);
        view1 = findViewById(R.id.sign_up_view1);
        view2 = findViewById(R.id.sign_up_view2);
        view3 = findViewById(R.id.sign_up_view3);
        doneStep = (TransitionDrawable) ContextCompat.getDrawable(this, R.drawable.transition_done_step);

    }

    public void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
    }

    public void doneStep(TextView step, View view, TextView textView, Fragment fragment) {
        view.setBackground(doneStep);
        doneStep.startTransition(1500);
        step.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_active_bg));
        step.setTextColor(getResources().getColor(R.color.white));
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        moveFragment(fragment);
        position++;
    }

    public void notDoneStep(TextView step, View view, TextView textView, Fragment fragment) {
        view.setBackgroundColor(getResources().getColor(R.color.gray));
        textView.setTextColor(getResources().getColor(R.color.gray));
        step.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_bg));
        step.setTextColor(getResources().getColor(R.color.dark_gray));
        moveFragment(fragment);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }
}