package com.thoughtworks.myapp.service.serviceclient;

import com.squareup.okhttp.OkHttpClient;
import com.thoughtworks.myapp.domain.PM25;
import com.thoughtworks.myapp.service.AirService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class AirServiceClient {

    private static final String BASE_URL = "http://www.pm25.in";
    private static final String TOKEN = "4esfG6UEhGzNkbszfjAp";

    private static AirServiceClient instance;
    private final AirService airService;

    private AirServiceClient() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(20 * 1000, TimeUnit.MILLISECONDS);

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL);
        builder.client(okHttpClient);
        builder.addConverterFactory(GsonConverterFactory.create());
        airService = builder.build().create(AirService.class);
    }

    public static AirServiceClient getInstance() {
        if (instance == null) {
            instance = new AirServiceClient();
        }
        return instance;
    }

    public void requestPM25(String city, Callback<List<PM25>> callback) {
        Call<List<PM25>> call = airService.getPM25(TOKEN, city);
        enqueue(call, callback);
    }

    private <T> void enqueue(Call<T> call, Callback<T> callback) {
        if (call != null && callback != null) {
            call.enqueue(callback);
        }
    }
}
