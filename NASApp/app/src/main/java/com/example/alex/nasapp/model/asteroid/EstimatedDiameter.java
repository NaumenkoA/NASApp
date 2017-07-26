
package com.example.alex.nasapp.model.asteroid;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EstimatedDiameter implements Parcelable {

    @SerializedName("kilometers")
    @Expose
    private Kilometers kilometers;
    @SerializedName("meters")
    @Expose
    private Meters meters;
    @SerializedName("miles")
    @Expose
    private Miles miles;
    @SerializedName("feet")
    @Expose
    private Feet feet;

    public Kilometers getKilometers() {
        return kilometers;
    }

    public Meters getMeters() {
        return meters;
    }

    public Miles getMiles() {
        return miles;
    }

    public Feet getFeet() {
        return feet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.kilometers, flags);
        dest.writeParcelable(this.meters, flags);
        dest.writeParcelable(this.miles, flags);
        dest.writeParcelable(this.feet, flags);
    }

    public EstimatedDiameter() {
    }

    protected EstimatedDiameter(Parcel in) {
        this.kilometers = in.readParcelable(Kilometers.class.getClassLoader());
        this.meters = in.readParcelable(Meters.class.getClassLoader());
        this.miles = in.readParcelable(Miles.class.getClassLoader());
        this.feet = in.readParcelable(Feet.class.getClassLoader());
    }

    public static final Parcelable.Creator<EstimatedDiameter> CREATOR = new Parcelable.Creator<EstimatedDiameter>() {
        @Override
        public EstimatedDiameter createFromParcel(Parcel source) {
            return new EstimatedDiameter(source);
        }

        @Override
        public EstimatedDiameter[] newArray(int size) {
            return new EstimatedDiameter[size];
        }
    };
}
