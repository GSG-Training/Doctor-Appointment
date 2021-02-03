package com.tamer.raed.doctorappointment.patient.ui.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.patient.ui.activities.UpdateProfileDialogFragment;

public class PatientMyProfileFragment extends Fragment {
    private ImageView imageView;
    private ImageButton imageButton;
    private TextInputEditText et_username, et_phone, et_password;
    private RadioGroup radioGroup;
    private RadioButton rb_male, rb_female;
    private TextView tv_gender;
    private Button btn_update_profile;
    private String gender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        setHasOptionsMenu(true);
        imageView = view.findViewById(R.id.patient_my_profile_image_view);
        imageButton = view.findViewById(R.id.patient_my_profile_img_btn_change_image);
        et_username = view.findViewById(R.id.patient_my_profile_et_username);
        et_phone = view.findViewById(R.id.patient_my_profile_et_phone);
        radioGroup = view.findViewById(R.id.patient_my_profile_radio_group);
        rb_male = view.findViewById(R.id.patient_my_profile_rb_male);
        rb_female = view.findViewById(R.id.patient_my_profile_rb_female);
        et_password = view.findViewById(R.id.patient_my_profile_et_password);
        tv_gender = view.findViewById(R.id.patient_my_profile_tv_gender);
        btn_update_profile = view.findViewById(R.id.patient_my_profile_btn_update_profile);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: change image profile
            }
        });

        btn_update_profile.setOnClickListener(view1 -> {
            if (!TextUtils.isEmpty(et_username.getText().toString())) {
                if (!TextUtils.isEmpty(et_phone.getText().toString())) {
                    setGender();
                    if (!TextUtils.isEmpty(gender)) {
                        if (!TextUtils.isEmpty(et_password.getText().toString())) {
                            showDialog();
                        } else {
                            et_password.setError(getString(R.string.password_error));
                        }
                    } else {
                        Toast.makeText(getContext(), "You must choose your gender!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    et_phone.setError(getString(R.string.phone_error));
                }
            } else {
                et_username.setError(getString(R.string.username_error));
            }
        });

        return view;
    }

    private void showDialog() {
        DialogFragment newFragment = new UpdateProfileDialogFragment(state -> {
            if (state) {
                updateProfile();
            } else
                Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();

        });
        if (getFragmentManager() != null) {
            newFragment.show(getFragmentManager(), "dialog");
        }
    }

    public void updateProfile() {
        // TODO: Update profile
    }

    @SuppressLint("NonConstantResourceId")
    private void setGender() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        switch (radioId) {
            case R.id.patient_my_profile_rb_male:
                gender = getString(R.string.male);
                break;
            case R.id.patient_my_profile_rb_female:
                gender = getString(R.string.female);
                break;
            default:
                gender = null;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.patient_my_profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_edit_item) {
            imageButton.setVisibility(View.VISIBLE);
            btn_update_profile.setVisibility(View.VISIBLE);
            et_username.setFocusableInTouchMode(true);
            et_phone.setFocusableInTouchMode(true);
            et_password.setFocusableInTouchMode(true);
            radioGroup.setVisibility(View.VISIBLE);
            tv_gender.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);
    }
}