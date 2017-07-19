
package com.example.alex.nasapp.model.asteroid;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NearEarthObjects {

    @SerializedName("2017-07-20")
    @Expose
    private List<Asteroid> Asteroid = null;

    public List<com.example.alex.nasapp.model.asteroid.Asteroid> getAsteroid() {
        return Asteroid;
    }
}
