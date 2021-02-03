package com.tamer.raed.doctorappointment.patient.ui.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tamer.raed.doctorappointment.R;

public class UpdateProfileDialogFragment extends DialogFragment implements View.OnClickListener {

    private final OnButtonClickListener onButtonClickListener;

    public UpdateProfileDialogFragment(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setCancelable(false);
        Button btn_update, btn_cancel;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.update_profile_dialog_fragment, null, false);
        btn_update = dialogView.findViewById(R.id.btn_dialog_update);
        btn_cancel = dialogView.findViewById(R.id.btn_dialog_cancel);

        btn_cancel.setOnClickListener(this);
        btn_update.setOnClickListener(this);

        return builder
                .setView(dialogView)
                .create();

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_update:
                onButtonClickListener.onButtonClick(true);
                dismiss();
                break;
            case R.id.btn_dialog_cancel:
                onButtonClickListener.onButtonClick(false);
                dismiss();
                break;
        }
    }

    public interface OnButtonClickListener {
        void onButtonClick(boolean state);
    }
}
