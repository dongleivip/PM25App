package com.thoughtworks.myapp.service;


import com.thoughtworks.myapp.domain.CityCollection;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by dl on 2015/12/25.
 */
public interface CitiesService {

    @GET("/api/querys.json")
    Call<CityCollection> getCities(@Query("token") String token);
}
