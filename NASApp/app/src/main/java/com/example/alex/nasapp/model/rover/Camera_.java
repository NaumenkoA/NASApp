
package com.example.alex.nasapp.model.rover;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Camera_ implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("full_name")
    @Expose
    private String fullName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.fullName);
    }

    public Camera_() {
    }

    private Camera_(Parcel in) {
        this.name = in.readString();
        this.fullName = in.readString();
    }

    public static final Parcelable.Creator<Camera_> CREATOR = new Parcelable.Creator<Camera_>() {
        @Override
        public Camera_ createFromParcel(Parcel source) {
            return new Camera_(source);
        }

        @Override
        public Camera_[] newArray(int size) {
            return new Camera_[size];
        }
    };
}
