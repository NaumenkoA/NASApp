
package com.example.alex.nasapp.model.asteroid;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelativeVelocity implements Parcelable {

    @SerializedName("kilometers_per_second")
    @Expose
    private String kilometersPerSecond;
    @SerializedName("kilometers_per_hour")
    @Expose
    private String kilometersPerHour;
    @SerializedName("miles_per_hour")
    @Expose
    private String milesPerHour;

    public String getKilometersPerSecond() {
        return kilometersPerSecond;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.kilometersPerSecond);
        dest.writeString(this.kilometersPerHour);
        dest.writeString(this.milesPerHour);
    }

    public RelativeVelocity() {
    }

    private RelativeVelocity(Parcel in) {
        this.kilometersPerSecond = in.readString();
        this.kilometersPerHour = in.readString();
        this.milesPerHour = in.readString();
    }

    public static final Parcelable.Creator<RelativeVelocity> CREATOR = new Parcelable.Creator<RelativeVelocity>() {
        @Override
        public RelativeVelocity createFromParcel(Parcel source) {
            return new RelativeVelocity(source);
        }

        @Override
        public RelativeVelocity[] newArray(int size) {
            return new RelativeVelocity[size];
        }
    };
}
