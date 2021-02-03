package com.tamer.raed.doctorappointment.patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tamer.raed.doctorappointment.R;
import com.tamer.raed.doctorappointment.model.Appointment;

import java.util.List;

public class PatientUpcomingAppointmentAdapter extends RecyclerView.Adapter<PatientUpcomingAppointmentAdapter.UpcomingAppointmentViewHolder> {
    private List<Appointment> appointments;
    private OnItemClickListener onItemClickListener;

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
        void onClick(int position);

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

            itemView.setOnClickListener(view -> onItemClickListener.onClick(getBindingAdapterPosition()));
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onMoreImageButtonClick(view, getBindingAdapterPosition());
                }
            });

        }

        private void bind(Appointment appointment) {
            // TODO: get the data from firebase using doctor appointment from appointment object

        }
    }
}
