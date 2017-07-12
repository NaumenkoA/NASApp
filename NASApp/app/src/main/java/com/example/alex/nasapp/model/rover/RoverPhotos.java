
package com.example.alex.nasapp.model.rover;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoverPhotos {

    public List<Photo> getPhotos() {
        return photos;
    }

    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;

}
