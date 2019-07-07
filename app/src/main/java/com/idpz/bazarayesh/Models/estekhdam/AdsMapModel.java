package com.idpz.bazarayesh.Models.estekhdam;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class AdsMapModel {

    @SerializedName("ads")
    private List<Ad> mAds;
    @SerializedName("status")
    private String mStatus;

    public List<Ad> getAds() {
        return mAds;
    }

    public void setAds(List<Ad> ads) {
        mAds = ads;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
