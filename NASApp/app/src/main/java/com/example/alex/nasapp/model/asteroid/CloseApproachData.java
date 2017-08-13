
package com.example.alex.nasapp.model.asteroid;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloseApproachData implements Parcelable {

    @SerializedName("close_approach_date")
    @Expose
    private String closeApproachDate;
    @SerializedName("epoch_date_close_approach")
    @Expose
    private Long epochDateCloseApproach;
    @SerializedName("relative_velocity")
    @Expose
    private RelativeVelocity relativeVelocity;
    @SerializedName("miss_distance")
    @Expose
    private MissDistance missDistance;
    @SerializedName("orbiting_body")
    @Expose
    private String orbitingBody;

    public String getCloseApproachDate() {
        return closeApproachDate;
    }

    public RelativeVelocity getRelativeVelocity() {
        return relativeVelocity;
    }

    public MissDistance getMissDistance() {
        return missDistance;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.closeApproachDate);
        dest.writeValue(this.epochDateCloseApproach);
        dest.writeParcelable(this.relativeVelocity, flags);
        dest.writeParcelable(this.missDistance, flags);
        dest.writeString(this.orbitingBody);
    }

    public CloseApproachData() {
    }

    private CloseApproachData(Parcel in) {
        this.closeApproachDate = in.readString();
        this.epochDateCloseApproach = (Long) in.readValue(Long.class.getClassLoader());
        this.relativeVelocity = in.readParcelable(RelativeVelocity.class.getClassLoader());
        this.missDistance = in.readParcelable(MissDistance.class.getClassLoader());
        this.orbitingBody = in.readString();
    }

    public static final Parcelable.Creator<CloseApproachData> CREATOR = new Parcelable.Creator<CloseApproachData>() {
        @Override
        public CloseApproachData createFromParcel(Parcel source) {
            return new CloseApproachData(source);
        }

        @Override
        public CloseApproachData[] newArray(int size) {
            return new CloseApproachData[size];
        }
    };
}
