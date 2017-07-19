
package com.example.alex.nasapp.model.asteroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AsteroidList {

    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("element_count")
    @Expose
    private Integer elementCount;
    @SerializedName("near_earth_objects")
    @Expose
    private NearEarthObjects nearEarthObjects;

    public Links getLinks() {
        return links;
    }

    public Integer getElementCount() {
        return elementCount;
    }

    public NearEarthObjects getNearEarthObjects() {
        return nearEarthObjects;
    }
}
