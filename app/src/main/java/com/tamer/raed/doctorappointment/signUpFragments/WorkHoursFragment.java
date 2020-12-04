package com.tamer.raed.doctorappointment.signUpFragments;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.tamer.raed.doctorappointment.R;

public class WorkHoursFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {
    private static String timeString;
    TextInputEditText time_et;
    TextView start_hour_tv, end_hour_tv;
    Button next, btn_choose_start_time, btn_choose_end_time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_hours, container, false);
        start_hour_tv = view.findViewById(R.id.start_hour_tv);
        end_hour_tv = view.findViewById(R.id.end_hour_tv);
        time_et = view.findViewById(R.id.time_et);
        btn_choose_start_time = view.findViewById(R.id.choose_start_time);
        btn_choose_end_time = view.findViewById(R.id.choose_end_time);

        btn_choose_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();

            }
        });
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

                    }
                });
            }
        }
    }

    private void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        if (getActivity() != null) {
            newFragment.show(getActivity().getFragmentManager(), "timePicker");
        }
    }

//    @Override
//    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
//        setTimeString(hourOfDay, minute, 0);
//        Toast.makeText(getContext(), "Time: "+timeString, Toast.LENGTH_SHORT).show();
//        start_hour_tv.setText(timeString);
//    }

    private void setTimeString(int hourOfDay, int minute, int mili) {

        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;

        if (minute < 10)
            min = "0" + minute;

        timeString = hour + ":" + min + ":00";
        Toast.makeText(getContext(), " " + timeString, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        Log.d("dddd", timeString);
        setTimeString(i, i1, 0);
        Toast.makeText(getContext(), "Time: " + timeString, Toast.LENGTH_SHORT).show();
        start_hour_tv.setText(timeString);
        Log.d("dddd", timeString);

    }
}