package com.tamer.raed.doctorappointment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.tamer.raed.doctorappointment.Doctor.DoctorSignUpActivity;
import com.tamer.raed.doctorappointment.Patient.PatientSignUpActivity;

public class WelcomeActivity extends AppCompatActivity {
    private Button btnSignUp;
    private TextView tvLogin;
    private Spinner spinner;
    private String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        }
        getWindow().setStatusBarColor(ContextCompat.getColor(WelcomeActivity.this, R.color.white));// set status background white
        initViews();

        tvLogin.setOnClickListener(view -> startActivity(new Intent(WelcomeActivity.this, LoginActivity.class)));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                accountType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnSignUp.setOnClickListener(view -> {
            switch (spinner.getSelectedItemPosition()) {
                case 0:
                    Toast.makeText(WelcomeActivity.this, getText(R.string.account_type_error), Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    startActivity(new Intent(WelcomeActivity.this, DoctorSignUpActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(WelcomeActivity.this, PatientSignUpActivity.class));
                    break;
            }

        });

    }

    private void initViews() {
        btnSignUp = findViewById(R.id.welcome_btn_sign_up);
        tvLogin = findViewById(R.id.welcome_tv);
        spinner = findViewById(R.id.account_type_spinner);
    }
}