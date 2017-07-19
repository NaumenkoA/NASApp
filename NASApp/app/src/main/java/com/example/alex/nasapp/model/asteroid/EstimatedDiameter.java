
package com.example.alex.nasapp.model.asteroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EstimatedDiameter {

    @SerializedName("kilometers")
    @Expose
    private Kilometers kilometers;
    @SerializedName("meters")
    @Expose
    private Meters meters;
    @SerializedName("miles")
    @Expose
    private Miles miles;
    @SerializedName("feet")
    @Expose
    private Feet feet;

    public Kilometers getKilometers() {
        return kilometers;
    }

    public Meters getMeters() {
        return meters;
    }

    public Miles getMiles() {
        return miles;
    }

    public Feet getFeet() {
        return feet;
    }
}
