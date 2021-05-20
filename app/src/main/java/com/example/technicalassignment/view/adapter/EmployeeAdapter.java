package com.example.technicalassignment.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.technicalassignment.Constants;
import com.example.technicalassignment.R;
import com.example.technicalassignment.room.employeeroom.EmployeeEntity;
import com.example.technicalassignment.view.CalendarActivity;
import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    private List<EmployeeEntity> listdata=new ArrayList<>();
    private Context context;

    public EmployeeAdapter(List<EmployeeEntity> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_employee, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final EmployeeEntity employeeEntity = listdata.get(position);
        holder.tv_emp.setText(listdata.get(position).getName());
        holder.tv_emp_id.setText(listdata.get(position).getEmployeeId());
        holder.tv_country.setText(listdata.get(position).getCountry());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CalendarActivity.class);
                intent.putExtra(Constants.COUNTRYCODE, employeeEntity.getCountry());
                intent.putExtra(Constants.EMPLOYEENAME,employeeEntity.getName());
                intent.putExtra(Constants.EMPLOYEE_ID,employeeEntity.getEmployeeId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_emp;
        public TextView tv_emp_id;
        public TextView tv_country;
        public ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_emp = (TextView) itemView.findViewById(R.id.tv_employee);
            this.tv_emp_id = (TextView) itemView.findViewById(R.id.tv_emp_id);
            this.tv_country = (TextView) itemView.findViewById(R.id.tv_country);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.parent);
        }
    }
}