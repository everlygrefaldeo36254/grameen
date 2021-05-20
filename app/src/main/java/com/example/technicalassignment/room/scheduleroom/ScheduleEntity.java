package com.example.technicalassignment.room.scheduleroom;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "schedule")
public class ScheduleEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String date;
    private String title;
    private String details;
    private String employee_id;

    public ScheduleEntity(String date,String title,String details, String employee_id) {
        this.date=date;
        this.title=title;
        this.details=details;
        this.employee_id=employee_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
