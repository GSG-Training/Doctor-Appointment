package com.tamer.raed.doctorappointment.users.doctor.ui.fragments.dashboardFragments;

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
import com.tamer.raed.doctorappointment.users.doctor.adapters.DoctorUpcomingAppointmentAdapter;
import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;
import java.util.List;

public class DoctorRecentAppointmentsFragment extends Fragment {

    private List<Appointment> appointments;
    private DoctorUpcomingAppointmentAdapter doctorUpcomingAppointmentAdapter;
    private ProgressBar progressBar;
    private TextView textView;
    private String doctorId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_recent_appointments, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_recent_appointments);
        progressBar = view.findViewById(R.id.appointmentsProgressBar);
        textView = view.findViewById(R.id.appointments_state_tv);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        doctorId = firebaseUser.getUid();

        appointments = new ArrayList<>();
        fillAppointments();


        doctorUpcomingAppointmentAdapter = new DoctorUpcomingAppointmentAdapter(appointments, (view1, position) -> {
            PopupMenu popup = new PopupMenu(getContext(), view1);
            popup.getMenuInflater().inflate(R.menu.recent_appointment_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(menuItem -> {
                int id = menuItem.getItemId();
                if (id == R.id.delete_item) {
                    cancelAppointment(position);
                    appointments.remove(position);
                    doctorUpcomingAppointmentAdapter.notifyDataSetChanged();
                    if (appointments.size() == 0) {
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(getString(R.string.no_orders));
                    }
                }
                return true;
            });
            popup.show();
        });
        recyclerView.setAdapter(doctorUpcomingAppointmentAdapter);
        return view;
    }

    private void cancelAppointment(int position) {
        Appointment appointment = appointments.get(position);
        String patientId = appointment.getPatientId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("DoctorRecentAppointments").document(doctorId).collection("Appointments").document(patientId).delete().addOnCompleteListener(task3 -> {
            if (task3.isSuccessful()) {
                Alerter.create(getActivity())
                        .setText(getString(R.string.delete_appointment))
                        .setDuration(3000)
                        .setBackgroundColorRes(R.color.teal_200)
                        .show();
            } else {
                Alerter.create(getActivity())
                        .setText(getString(R.string.general_error))
                        .setDuration(3000)
                        .setBackgroundColorRes(R.color.teal_200)
                        .show();
            }
        });
    }

    private void fillAppointments() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DoctorRecentAppointments").document(doctorId).collection("Appointments")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            Appointment appointment = snapshot.toObject(Appointment.class);
                            appointments.add(appointment);
                            doctorUpcomingAppointmentAdapter.notifyDataSetChanged();
                        }
                    } else {
                        doctorUpcomingAppointmentAdapter.notifyDataSetChanged();
                        if (appointments.size() == 0) {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(getString(R.string.no_orders));
                        }
                    }
                });
        progressBar.setVisibility(View.GONE);
    }
}