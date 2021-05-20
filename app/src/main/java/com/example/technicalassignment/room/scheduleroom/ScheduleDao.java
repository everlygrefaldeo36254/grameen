package com.example.technicalassignment.room.scheduleroom;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ScheduleDao {
    @Query("SELECT * FROM schedule")
    List<ScheduleEntity> getAllSchedule();

    @Insert
    void insertAll(List<ScheduleEntity> employeeEntities);

    @Query("DELETE FROM schedule")
    void deleteScheduleEmployees();


    @Query("SELECT * FROM schedule where employee_id= :employee_id")
    List<ScheduleEntity> getAllScheduleForSingleEmployee(String employee_id);


    @Insert
    void insertSingleSchedule(ScheduleEntity scheduleEntity);
}
