package com.example.technicalassignment.room.publicholidaysroom;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {PublicHolidayEntity.class}, version = 1, exportSchema = false)
public abstract class PublicHolidayDatabase extends RoomDatabase {
    private static PublicHolidayDatabase mInstance;
    private static final String DATABASE_NAME = "publicholiday-database";

    public abstract PublicHolidayDao daoAccess();

    public synchronized static PublicHolidayDatabase getDatabaseInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), PublicHolidayDatabase.class, DATABASE_NAME)
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
