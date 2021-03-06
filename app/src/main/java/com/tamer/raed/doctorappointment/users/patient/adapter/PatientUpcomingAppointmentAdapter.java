package com.tamer.raed.doctorappointment.users.patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Appointment;
import com.tamer.raed.doctorappointment.model.Doctor;

import java.util.List;

public class PatientUpcomingAppointmentAdapter extends RecyclerView.Adapter<PatientUpcomingAppointmentAdapter.UpcomingAppointmentViewHolder> {
    private final List<Appointment> appointments;
    private final OnItemClickListener onItemClickListener;

    public PatientUpcomingAppointmentAdapter(List<Appointment> appointments, OnItemClickListener onItemClickListener) {
        this.appointments = appointments;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public UpcomingAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_layout, parent, false);
        return new PatientUpcomingAppointmentAdapter.UpcomingAppointmentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingAppointmentViewHolder holder, int position) {
        holder.bind(appointments.get(position));
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public interface OnItemClickListener {
        void onMoreImageButtonClick(View view, int position);
    }

    class UpcomingAppointmentViewHolder extends RecyclerView.ViewHolder {
        ImageButton imageButton;
        TextView tv_doctor_username, tv_specialization, tv_month, tv_day, tv_time, tv_doctor_phone;

        public UpcomingAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton = itemView.findViewById(R.id.item_appointment_img_btn_more);
            tv_doctor_username = itemView.findViewById(R.id.item_appointment_doctor_name);
            tv_specialization = itemView.findViewById(R.id.item_appointment_specialization);
            tv_month = itemView.findViewById(R.id.item_appointment_month);
            tv_day = itemView.findViewById(R.id.item_appointment_day);
            tv_time = itemView.findViewById(R.id.item_appointment_time);
            tv_doctor_phone = itemView.findViewById(R.id.item_appointment_phone);

            imageButton.setOnClickListener(view -> onItemClickListener.onMoreImageButtonClick(view, getBindingAdapterPosition()));

        }

        private void bind(Appointment appointment) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("Doctors").document(appointment.getDoctorId());
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Doctor doctor = document.toObject(Doctor.class);
                        if (doctor != null) {
                            tv_doctor_username.setText(doctor.getUsername());
                            tv_doctor_phone.setText(doctor.getPhone());
                            tv_specialization.setText(doctor.getSpecialization());
                        }
                    }
                }
            });
            tv_day.setText(String.valueOf(appointment.getDay()));
            tv_month.setText(appointment.getMonth());
            tv_time.setText(appointment.getTime());


        }
    }
}
