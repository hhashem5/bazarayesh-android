
package com.idpz.bazarayesh.Models.adDetails;

import com.google.gson.annotations.SerializedName;

public class AdDetailsModel {

    @SerializedName("ad")
    private Ad mAd;
    @SerializedName("status")
    private String mStatus;

    public Ad getAd() {
        return mAd;
    }

    public void setAd(Ad ad) {
        mAd = ad;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
