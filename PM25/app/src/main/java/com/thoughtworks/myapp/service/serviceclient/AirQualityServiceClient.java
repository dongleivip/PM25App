package com.thoughtworks.myapp.service.serviceclient;

import com.squareup.okhttp.OkHttpClient;
import com.thoughtworks.myapp.domain.AQI;
import com.thoughtworks.myapp.service.AirQualityService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by dl on 2015/12/25.
 */
public class AirQualityServiceClient {

    private static final String BASE_URL = "http://www.pm25.in";
    private static final String TOKEN = "4esfG6UEhGzNkbszfjAp";

    private static AirQualityServiceClient instance;
    private final AirQualityService airQualityService;

    private AirQualityServiceClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(20 * 1000, TimeUnit.MILLISECONDS);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.client(okHttpClient);
        builder.addConverterFactory(GsonConverterFactory.create());
        airQualityService = builder.build().create(AirQualityService.class);
    }

    public static AirQualityServiceClient getInstance() {
        if (instance == null) {
            instance = new AirQualityServiceClient();
        }
        return instance;
    }

    public void requestAQI(String city, Callback<List<AQI>> callback) {
        Call<List<AQI>> call = airQualityService.getAQI(TOKEN, city);
        enqueue(call, callback);
    }

    private <T> void enqueue(Call<T> call, Callback<T> callback) {
        if (call != null && callback != null) {
            call.enqueue(callback);
        }
    }
}
