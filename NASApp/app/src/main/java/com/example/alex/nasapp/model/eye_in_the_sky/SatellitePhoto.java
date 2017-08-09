
package com.example.alex.nasapp.model.eye_in_the_sky;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SatellitePhoto implements Parcelable {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("url")
    @Expose
    private String url;

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    @SerializedName("id")
    @Expose
    private String id;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeString(this.url);
        dest.writeString(this.id);
    }

    public SatellitePhoto() {
    }

    protected SatellitePhoto(Parcel in) {
        this.date = in.readString();
        this.url = in.readString();
        this.id = in.readString();
    }

    public static final Parcelable.Creator<SatellitePhoto> CREATOR = new Parcelable.Creator<SatellitePhoto>() {
        @Override
        public SatellitePhoto createFromParcel(Parcel source) {
            return new SatellitePhoto(source);
        }

        @Override
        public SatellitePhoto[] newArray(int size) {
            return new SatellitePhoto[size];
        }
    };
}
