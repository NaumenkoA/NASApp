
package com.example.alex.nasapp.model.rover;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable {


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

    public Photo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.sol);
        dest.writeParcelable(this.camera, flags);
        dest.writeString(this.imgSrc);
        dest.writeString(this.earthDate);
        dest.writeParcelable(this.rover, flags);
    }

    protected Photo(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.sol = (Integer) in.readValue(Integer.class.getClassLoader());
        this.camera = in.readParcelable(Camera.class.getClassLoader());
        this.imgSrc = in.readString();
        this.earthDate = in.readString();
        this.rover = in.readParcelable(Rover.class.getClassLoader());
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
