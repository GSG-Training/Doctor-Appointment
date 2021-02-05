package com.tamer.raed.doctorappointment.doctor.ui.signUpFragments;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.tamer.raed.doctorappointment.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WorkHoursFragment extends Fragment {
    private TextInputEditText time_et;
    private TextView start_hour_tv, end_hour_tv;
    private Button next, btn_choose_start_time, btn_choose_end_time;
    private Spinner start_day_spinner, end_day_spinner;
    private String start_day, end_day, start_hour, end_hour, time_for_each_case;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work_hours, container, false);
        start_day_spinner = view.findViewById(R.id.start_day_spinner);
        end_day_spinner = view.findViewById(R.id.end_day_spinner);

        start_hour_tv = view.findViewById(R.id.start_hour_tv);
        end_hour_tv = view.findViewById(R.id.end_hour_tv);
        time_et = view.findViewById(R.id.time_et);
        btn_choose_start_time = view.findViewById(R.id.choose_start_time);
        btn_choose_end_time = view.findViewById(R.id.choose_end_time);

        btn_choose_start_time.setOnClickListener(view1 -> setTimeFromTimePicker(getContext(), start_hour_tv));
        btn_choose_end_time.setOnClickListener(view1 -> setTimeFromTimePicker(getContext(), end_hour_tv));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity() != null) {
            next = getActivity().findViewById(R.id.sign_up_btn_next);
            if (next != null) {
                next.setOnClickListener(view -> {
                    if (start_day_spinner.getSelectedItemPosition() == 0) {
                        Toast.makeText(getContext(), getText(R.string.start_day_error), Toast.LENGTH_LONG).show();
                    } else if (end_day_spinner.getSelectedItemPosition() == 0) {
                        Toast.makeText(getContext(), getText(R.string.end_day_error), Toast.LENGTH_LONG).show();
                    } else if (start_day_spinner.getSelectedItemPosition() == end_day_spinner.getSelectedItemPosition()) {
                        Toast.makeText(getContext(), getText(R.string.work_day_error), Toast.LENGTH_LONG).show();
                    } else if (!TextUtils.isEmpty(start_hour_tv.getText().toString())) {
                        start_day = start_day_spinner.getSelectedItem().toString();
                        end_day = end_day_spinner.getSelectedItem().toString();
                        start_hour = start_hour_tv.getText().toString();
                        if (!TextUtils.isEmpty(end_hour_tv.getText().toString())) {
                            end_hour = end_hour_tv.getText().toString();
                            if (!TextUtils.isEmpty(time_et.getText().toString())) {
                                time_for_each_case = time_et.getText().toString();
                                Log.d("dddd", "Start day: " + start_day + "\nEnd Day: " + end_day + "\nStart hour: " + start_hour + "\nEnd hour: " + end_hour + "\nTime: " + time_for_each_case);
                            } else {
                                time_et.setError(getString(R.string.time_case_error));
                                // TODO: go to doctor homepage

//                                  startActivity(new Intent(WorkHoursFragment.this, ));
                            }
                        } else {
                            end_hour_tv.setError(getString(R.string.end_hour_error));
                        }

                    } else {
                        start_hour_tv.setError(getString(R.string.start_hour_error));
                    }
                });
            }
        }
    }

    public void setTimeFromTimePicker(Context context, TextView textView) {
        int hourOfDay, minute;
        final Calendar c = Calendar.getInstance();
        hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (timePicker, hourOfDay1, minute1) -> {
            Time time = new Time(hourOfDay1, minute1, 0);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
            String s = simpleDateFormat.format(time);
            textView.setText(s);
        }, hourOfDay, minute, false);

        timePickerDialog.show();
    }
}