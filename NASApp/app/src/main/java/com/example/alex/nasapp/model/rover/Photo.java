
package com.example.alex.nasapp.model.rover;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {


    public int getId() {
        return id;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sol")
    @Expose
    private Integer sol;
    @SerializedName("camera")
    @Expose
    private Camera camera;
    @SerializedName("img_src")
    @Expose
    private String imgSrc;
    @SerializedName("earth_date")
    @Expose
    private String earthDate;
    @SerializedName("rover")
    @Expose
    private Rover rover;

    public Rover getRover() {
        return rover;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public String getImgSrc() {
        return imgSrc;
    }
}
