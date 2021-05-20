package com.example.technicalassignment.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.technicalassignment.R;
import com.example.technicalassignment.room.scheduleroom.ScheduleEntity;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {
    private List<ScheduleEntity> listdata=new ArrayList<>();
    private Context context;

    public ScheduleAdapter(List<ScheduleEntity> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_schedule, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ScheduleEntity employeeEntity = listdata.get(position);
        holder.tv_date.setText(listdata.get(position).getDate());
        holder.tv_title.setText(listdata.get(position).getTitle());
        holder.tv_details.setText(listdata.get(position).getDetails());
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_date;
        public TextView tv_title;
        public TextView tv_details;


        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_date = (TextView) itemView.findViewById(R.id.tv_date);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.tv_details = (TextView) itemView.findViewById(R.id.tv_details);

        }
    }
}