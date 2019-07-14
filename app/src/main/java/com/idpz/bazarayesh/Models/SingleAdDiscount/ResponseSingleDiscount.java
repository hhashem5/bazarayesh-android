
package com.idpz.bazarayesh.Models.SingleAdDiscount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseSingleDiscount {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("ad")
    @Expose
    private Ad ad;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Ad getAd() {
        return ad;
    }

    public void setAd(Ad ad) {
        this.ad = ad;
    }

}