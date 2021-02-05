package com.tamer.raed.doctorappointment.patient.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tamer.raed.doctorappointment.R;

import java.util.List;

public class HoursAdapter extends RecyclerView.Adapter<HoursAdapter.HourViewHolder> {
    private final List<String> hours;
    private final OnItemClickListener onItemClickListener;

    public HoursAdapter(List<String> hours, OnItemClickListener onItemClickListener) {
        this.hours = hours;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hour, parent, false);
        return new HourViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HourViewHolder holder, int position) {
        holder.bind(hours.get(position));
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class HourViewHolder extends RecyclerView.ViewHolder {
        TextView tv_hour;

        public HourViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_hour = itemView.findViewById(R.id.item_hour_tv);
        }

        private void bind(String hour) {
            tv_hour.setText(hour);
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(getBindingAdapterPosition()));
        }
    }


}
