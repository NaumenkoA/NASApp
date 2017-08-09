
package com.example.alex.nasapp.model.rover;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoverPhotos implements Parcelable {

    public List<Photo> getPhotos() {
        return photos;
    }

    @SerializedName("photos")
    @Expose
    private List<Photo> photos = null;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.photos);
    }

    public RoverPhotos() {
    }

    protected RoverPhotos(Parcel in) {
        this.photos = new ArrayList<Photo>();
        in.readList(this.photos, Photo.class.getClassLoader());
    }

    public static final Parcelable.Creator<RoverPhotos> CREATOR = new Parcelable.Creator<RoverPhotos>() {
        @Override
        public RoverPhotos createFromParcel(Parcel source) {
            return new RoverPhotos(source);
        }

        @Override
        public RoverPhotos[] newArray(int size) {
            return new RoverPhotos[size];
        }
    };
}
