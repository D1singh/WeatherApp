
package com.example.retrone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Astronomy {

    @SerializedName("astro")
    @Expose
    private Astro astro;

    public Astro getAstro() {
        return astro;
    }

    public void setAstro(Astro astro) {
        this.astro = astro;
    }

}
