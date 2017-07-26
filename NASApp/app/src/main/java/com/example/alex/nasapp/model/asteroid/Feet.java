
package com.example.alex.nasapp.model.asteroid;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feet implements Parcelable {

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

    public Feet() {
    }

    protected Feet(Parcel in) {
        this.estimatedDiameterMin = (Double) in.readValue(Double.class.getClassLoader());
        this.estimatedDiameterMax = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Feet> CREATOR = new Parcelable.Creator<Feet>() {
        @Override
        public Feet createFromParcel(Parcel source) {
            return new Feet(source);
        }

        @Override
        public Feet[] newArray(int size) {
            return new Feet[size];
        }
    };
}
