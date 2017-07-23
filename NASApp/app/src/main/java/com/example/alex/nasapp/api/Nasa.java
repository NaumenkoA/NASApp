package com.example.alex.nasapp.api;

import com.example.alex.nasapp.model.asteroid.AsteroidList;
import com.example.alex.nasapp.model.eye_in_the_sky.SatellitePhoto;
import com.example.alex.nasapp.model.rover.RoverPhotos;
import com.google.android.gms.common.api.Result;
import com.google.gson.JsonElement;

import java.text.SimpleDateFormat;
import java.util.AbstractList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Nasa {
    String NASA_BASE_URL ="https://api.nasa.gov";
    String API_KEY = "Py00BftJJQ5hNKhRButBln7H5AdmPVU4f6NED90h";

    @GET("mars-photos/api/v1/rovers/curiosity/photos?api_key=" + API_KEY)
    Call<RoverPhotos> getMarsRoverImages (@Query("sol") int sol, @Query("page") int pageNumber);

    @GET("planetary/earth/imagery?api_key=" + API_KEY)
    Call<SatellitePhoto> getSatellitePhoto (@Query("lat") float lat,
                                            @Query("lon") float lon,
                                            @Query("date") String date);

    @GET("neo/rest/v1/feed?api_key=" + API_KEY)
    Call<JsonElement> getAsteroidList (@Query("start_date")String startDate);

        }
