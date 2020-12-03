package com.tamer.raed.doctorappointment;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.tamer.raed.doctorappointment.signUpFragments.AddressFragment;
import com.tamer.raed.doctorappointment.signUpFragments.LoginDataFragment;
import com.tamer.raed.doctorappointment.signUpFragments.SpecializationFragment;
import com.tamer.raed.doctorappointment.signUpFragments.WorkHoursFragment;

public class SignUpActivity extends AppCompatActivity {
    static int position = 1;
    FrameLayout frameLayout;
    Button next;
    Button clear;
    LoginDataFragment loginDataFragment;
    SpecializationFragment specializationFragment;
    AddressFragment addressFragment;
    WorkHoursFragment workHoursFragment;
    RadioButton rb_login_data, rb_specialization, rb_address, rb_work_hour;
    View view1, view2, view3;
    TextView tv_specialization, tv_address, tv_work_hour;
    ObjectAnimator objectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
        initFragments();
        moveFragment(loginDataFragment);


        next.setOnClickListener(view -> {
            switch (position) {
                case 0:
                    moveFragment(loginDataFragment);
                    position++;
                    break;
                case 1:
                    doneStep(rb_specialization, view1, tv_specialization, specializationFragment);
                    position++;
                    break;
                case 2:
                    doneStep(rb_address, view2, tv_address, addressFragment);
                    position++;
                    break;
                case 3:
                    next.setText(getString(R.string.sign_up));
                    doneStep(rb_work_hour, view3, tv_work_hour, workHoursFragment);
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
        frameLayout = findViewById(R.id.frameLayout);
        next = findViewById(R.id.sign_up_btn_next);
        clear = findViewById(R.id.sign_up_btn_clear);
        rb_login_data = findViewById(R.id.sign_up_rb_login_data);
        rb_specialization = findViewById(R.id.sign_up_rb_specialization);
        rb_address = findViewById(R.id.sign_up_rb_address);
        rb_work_hour = findViewById(R.id.sign_up_rb_work_hour);
        tv_specialization = findViewById(R.id.sign_up_tv_specialization);
        tv_address = findViewById(R.id.sign_up_tv_address);
        tv_work_hour = findViewById(R.id.sign_up_tv_work_hour);
        view1 = findViewById(R.id.sign_up_view1);
        view2 = findViewById(R.id.sign_up_view2);
        view3 = findViewById(R.id.sign_up_view3);
        objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.fill_view_animation);
    }

    public void moveFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in,
                android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
    }

    public void doneStep(RadioButton radioButton, View view, TextView textView, Fragment fragment) {
        objectAnimator.setTarget(view);
        objectAnimator.start();
        radioButton.setChecked(true);
        textView.setTextColor(getResources().getColor(R.color.colorPrimary));
        moveFragment(fragment);

    }
}