
package com.example.alex.nasapp.model.eye_in_the_sky;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SatellitePhoto {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("url")
    @Expose
    private String url;

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    @SerializedName("id")
    @Expose
    private String id;

}
