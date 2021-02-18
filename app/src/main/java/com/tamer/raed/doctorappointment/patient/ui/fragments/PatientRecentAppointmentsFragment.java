package com.tamer.raed.doctorappointment.patient.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Appointment;
import com.tamer.raed.doctorappointment.patient.adapter.PatientUpcomingAppointmentAdapter;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;

public class PatientRecentAppointmentsFragment extends Fragment {
    private List<Appointment> appointments;
    private PatientUpcomingAppointmentAdapter patientUpcomingAppointmentAdapter;
    private ProgressBar progressBar;
    private TextView textView;
    private String patientId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_recent_appointments, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_recent_appointments);
        appointments = new ArrayList<>();
        progressBar = view.findViewById(R.id.appointmentsProgressBar);
        textView = view.findViewById(R.id.appointments_state_tv);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        patientId = firebaseUser.getUid();
        fillAppointments();

        patientUpcomingAppointmentAdapter = new PatientUpcomingAppointmentAdapter(appointments, (view1, position) -> {
            PopupMenu popup = new PopupMenu(getContext(), view1);
            popup.getMenuInflater().inflate(R.menu.recent_appointment_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(menuItem -> {
                int id = menuItem.getItemId();
                if (id == R.id.delete_item) {
                    cancelAppointment(position);
                    appointments.remove(position);
                    patientUpcomingAppointmentAdapter.notifyDataSetChanged();
                    if (appointments.size() == 0) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(getString(R.string.no_orders));
                    }
                }
                return true;
            });
            popup.show();
        });
        recyclerView.setAdapter(patientUpcomingAppointmentAdapter);
        return view;
    }

    private void cancelAppointment(int position) {
        Appointment appointment = appointments.get(position);
        String patientId = appointment.getPatientId();
        String doctorId = appointment.getDoctorId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("PatientRecentAppointments").document(patientId).collection("Appointments").document(doctorId).delete().addOnCompleteListener(task3 -> {
            if (task3.isSuccessful()) {
                Alerter.create(getActivity())
                        .setText(getString(R.string.delete_appointment))
                        .setDuration(5000)
                        .setBackgroundColorRes(R.color.purple_700)
                        .show();
            } else {
                Alerter.create(getActivity())
                        .setText(getString(R.string.general_error))
                        .setDuration(5000)
                        .setBackgroundColorRes(R.color.purple_700)
                        .show();
            }
        });
    }

    private void fillAppointments() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("PatientRecentAppointments").document(patientId).collection("Appointments")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            Appointment appointment = snapshot.toObject(Appointment.class);
                            appointments.add(appointment);
                            patientUpcomingAppointmentAdapter.notifyDataSetChanged();
                        }
                    } else {
                        patientUpcomingAppointmentAdapter.notifyDataSetChanged();
                        if (appointments.size() == 0) {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(getString(R.string.no_orders));
                        }
                    }
                });
        progressBar.setVisibility(View.GONE);
    }
}