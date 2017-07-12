package com.example.alex.nasapp.api;

import com.example.alex.nasapp.model.rover.RoverPhotos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Nasa {
    String NASA_BASE_URL ="https://api.nasa.gov";
    String API_KEY = "Py00BftJJQ5hNKhRButBln7H5AdmPVU4f6NED90h";

    @GET("mars-photos/api/v1/rovers/curiosity/photos?api_key=" + API_KEY)
    Call<RoverPhotos> getMarsRoverImages (@Query("sol") int sol, @Query("page") int pageNumber);

        }
