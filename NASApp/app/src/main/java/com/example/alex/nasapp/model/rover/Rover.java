
package com.example.alex.nasapp.model.rover;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rover implements Parcelable {

    public String getName() {
        return name;
    }

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("landing_date")
    @Expose
    private String landingDate;
    @SerializedName("launch_date")
    @Expose
    private String launchDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("max_sol")
    @Expose
    private Integer maxSol;
    @SerializedName("max_date")
    @Expose
    private String maxDate;
    @SerializedName("total_photos")
    @Expose
    private Integer totalPhotos;
    @SerializedName("cameras")
    @Expose
    private List<Camera_> cameras = null;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.name);
        dest.writeString(this.landingDate);
        dest.writeString(this.launchDate);
        dest.writeString(this.status);
        dest.writeValue(this.maxSol);
        dest.writeString(this.maxDate);
        dest.writeValue(this.totalPhotos);
        dest.writeList(this.cameras);
    }

    public Rover() {
    }

    private Rover(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.landingDate = in.readString();
        this.launchDate = in.readString();
        this.status = in.readString();
        this.maxSol = (Integer) in.readValue(Integer.class.getClassLoader());
        this.maxDate = in.readString();
        this.totalPhotos = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cameras = new ArrayList<Camera_>();
        in.readList(this.cameras, Camera_.class.getClassLoader());
    }

    public static final Parcelable.Creator<Rover> CREATOR = new Parcelable.Creator<Rover>() {
        @Override
        public Rover createFromParcel(Parcel source) {
            return new Rover(source);
        }

        @Override
        public Rover[] newArray(int size) {
            return new Rover[size];
        }
    };
}
