package com.example.technicalassignment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.technicalassignment.object.PublicHolidays;
import com.example.technicalassignment.repository.PublicHolidayRepo;
import com.example.technicalassignment.room.employeeroom.EmployeeEntity;
import com.example.technicalassignment.room.publicholidaysroom.PublicHolidayEntity;
import com.example.technicalassignment.room.scheduleroom.ScheduleEntity;

import java.util.List;

public class PublicHolidayViewModel extends AndroidViewModel {

    private PublicHolidayRepo repo;

    public PublicHolidayViewModel(@NonNull Application application) {
        super(application);
        repo = new PublicHolidayRepo(application);
    }

    public MutableLiveData<List<PublicHolidays>> getPublicHolidayFromServer(String path,String year, String countryName) {
        return repo.getPublicHolidaysFromServer(path,year, countryName);
    }
    public void insertHolidaysToRoom(List<PublicHolidayEntity> publicHolidayEntities) {
        repo.insertHolidaysToRoom(publicHolidayEntities);
    }
    public void deleteHolidaysFromRoom() {
        repo.deletePublicHolidaysFromRoom();
    }
    public List<PublicHolidayEntity> getAllHolidaysFromRoom() {
        return repo.getAllHolidaysFromRoom();
    }
    public void insertEmployeesToRoom(List<EmployeeEntity> employeeEntities) {
        repo.insertEmployeesToRoom(employeeEntities);
    }
    public void deleteAllEmployees() {
        repo.deleteEmployeesFromRoom();
    }
    public List<EmployeeEntity> getAllEmployeesFromRoom() {
        return repo.getAllEmployees();
    }
    public List<ScheduleEntity> getAllScheduleSingleEmployee(String id) {
        return repo.getAllScheduleSingleEmployee(id);
    }
    public void insertSingleSched(ScheduleEntity entity) {
        repo.insertSchedule(entity);
    }
}
