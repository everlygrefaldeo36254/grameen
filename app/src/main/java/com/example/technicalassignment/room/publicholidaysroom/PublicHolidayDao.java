package com.example.technicalassignment.room.publicholidaysroom;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface PublicHolidayDao {
    @Query("SELECT * FROM public_holidays")
    List<PublicHolidayEntity> getAll();

    @Insert
    void insertAll(List<PublicHolidayEntity> publicHolidayEntities);

    @Query("DELETE FROM public_holidays")
    void deleteAllPublicHolidays();
}
