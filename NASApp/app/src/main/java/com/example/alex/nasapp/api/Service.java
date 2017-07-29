package com.example.alex.nasapp.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {
    public static Nasa getNasaApi() {
        return new Retrofit.Builder()
                .baseUrl(Nasa.NASA_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build()
                .create(Nasa.class);
    }

}
