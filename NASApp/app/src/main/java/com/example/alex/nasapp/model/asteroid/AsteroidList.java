
package com.example.alex.nasapp.model.asteroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class AsteroidList {

    public AsteroidList (List <Asteroid> asteroids) {
        this.asteroidList = asteroids;
    }

    public List<Asteroid> getAsteroidList() {
        return asteroidList;
    }

     private List<Asteroid> asteroidList;

     public List<Asteroid> getHazardousAsteroidList() {
        List <Asteroid> hazardous = new ArrayList<>();
        for (Asteroid asteroid:asteroidList) {
            if (asteroid.getPotentiallyHazardousAsteroid()){
                hazardous.add(asteroid);
            }
        }
        return hazardous;
    }

    public int getAsteroidPosition(List<Asteroid> asteroidList, Asteroid selectedAsteroid) {
        int i = 0;
        if (selectedAsteroid == null) return -1;
        for (Asteroid asteroid:asteroidList){
            if (asteroid.getName().equals(selectedAsteroid.getName())) return i;
            i++;
        }
        return -1;
    }
}
