
package com.example.alex.nasapp.model.asteroid;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MissDistance implements Parcelable {

    @SerializedName("astronomical")
    @Expose
    private String astronomical;
    @SerializedName("lunar")
    @Expose
    private String lunar;
    @SerializedName("kilometers")
    @Expose
    private String kilometers;
    @SerializedName("miles")
    @Expose
    private String miles;

    public String getLunar() {
        return lunar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.astronomical);
        dest.writeString(this.lunar);
        dest.writeString(this.kilometers);
        dest.writeString(this.miles);
    }

    public MissDistance() {
    }

    private MissDistance(Parcel in) {
        this.astronomical = in.readString();
        this.lunar = in.readString();
        this.kilometers = in.readString();
        this.miles = in.readString();
    }

    public static final Parcelable.Creator<MissDistance> CREATOR = new Parcelable.Creator<MissDistance>() {
        @Override
        public MissDistance createFromParcel(Parcel source) {
            return new MissDistance(source);
        }

        @Override
        public MissDistance[] newArray(int size) {
            return new MissDistance[size];
        }
    };
}
