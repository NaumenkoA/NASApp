
package com.example.alex.nasapp.model.asteroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AsteroidList {

    public AsteroidList (List <Asteroid> asteroids) {
        this.asteroidList = asteroids;
    }

    public List<Asteroid> getAsteroidList() {
        return asteroidList;
    }

     private List<Asteroid> asteroidList;
}
