
package com.example.alex.nasapp.model.asteroid;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Miles implements Parcelable {

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

    public Miles() {
    }

    protected Miles(Parcel in) {
        this.estimatedDiameterMin = (Double) in.readValue(Double.class.getClassLoader());
        this.estimatedDiameterMax = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Miles> CREATOR = new Parcelable.Creator<Miles>() {
        @Override
        public Miles createFromParcel(Parcel source) {
            return new Miles(source);
        }

        @Override
        public Miles[] newArray(int size) {
            return new Miles[size];
        }
    };
}
