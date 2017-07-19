
package com.example.alex.nasapp.model.asteroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MissDistance {

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

    public String getAstronomical() {
        return astronomical;
    }

    public String getLunar() {
        return lunar;
    }

    public String getKilometers() {
        return kilometers;
    }

    public String getMiles() {
        return miles;
    }
}
