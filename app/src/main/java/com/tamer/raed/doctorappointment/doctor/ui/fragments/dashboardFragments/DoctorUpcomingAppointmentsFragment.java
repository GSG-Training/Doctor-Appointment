package com.tamer.raed.doctorappointment.doctor.ui.fragments.dashboardFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.doctor.adapters.DoctorUpcomingAppointmentAdapter;
import com.tamer.raed.doctorappointment.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class DoctorUpcomingAppointmentsFragment extends Fragment {
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
            popup.getMenuInflater().inflate(R.menu.upcoming_appointment_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(menuItem -> {
                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.cancel_item:
                        cancelAppointment(position);
                        appointments.remove(position);
                        doctorUpcomingAppointmentAdapter.notifyDataSetChanged();
                        if (appointments.size() == 0) {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(getString(R.string.no_orders));
                        }
                        break;
                    case R.id.done_item:
                        setAppointmentToFirebase(position);
                        appointments.remove(position);
                        doctorUpcomingAppointmentAdapter.notifyDataSetChanged();
                        if (appointments.size() == 0) {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(getString(R.string.no_orders));
                        }
                        break;
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

        db.collection("DoctorAppointments").document(doctorId).collection("Appointments").document(patientId).delete().addOnCompleteListener(task3 -> {
            if (task3.isSuccessful()) {
                Toast.makeText(getContext(), getString(R.string.cancel_success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
            }
        });

        db.collection("PatientAppointments").document(patientId).collection("Appointments").document(doctorId).delete().addOnCompleteListener(task3 -> {
            if (task3.isSuccessful()) {
                Toast.makeText(getContext(), getString(R.string.cancel_success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillAppointments() {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("DoctorAppointments").document(doctorId).collection("Appointments")
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

    private void setAppointmentToFirebase(int position) {
        Appointment appointment = appointments.get(position);
        String patientId = appointment.getPatientId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("DoctorRecentAppointments").document(doctorId).collection("Appointments").document(patientId).set(appointment).addOnCompleteListener(task2 -> {
            if (task2.isSuccessful()) {
                db.collection("DoctorAppointments").document(doctorId).collection("Appointments").document(patientId).delete().addOnCompleteListener(task3 -> {
                    if (task3.isSuccessful()) {
                        Toast.makeText(getContext(), getString(R.string.done), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
            }
        });

        db.collection("PatientRecentAppointments").document(patientId).collection("Appointments").document(doctorId).set(appointment).addOnCompleteListener(task4 -> {
            if (task4.isSuccessful()) {
                db.collection("PatientAppointments").document(patientId).collection("Appointments").document(doctorId).delete().addOnCompleteListener(task3 -> {
                    if (task3.isSuccessful()) {
                        Toast.makeText(getContext(), getString(R.string.done), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getContext(), getString(R.string.general_error), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

