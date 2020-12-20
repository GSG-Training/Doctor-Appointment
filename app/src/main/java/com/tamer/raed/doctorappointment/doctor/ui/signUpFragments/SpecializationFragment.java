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

public class SpecializationFragment extends Fragment {
    TextInputEditText specialization_et, biography_et;
    Button next;
    String specialization, biography;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specialization, container, false);
        specialization_et = view.findViewById(R.id.specialization_et);
        biography_et = view.findViewById(R.id.biography_et);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            next = getActivity().findViewById(R.id.sign_up_btn_next);
            if (next != null) {
                next.setOnClickListener(view -> {
                    if (!Objects.requireNonNull(specialization_et.getText()).toString().isEmpty()) {
                        specialization = specialization_et.getText().toString();
                        if (!Objects.requireNonNull(biography_et.getText()).toString().isEmpty()) {
                            biography = biography_et.getText().toString();
                            TextView text_view_address = getActivity().findViewById(R.id.sign_up_text_view_address);
                            View view2 = getActivity().findViewById(R.id.sign_up_view2);
                            TextView tv_address = getActivity().findViewById(R.id.sign_up_tv_address);
                            AddressFragment addressFragment = new AddressFragment();
                            ((DoctorSignUpActivity) getActivity()).doneStep(text_view_address, view2, tv_address, addressFragment);
                        } else {
                            biography_et.setError("biography must be enter!!");
                        }
                    } else {
                        specialization_et.setError("specialization must be enter!!");
                    }
                });
            }
        }
    }
}