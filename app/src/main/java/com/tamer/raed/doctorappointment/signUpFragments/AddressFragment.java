package com.tamer.raed.doctorappointment.signUpFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.SignUpActivity;

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
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            next = getActivity().findViewById(R.id.sign_up_btn_next);
            if (next != null) {
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!Objects.requireNonNull(country_et.getText()).toString().isEmpty()) {
                            country = country_et.getText().toString();
                            if (!Objects.requireNonNull(city_et.getText()).toString().isEmpty()) {
                                city = city_et.getText().toString();
                                if (!Objects.requireNonNull(street_et.getText()).toString().isEmpty()) {
                                    street = street_et.getText().toString();
                                    RadioButton rb_work_hour = getActivity().findViewById(R.id.sign_up_rb_work_hour);
                                    View view3 = getActivity().findViewById(R.id.sign_up_view3);
                                    TextView tv_work_hour = getActivity().findViewById(R.id.sign_up_tv_work_hour);
                                    WorkHoursFragment workHoursFragment = new WorkHoursFragment();
                                    ((SignUpActivity) getActivity()).doneStep(rb_work_hour, view3, tv_work_hour, workHoursFragment);
                                } else {
                                    street_et.setError("street must be enter!!");
                                }
                            } else {
                                city_et.setError("city must be enter!!");
                            }
                        } else {
                            country_et.setError("country must be enter!!");
                        }
                    }
                });
            }
        }
    }

}