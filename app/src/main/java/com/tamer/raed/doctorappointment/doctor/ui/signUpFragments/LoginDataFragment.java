package com.tamer.raed.doctorappointment.doctor.ui.signUpFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.doctor.DoctorSignUpActivity;

import java.util.Objects;

public class LoginDataFragment extends Fragment {

    String username, phone, password;
    TextInputEditText et_username, et_phone, et_password;
    Button next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_data, container, false);
        et_username = view.findViewById(R.id.login_data_username);
        et_phone = view.findViewById(R.id.login_data_phone);
        et_password = view.findViewById(R.id.login_data_password);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            next = getActivity().findViewById(R.id.sign_up_btn_next);
            if (next != null) {
                next.setOnClickListener(view -> {
                    if (!Objects.requireNonNull(et_username.getText()).toString().isEmpty()) {
                        username = et_username.getText().toString();
                        if (!Objects.requireNonNull(et_phone.getText()).toString().isEmpty()) {
                            phone = et_phone.getText().toString();
                            if (!Objects.requireNonNull(et_password.getText()).toString().isEmpty()) {
                                password = et_password.getText().toString();
                                TextView text_view_specialization = getActivity().findViewById(R.id.sign_up_text_view_specialization);
                                View view1 = getActivity().findViewById(R.id.sign_up_view1);
                                TextView tv_specialization = getActivity().findViewById(R.id.sign_up_tv_specialization);
                                SpecializationFragment specializationFragment = new SpecializationFragment();
                                ((DoctorSignUpActivity) getActivity()).doneStep(text_view_specialization, view1, tv_specialization, specializationFragment);
                            } else {
                                et_password.setError("Password must be enter!!");
                            }
                        } else {
                            et_phone.setError("Phone must be enter!!");
                        }
                    } else {
                        et_username.setError("Username must be enter!!");
                    }
                });
            }
        }
    }
}