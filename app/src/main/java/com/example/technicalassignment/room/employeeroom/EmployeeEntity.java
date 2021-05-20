package com.example.technicalassignment.room.employeeroom;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employees")
public class EmployeeEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String country;
    private String name;
    private String employeeId;

    public EmployeeEntity( String country, String name, String employeeId) {
        this.country = country;
        this.name = name;
        this.employeeId = employeeId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
