
package com.example.alex.nasapp.model.asteroid;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Asteroid implements Parcelable {

    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("neo_reference_id")
    @Expose
    private String neoReferenceId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nasa_jpl_url")
    @Expose
    private String nasaJplUrl;
    @SerializedName("absolute_magnitude_h")
    @Expose
    private Double absoluteMagnitudeH;
    @SerializedName("estimated_diameter")
    @Expose
    private EstimatedDiameter estimatedDiameter;
    @SerializedName("is_potentially_hazardous_asteroid")
    @Expose
    private Boolean isPotentiallyHazardousAsteroid;
    @SerializedName("close_approach_data")
    @Expose
    private List<CloseApproachData> closeApproachData = null;

    public String getName() {
        return name;
    }

    public EstimatedDiameter getEstimatedDiameter() {
        return estimatedDiameter;
    }

    public Boolean getPotentiallyHazardousAsteroid() {
        return isPotentiallyHazardousAsteroid;
    }

    public List<CloseApproachData> getCloseApproachData() {
        return closeApproachData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.links, flags);
        dest.writeString(this.neoReferenceId);
        dest.writeString(this.name);
        dest.writeString(this.nasaJplUrl);
        dest.writeValue(this.absoluteMagnitudeH);
        dest.writeParcelable(this.estimatedDiameter, flags);
        dest.writeValue(this.isPotentiallyHazardousAsteroid);
        dest.writeList(this.closeApproachData);
    }

    public Asteroid() {
    }

    private Asteroid(Parcel in) {
        this.links = in.readParcelable(Links.class.getClassLoader());
        this.neoReferenceId = in.readString();
        this.name = in.readString();
        this.nasaJplUrl = in.readString();
        this.absoluteMagnitudeH = (Double) in.readValue(Double.class.getClassLoader());
        this.estimatedDiameter = in.readParcelable(EstimatedDiameter.class.getClassLoader());
        this.isPotentiallyHazardousAsteroid = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.closeApproachData = new ArrayList<CloseApproachData>();
        in.readList(this.closeApproachData, CloseApproachData.class.getClassLoader());
    }

    public static final Parcelable.Creator<Asteroid> CREATOR = new Parcelable.Creator<Asteroid>() {
        @Override
        public Asteroid createFromParcel(Parcel source) {
            return new Asteroid(source);
        }

        @Override
        public Asteroid[] newArray(int size) {
            return new Asteroid[size];
        }
    };
}
