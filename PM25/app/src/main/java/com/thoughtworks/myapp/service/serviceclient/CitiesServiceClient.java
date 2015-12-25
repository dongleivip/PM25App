package com.thoughtworks.myapp.service.serviceclient;

import com.squareup.okhttp.OkHttpClient;
import com.thoughtworks.myapp.domain.CityCollection;
import com.thoughtworks.myapp.service.CitiesService;

import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by dl on 2015/12/25.
 */
public class CitiesServiceClient {

    private static final String BASE_URL = "http://www.pm25.in";
    private static final String TOKEN = "4esfG6UEhGzNkbszfjAp";

    private static CitiesServiceClient instance;
    private final CitiesService citiesService;

    private CitiesServiceClient(){

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(20 * 1000, TimeUnit.MILLISECONDS);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.client(okHttpClient);
        builder.addConverterFactory(GsonConverterFactory.create());
        citiesService = builder.build().create(CitiesService.class);
    }

    public static CitiesServiceClient getInstance() {
        if (instance == null) {
            instance = new CitiesServiceClient();
        }
        return instance;

    }

    public void requestCities(Callback<CityCollection> callback) {
        Call<CityCollection> call = citiesService.getCities(TOKEN);
        enqueue(call, callback);
    }

    private <T> void enqueue(Call<T> call, Callback<T> callback) {
        if (call != null && callback != null) {
            call.enqueue(callback);
        }
    }



}
