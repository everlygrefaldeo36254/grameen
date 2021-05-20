package com.example.technicalassignment.room.employeeroom;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.technicalassignment.object.Employee;
import com.example.technicalassignment.room.publicholidaysroom.PublicHolidayDao;
import com.example.technicalassignment.room.publicholidaysroom.PublicHolidayEntity;

@Database(entities = {EmployeeEntity.class}, version = 1, exportSchema = false)
public abstract class EmployeeDatabase extends RoomDatabase {
    private static EmployeeDatabase mInstance;
    private static final String DATABASE_NAME = "employee-database";

    public abstract EmployeeDao daoAccess();

    public synchronized static EmployeeDatabase getDatabaseInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), EmployeeDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }

    public static void destroyInstance() {
        mInstance = null;
    }

}
