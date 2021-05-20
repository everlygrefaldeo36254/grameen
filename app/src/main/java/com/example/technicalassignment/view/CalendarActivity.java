package com.example.technicalassignment.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.technicalassignment.Constants;
import com.example.technicalassignment.R;
import com.example.technicalassignment.object.PublicHolidays;
import com.example.technicalassignment.room.publicholidaysroom.PublicHolidayEntity;
import com.example.technicalassignment.room.scheduleroom.ScheduleEntity;
import com.example.technicalassignment.utils.GLOBALURL;
import com.example.technicalassignment.utils.NetworkUtil;
import com.example.technicalassignment.view.adapter.ScheduleAdapter;
import com.example.technicalassignment.viewmodel.PublicHolidayViewModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CalendarActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private PublicHolidayViewModel viewModel;
    private String countryId = "";
    private String employeeName = "";
    private TextView tv_empName;
    private EditText et_title;
    private EditText et_details;
    private Button btn_submit;
    private String currentDateString = "";
    private TextView tv_sched_list;
    private String employee_id;
    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;
    private List<ScheduleEntity> scheduleEntities = new ArrayList<>();
    private ProgressBar progress_bar;
    private TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel = new ViewModelProvider(CalendarActivity.this).get(PublicHolidayViewModel.class);
        //initialize views
        recyclerView = (RecyclerView) findViewById(R.id.rv_sched);
        tv_empName = (TextView) findViewById(R.id.tv_empName);
        et_title = (EditText) findViewById(R.id.et_title);
        et_details = (EditText) findViewById(R.id.et_details);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_sched_list = (TextView) findViewById(R.id.tv_sched_list);
        progress_bar.setVisibility(View.GONE);
        //initialize listener
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateEnties();
            }
        });

        //getting data from previous activity
        if (getIntent().getExtras() != null) {
            countryId = getIntent().getExtras().getString(Constants.COUNTRYCODE);
            employeeName = getIntent().getExtras().getString(Constants.EMPLOYEENAME);
            employee_id = getIntent().getExtras().getString(Constants.EMPLOYEE_ID);
            tv_empName.setText("Setting up an appointment with " + employeeName + " from " + countryId + ".");
            countryId = getCountryCode(countryId);
            tv_sched_list.setText(employeeName + "'s schedule");
            tv_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment datePicker = new DatePickerFragment();
                    datePicker.show(getSupportFragmentManager(), "date picker");
                }
            });
            //sets data to employee recyclerview
            viewModel.getAllScheduleSingleEmployee(employee_id);
            setAdapter();

        } else {
            //removes views if no extras detected
            tv_empName.setText("No extras detected.");
            et_title.setVisibility(View.GONE);
            et_details.setVisibility(View.GONE);
            btn_submit.setVisibility(View.GONE);
        }
    }

    private void validateEnties() {
        if (et_title.getText().toString().isEmpty()) {
            et_title.setHintTextColor(Color.parseColor("#FF0000"));
        } else if (et_details.getText().toString().isEmpty()) {
            et_details.setHintTextColor(Color.parseColor("#FF0000"));
        } else if (currentDateString.equals("")) {
            tv_date.setHintTextColor(Color.parseColor("#FF0000"));
        } else {
            progress_bar.setVisibility(View.VISIBLE);
            insertSched(currentDateString, et_title.getText().toString(), et_details.getText().toString(), employee_id);
        }
    }


    @Override
    public void onDateSet(DatePicker datePicker, final int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDateString = DateFormat.getDateInstance(DateFormat.LONG).format(c.getTime());
        tv_date.setText(currentDateString);

        // format date to make it similar to local storage values
        SimpleDateFormat format1 = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = format1.parse(currentDateString);
            currentDateString = format2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        btn_submit.setEnabled(true);

        //checks network
        if (NetworkUtil.isNetworkAvailable(this)) {
            //deletes previously stored data and fetches new ones
            progress_bar.setVisibility(View.VISIBLE);
            viewModel.deleteHolidaysFromRoom();
            loadPublicHolidaysFromServer(String.valueOf(year), countryId);
        } else {
            //compares selected date to holidays in local storage
            compareDate();
        }

    }

    private void insertSched(String currentDateString, String title, String details, String id) {
        ScheduleEntity scheduleEntity = new ScheduleEntity(currentDateString, title, details, id);
        viewModel.insertSingleSched(scheduleEntity);
        Toast.makeText(this, "Schedule successfully set.", Toast.LENGTH_LONG).show();
        setAdapter();
    }

    private void loadPublicHolidaysFromServer(String year, String countryName) {
        viewModel.getPublicHolidayFromServer(GLOBALURL.GETPUBLICHOLIDAYS, year, countryName).observe(this, new Observer<List<PublicHolidays>>() {
            @SuppressLint("ShowToast")
            @Override
            public void onChanged(List<PublicHolidays> publicHolidays) {
                List<PublicHolidayEntity> entities = new ArrayList<>();
                for (int i = 0; publicHolidays.size() > i; i++) {
                    PublicHolidayEntity entity = new PublicHolidayEntity();
                    entity.setCountryCode(publicHolidays.get(i).getCountryCode());
                    entity.setDate(publicHolidays.get(i).getDate());
                    entity.setLocalName(publicHolidays.get(i).getLocalName());
                    entity.setName(publicHolidays.get(i).getName());
                    entities.add(entity);
                }
                //inserts data to room
                insertPubliCholidaysToRoom(entities);

            }
        });
    }

    private void insertPubliCholidaysToRoom(List<PublicHolidayEntity> publicHolidays) {
        viewModel.insertHolidaysToRoom(publicHolidays);
        compareDate();
    }

    private void compareDate() {
        List<PublicHolidayEntity> publicHolidayEntities = viewModel.getAllHolidaysFromRoom();
        for (int i = 0; publicHolidayEntities.size() > i; i++) {
            if (currentDateString.equals(publicHolidayEntities.get(i).getDate())) {
                btn_submit.setEnabled(false);
                showDialog(publicHolidayEntities.get(i).getLocalName());
            }
        }
        setAdapter();
        progress_bar.setVisibility(View.GONE);

    }

    private void showDialog(String s) {
        AlertDialog alertDialog = new AlertDialog.Builder(CalendarActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Unable to set appointment. Holiday - " + s);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public String getCountryCode(String countryName) {
        String[] isoCountryCodes = Locale.getISOCountries();
        Map<String, String> countryMap = new HashMap<>();
        Locale locale;
        String name;
        for (String code : isoCountryCodes) {
            locale = new Locale("", code);
            name = locale.getDisplayCountry();
            countryMap.put(name, code);
        }
        return countryMap.get(countryName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.getAllScheduleSingleEmployee(employee_id);
        setAdapter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setAdapter() {
        scheduleEntities = viewModel.getAllScheduleSingleEmployee(employee_id);
        adapter = new ScheduleAdapter(scheduleEntities, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        progress_bar.setVisibility(View.GONE);
    }
}