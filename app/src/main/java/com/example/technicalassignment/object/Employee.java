package com.example.technicalassignment.object;

public class Employee {
    String country,name,employeeId;

    public Employee(String country, String name, String employeeId) {
        this.country = country;
        this.name = name;
        this.employeeId = employeeId;
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
