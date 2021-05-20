package com.example.technicalassignment.room.scheduleroom;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ScheduleEntity.class}, version = 1, exportSchema = false)
public abstract class ScheduleDatabase extends RoomDatabase {
    private static ScheduleDatabase mInstance;
    private static final String DATABASE_NAME = "schedule-database";

    public abstract ScheduleDao daoAccess();

    public synchronized static ScheduleDatabase getDatabaseInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), ScheduleDatabase.class, DATABASE_NAME)
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
