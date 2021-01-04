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
import com.tamer.raed.doctorappointment.doctor.ui.DoctorSignUpActivity;
import com.tamer.raed.doctorappointment.model.SharedPrefs;

import java.util.HashMap;
import java.util.Objects;

public class AddressFragment extends Fragment {
    TextInputEditText country_et, city_et, street_et;
    Button next;
    String country, city, street;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        country_et = view.findViewById(R.id.country_et);
        city_et = view.findViewById(R.id.city_et);
        street_et = view.findViewById(R.id.street_et);

        HashMap<String, String> hashMap = SharedPrefs.getAddressData(getContext());
        if (!hashMap.isEmpty()) {
            country = hashMap.get(SharedPrefs.COUNTRY_KEY);
            city = hashMap.get(SharedPrefs.CITY_KEY);
            street = hashMap.get(SharedPrefs.STREET_KEY);
            if (country != null && city != null && street != null) {
                country_et.setText(country);
                city_et.setText(city);
                street_et.setText(street);
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
                    if (!Objects.requireNonNull(country_et.getText()).toString().isEmpty()) {
                        country = country_et.getText().toString();
                        if (!Objects.requireNonNull(city_et.getText()).toString().isEmpty()) {
                            city = city_et.getText().toString();
                            if (!Objects.requireNonNull(street_et.getText()).toString().isEmpty()) {
                                street = street_et.getText().toString();
                                SharedPrefs.setAddressData(getContext(), country, city, street);
                                TextView text_view_work_hour = getActivity().findViewById(R.id.sign_up_text_view_work_hour);
                                View view3 = getActivity().findViewById(R.id.sign_up_view3);
                                TextView tv_work_hour = getActivity().findViewById(R.id.sign_up_tv_work_hour);
                                WorkHoursFragment workHoursFragment = new WorkHoursFragment();
                                ((DoctorSignUpActivity) getActivity()).doneStep(text_view_work_hour, view3, tv_work_hour, workHoursFragment);
                            } else {
                                street_et.setError("street must be enter!!");
                            }
                        } else {
                            city_et.setError("city must be enter!!");
                        }
                    } else {
                        country_et.setError("country must be enter!!");
                    }
                });
            }
        }
    }

}