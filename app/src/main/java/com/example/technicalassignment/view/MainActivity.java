package com.example.technicalassignment.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.technicalassignment.R;
import com.example.technicalassignment.view.adapter.EmployeeAdapter;
import com.example.technicalassignment.room.employeeroom.EmployeeEntity;
import com.example.technicalassignment.viewmodel.PublicHolidayViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PublicHolidayViewModel viewModel;
    private RecyclerView recyclerView;
    private String year = "";
    private String countryName = "";
    private EmployeeAdapter adapter;
    private List<EmployeeEntity> employeeEntities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(MainActivity.this).get(PublicHolidayViewModel.class);
        viewModel.deleteAllEmployees();
        recyclerView = (RecyclerView) findViewById(R.id.rv_employees);
        //loads data manually during first launch
        loadEmployeesManually();
    }

    private void loadEmployeesManually() {
        List<EmployeeEntity> employeeEntities = new ArrayList<>();
        employeeEntities.add(new EmployeeEntity("Australia", "John", "101"));
        employeeEntities.add(new EmployeeEntity("Australia", "Chris", "102"));
        employeeEntities.add(new EmployeeEntity("Australia", "Jenny", "103"));
        employeeEntities.add(new EmployeeEntity("Indonesia", "Larissa", "201"));
        employeeEntities.add(new EmployeeEntity("Indonesia", "Marius", "201"));
        employeeEntities.add(new EmployeeEntity("Indonesia", "Rina", "201"));
        employeeEntities.add(new EmployeeEntity("Colombia", "Julian", "301"));
        viewModel.insertEmployeesToRoom(employeeEntities);
        setAdapter();
    }

    private void setAdapter() {
        employeeEntities = viewModel.getAllEmployeesFromRoom();
        adapter = new EmployeeAdapter(employeeEntities, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //removes data when the app comes back to foreground and reloads it manually againn
        viewModel.deleteAllEmployees();
        loadEmployeesManually();

    }
}