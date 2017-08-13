
package com.example.alex.nasapp.model.asteroid;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class AsteroidList implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.asteroidList);
    }

    private AsteroidList(Parcel in) {
        this.asteroidList = in.createTypedArrayList(Asteroid.CREATOR);
    }

    public static final Parcelable.Creator<AsteroidList> CREATOR = new Parcelable.Creator<AsteroidList>() {
        @Override
        public AsteroidList createFromParcel(Parcel source) {
            return new AsteroidList(source);
        }

        @Override
        public AsteroidList[] newArray(int size) {
            return new AsteroidList[size];
        }
    };
}
