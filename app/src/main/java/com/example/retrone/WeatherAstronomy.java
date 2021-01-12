
package com.example.retrone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherAstronomy {

    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("astronomy")
    @Expose
    private Astronomy astronomy;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Astronomy getAstronomy() {
        return astronomy;
    }

    public void setAstronomy(Astronomy astronomy) {
        this.astronomy = astronomy;
    }

}
