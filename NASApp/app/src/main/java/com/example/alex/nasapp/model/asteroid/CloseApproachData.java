
package com.example.alex.nasapp.model.asteroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CloseApproachData {

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

    public Long getEpochDateCloseApproach() {
        return epochDateCloseApproach;
    }

    public RelativeVelocity getRelativeVelocity() {
        return relativeVelocity;
    }

    public MissDistance getMissDistance() {
        return missDistance;
    }

    public String getOrbitingBody() {
        return orbitingBody;
    }
}
