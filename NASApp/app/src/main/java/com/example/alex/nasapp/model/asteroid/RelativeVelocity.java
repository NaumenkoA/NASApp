
package com.example.alex.nasapp.model.asteroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelativeVelocity {

    @SerializedName("kilometers_per_second")
    @Expose
    public String kilometersPerSecond;
    @SerializedName("kilometers_per_hour")
    @Expose
    private String kilometersPerHour;
    @SerializedName("miles_per_hour")
    @Expose
    private String milesPerHour;

    public String getKilometersPerSecond() {
        return kilometersPerSecond;
    }

    public String getKilometersPerHour() {
        return kilometersPerHour;
    }

    public String getMilesPerHour() {
        return milesPerHour;
    }
}
