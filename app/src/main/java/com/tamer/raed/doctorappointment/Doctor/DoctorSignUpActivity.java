package com.tamer.raed.doctorappointment.Doctor;

import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tamer.raed.doctorappointment.Doctor.SignUpFragments.AddressFragment;
import com.tamer.raed.doctorappointment.Doctor.SignUpFragments.LoginDataFragment;
import com.tamer.raed.doctorappointment.Doctor.SignUpFragments.SpecializationFragment;
import com.tamer.raed.doctorappointment.Doctor.SignUpFragments.WorkHoursFragment;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.WelcomeActivity;

import java.util.Objects;

public class DoctorSignUpActivity extends AppCompatActivity {
    static int position = 0;
    FrameLayout frameLayout;
    Button next;
    Button back;
    LoginDataFragment loginDataFragment;
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(DoctorSignUpActivity.this, R.color.white));// set status background white

        initViews();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow));
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        initFragments();
        moveFragment(loginDataFragment);

        back.setOnClickListener(view -> {
            position--;
            Toast.makeText(this, position + " ", Toast.LENGTH_SHORT).show();
            switch (position) {
                case 0:
                    notDoneStep(text_view_specialization, view1, tv_specialization, loginDataFragment);
                    break;
                case 1:
                    notDoneStep(text_view_address, view1, tv_address, specializationFragment);
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
        loginDataFragment = new LoginDataFragment();
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
        Toast.makeText(this, position + " ", Toast.LENGTH_SHORT).show();
    }

    public void notDoneStep(TextView step, View view, TextView textView, Fragment fragment) {
        view.setBackgroundColor(getResources().getColor(R.color.light_gray));
        step.setBackground(ContextCompat.getDrawable(this, R.drawable.circle_bg));
        textView.setTextColor(getResources().getColor(R.color.light_gray));
        moveFragment(fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();
        position = 0;
    }
}