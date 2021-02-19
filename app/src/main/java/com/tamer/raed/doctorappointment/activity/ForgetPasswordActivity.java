package com.tamer.raed.doctorappointment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.tamer.raed.doctorappointment.R;
import com.tapadoo.alerter.Alerter;

public class ForgetPasswordActivity extends AppCompatActivity {
    private TextInputEditText et_email;
    private ProgressBar progressBar;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        et_email = findViewById(R.id.et_email);
        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.button);
    }

    public void sendEmail(View view) {
        progressBar.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);

        if (!TextUtils.isEmpty(et_email.getText().toString())) {
            String email = et_email.getText().toString();
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Alerter.create(this)
                                    .setText(getString(R.string.check_email))
                                    .setTitle(getString(R.string.email_send))
                                    .setDuration(5000)
                                    .setBackgroundColorRes(R.color.teal_200)
                                    .show();
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }, 5000);
                        }
                    });
        } else {
            et_email.setError(getString(R.string.email_error));
        }
        progressBar.setVisibility(View.GONE);
        button.setVisibility(View.VISIBLE);
    }
}