package com.example.technicalassignment.utils;

import com.example.technicalassignment.object.PublicHolidays;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PublicHolidayService {
    @GET("{year}/{countryName}")
    public Call<List<PublicHolidays>> getHolidays(@Path("countryName") String countryName,
                                                  @Path("year") String year);
}
