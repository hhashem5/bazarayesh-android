
package com.idpz.bazarayesh.Models.adDetails;

import com.google.gson.annotations.SerializedName;

public class Ad {

    @SerializedName("id")
    private Integer mId;
    @SerializedName("lat")
    private Double mLat;
    @SerializedName("lng")
    private Double mLng;
    @SerializedName("member_id")
    private Integer mMemberId;
    @SerializedName("name")
    private String mName;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        mLat = lat;
    }

    public Double getLng() {
        return mLng;
    }

    public void setLng(Double lng) {
        mLng = lng;
    }

    public Integer getMemberId() {
        return mMemberId;
    }

    public void setMemberId(Integer memberId) {
        mMemberId = memberId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
