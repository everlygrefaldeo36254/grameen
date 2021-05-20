package com.example.technicalassignment.repository;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.example.technicalassignment.object.PublicHolidays;
import com.example.technicalassignment.room.employeeroom.EmployeeDatabase;
import com.example.technicalassignment.room.employeeroom.EmployeeEntity;
import com.example.technicalassignment.room.publicholidaysroom.PublicHolidayDatabase;
import com.example.technicalassignment.room.publicholidaysroom.PublicHolidayEntity;
import com.example.technicalassignment.room.scheduleroom.ScheduleDatabase;
import com.example.technicalassignment.room.scheduleroom.ScheduleEntity;
import com.example.technicalassignment.utils.GLOBALURL;
import com.example.technicalassignment.utils.PublicHolidayService;

import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PublicHolidayRepo {
    PublicHolidayDatabase publicholidaydb;
    EmployeeDatabase employeeDatabase;
    PublicHolidayService publicHolidayService;
    ScheduleDatabase scheduleDatabase;

    public PublicHolidayRepo(Application application) {
        publicholidaydb = PublicHolidayDatabase.getDatabaseInstance(application);
        employeeDatabase = EmployeeDatabase.getDatabaseInstance(application);
        scheduleDatabase = ScheduleDatabase.getDatabaseInstance(application);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        publicHolidayService = new Retrofit.Builder()
                .baseUrl(GLOBALURL.GETPUBLICHOLIDAYS)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PublicHolidayService.class);
    }

    public MutableLiveData<List<PublicHolidays>> getPublicHolidaysFromServer(String path, String date, String countryName) {
        final MutableLiveData<List<PublicHolidays>> listLiveData = new MutableLiveData<>();
        publicHolidayService.getHolidays(countryName, date)
                .enqueue(new Callback<List<PublicHolidays>>() {
                    @Override
                    public void onResponse(Call<List<PublicHolidays>> call, Response<List<PublicHolidays>> response) {
                        if (response.body() != null) {
                            listLiveData.postValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PublicHolidays>> call, Throwable t) {
                        listLiveData.postValue(null);
                    }
                });

        return listLiveData;
    }

    public List<PublicHolidayEntity> getPublicHolidaysFromRoom() {
        return publicholidaydb.daoAccess().getAll();
    }

    public void insertHolidaysToRoom(List<PublicHolidayEntity> publicHolidayEntities) {
        publicholidaydb.daoAccess().insertAll(publicHolidayEntities);
    }

    public void deletePublicHolidaysFromRoom() {
        publicholidaydb.daoAccess().deleteAllPublicHolidays();
    }

    public List<PublicHolidayEntity> getAllHolidaysFromRoom() {
        return publicholidaydb.daoAccess().getAll();
    }

    public void insertEmployeesToRoom(List<EmployeeEntity> employeeEntities) {
        employeeDatabase.daoAccess().insertAll(employeeEntities);
    }

    public void deleteEmployeesFromRoom() {
        employeeDatabase.daoAccess().deleteAllEmployees();
    }

    public List<EmployeeEntity> getAllEmployees() {
        return employeeDatabase.daoAccess().getAllEmployees();
    }

    public void insertSchedule(ScheduleEntity entity) {
        scheduleDatabase.daoAccess().insertSingleSchedule(entity);
    }

    public List<ScheduleEntity> getAllScheduleSingleEmployee(String id) {
        return scheduleDatabase.daoAccess().getAllScheduleForSingleEmployee(id);
    }

}
