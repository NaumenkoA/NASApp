
package com.example.alex.nasapp.model.asteroid;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meters implements Parcelable {

    @SerializedName("estimated_diameter_min")
    @Expose
    private Double estimatedDiameterMin;
    @SerializedName("estimated_diameter_max")
    @Expose
    private Double estimatedDiameterMax;

    public Double getEstimatedDiameterMin() {
        return estimatedDiameterMin;
    }

    public Double getEstimatedDiameterMax() {
        return estimatedDiameterMax;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.estimatedDiameterMin);
        dest.writeValue(this.estimatedDiameterMax);
    }

    public Meters() {
    }

    protected Meters(Parcel in) {
        this.estimatedDiameterMin = (Double) in.readValue(Double.class.getClassLoader());
        this.estimatedDiameterMax = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Meters> CREATOR = new Parcelable.Creator<Meters>() {
        @Override
        public Meters createFromParcel(Parcel source) {
            return new Meters(source);
        }

        @Override
        public Meters[] newArray(int size) {
            return new Meters[size];
        }
    };
}
