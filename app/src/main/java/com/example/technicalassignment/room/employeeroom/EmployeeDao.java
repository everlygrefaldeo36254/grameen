package com.example.technicalassignment.room.employeeroom;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EmployeeDao {
    @Query("SELECT * FROM employees")
    List<EmployeeEntity> getAllEmployees();

    @Insert
    void insertAll(List<EmployeeEntity> employeeEntities);

    @Query("DELETE FROM employees")
    void deleteAllEmployees();
}
