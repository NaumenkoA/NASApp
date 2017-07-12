package com.example.alex.nasapp.model.menuFeatures;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuFeature implements Parcelable {
    private int featureNameId;
    private int imageResourceId;
    private int featureDescriptionId;
    private int featureId;

    public int getFeatureDescriptionId() {
        return featureDescriptionId;
    }

    public int getFeatureNameId() {
        return featureNameId;
    }

    public MenuFeature (int featureNameId, int imageResourceId, int featureDescription, int featureId) {
        this.featureNameId = featureNameId;

        this.imageResourceId = imageResourceId;
        this.featureDescriptionId = featureDescription;
        this.featureId = featureId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getFeatureId() {
        return featureId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.featureNameId);
        dest.writeInt(this.imageResourceId);
        dest.writeInt(this.featureDescriptionId);
        dest.writeInt(this.featureId);
    }

    protected MenuFeature(Parcel in) {
        this.featureNameId = in.readInt();
        this.imageResourceId = in.readInt();
        this.featureDescriptionId = in.readInt();
        this.featureId = in.readInt();
    }

    public static final Creator<MenuFeature> CREATOR = new Creator<MenuFeature>() {
        @Override
        public MenuFeature createFromParcel(Parcel source) {
            return new MenuFeature(source);
        }

        @Override
        public MenuFeature[] newArray(int size) {
            return new MenuFeature[size];
        }
    };
}
