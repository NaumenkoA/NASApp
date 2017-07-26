
package com.example.alex.nasapp.model.asteroid;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kilometers implements Parcelable {

    @SerializedName("estimated_diameter_min")
    @Expose
    public Double estimatedDiameterMin;
    @SerializedName("estimated_diameter_max")
    @Expose
    public Double estimatedDiameterMax;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.estimatedDiameterMin);
        dest.writeValue(this.estimatedDiameterMax);
    }

    public Kilometers() {
    }

    protected Kilometers(Parcel in) {
        this.estimatedDiameterMin = (Double) in.readValue(Double.class.getClassLoader());
        this.estimatedDiameterMax = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Kilometers> CREATOR = new Parcelable.Creator<Kilometers>() {
        @Override
        public Kilometers createFromParcel(Parcel source) {
            return new Kilometers(source);
        }

        @Override
        public Kilometers[] newArray(int size) {
            return new Kilometers[size];
        }
    };
}
