
package com.idpz.bazarayesh.Models.AdBride;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBride {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ads")
    @Expose
    private List<Ad> ads = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }

}
