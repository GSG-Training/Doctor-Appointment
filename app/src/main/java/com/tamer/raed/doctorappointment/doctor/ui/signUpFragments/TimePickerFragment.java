package com.tamer.raed.doctorappointment.doctor.ui.signUpFragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    TimePickerDialog.OnTimeSetListener onTimeSetListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onTimeSetListener = (TimePickerDialog.OnTimeSetListener) getParentFragment();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return
        return new TimePickerDialog(getActivity(), onTimeSetListener, hour, minute, false);

    }

}