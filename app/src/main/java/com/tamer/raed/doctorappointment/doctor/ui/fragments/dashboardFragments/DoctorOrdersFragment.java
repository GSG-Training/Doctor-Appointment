package com.tamer.raed.doctorappointment.doctor.ui.fragments.dashboardFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.doctor.adapters.DoctorUpcomingAppointmentAdapter;
import com.tamer.raed.doctorappointment.model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class DoctorOrdersFragment extends Fragment {

    private List<Appointment> appointments;
    private DoctorUpcomingAppointmentAdapter doctorUpcomingAppointmentAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_recent_appointments, container, false);
        recyclerView = view.findViewById(R.id.rv_recent_appointments);
        appointments = new ArrayList<>();
        fillAppointments();

        doctorUpcomingAppointmentAdapter = new DoctorUpcomingAppointmentAdapter(appointments, new DoctorUpcomingAppointmentAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                // TODO: GO to new activity
            }

            @SuppressLint("NonConstantResourceId")
            @Override
            public void onMoreImageButtonClick(View view, int position) {
                PopupMenu popup = new PopupMenu(getContext(), view);
                popup.getMenuInflater().inflate(R.menu.order_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(menuItem -> {
                    int id = menuItem.getItemId();
                    switch (id) {
                        case R.id.order_cancel:
                            appointments.remove(position);
                            doctorUpcomingAppointmentAdapter.notifyDataSetChanged();
                            break;
                        case R.id.order_accept:
                            // TODO: accept order
                            break;
                    }
                    return true;
                });
                popup.show();
            }
        });
        recyclerView.setAdapter(doctorUpcomingAppointmentAdapter);
        return view;
    }

    private void fillAppointments() {
        appointments.add(new Appointment(0));
        appointments.add(new Appointment(0));
        appointments.add(new Appointment(0));
        appointments.add(new Appointment(0));
        appointments.add(new Appointment(0));
        appointments.add(new Appointment(0));
        appointments.add(new Appointment(0));
        appointments.add(new Appointment(0));
        appointments.add(new Appointment(0));
    }
}