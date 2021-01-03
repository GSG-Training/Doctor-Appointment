package com.tamer.raed.doctorappointment.doctor.ui.signUpFragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.doctor.ui.DoctorSignUpActivity;
import com.tamer.raed.doctorappointment.model.SharedPrefs;

import java.util.HashMap;

public class SpecializationFragment extends Fragment {
    TextInputEditText specialization_et, biography_et, experience_et;
    Button next;
    String specialization, biography, experience;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_specialization, container, false);
        specialization_et = view.findViewById(R.id.specialization_et);
        biography_et = view.findViewById(R.id.biography_et);
        experience_et = view.findViewById(R.id.experience_et);

        HashMap<String, String> hashMap = SharedPrefs.getSpecializationData(getContext());
        if (!hashMap.isEmpty()) {
            biography = hashMap.get(SharedPrefs.BIOGRAPHY_KEY);
            experience = hashMap.get(SharedPrefs.EXPERIENCE_KEY);
            specialization = hashMap.get(SharedPrefs.SPECIALIZATION_KEY);
            if (biography != null && experience != null && specialization != null) {
                experience_et.setText(experience);
                biography_et.setText(biography);
                specialization_et.setText(specialization);
            }
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            next = getActivity().findViewById(R.id.sign_up_btn_next);
            if (next != null) {
                next.setOnClickListener(view -> {
                    if (!TextUtils.isEmpty(specialization_et.getText().toString())) {
                        specialization = specialization_et.getText().toString();
                        if (!TextUtils.isEmpty(experience_et.getText().toString())) {
                            experience = experience_et.getText().toString();
                            if (!TextUtils.isEmpty(biography_et.getText().toString())) {
                                biography = biography_et.getText().toString();
                                SharedPrefs.setSpecializationData(getContext(), specialization, biography, experience);
                                TextView text_view_address = getActivity().findViewById(R.id.sign_up_text_view_address);
                                View view2 = getActivity().findViewById(R.id.sign_up_view2);
                                TextView tv_address = getActivity().findViewById(R.id.sign_up_tv_address);
                                AddressFragment addressFragment = new AddressFragment();
                                ((DoctorSignUpActivity) getActivity()).doneStep(text_view_address, view2, tv_address, addressFragment);
                            } else {
                                experience_et.setError("experience must be enter!!");
                            }

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