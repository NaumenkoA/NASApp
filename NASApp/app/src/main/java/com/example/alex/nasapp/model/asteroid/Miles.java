
package com.example.alex.nasapp.model.asteroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Miles {

    @SerializedName("estimated_diameter_min")
    @Expose
    public Double estimatedDiameterMin;
    @SerializedName("estimated_diameter_max")
    @Expose
    public Double estimatedDiameterMax;

}
