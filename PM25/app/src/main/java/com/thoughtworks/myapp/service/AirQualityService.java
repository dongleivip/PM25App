package com.thoughtworks.myapp.service;

import com.thoughtworks.myapp.domain.AQI;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by dl on 2015/12/25.
 */
public interface AirQualityService {

    @GET("/api/querys/only_aqi.json")
    Call<List<AQI>> getAQI(@Query("token") String token, @Query("city") String city);

}
