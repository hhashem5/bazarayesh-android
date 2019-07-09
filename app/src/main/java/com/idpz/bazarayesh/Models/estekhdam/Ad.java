package com.idpz.bazarayesh.Models.estekhdam;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class Ad {

    @SerializedName("id")
    private Long mId;
    @SerializedName("lat")
    private Double mLat;
    @SerializedName("lng")
    private Double mLng;
    @SerializedName("member_id")
    private Long mMemberId;
    @SerializedName("name")
    private String mName;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
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

    public Long getMemberId() {
        return mMemberId;
    }

    public void setMemberId(Long memberId) {
        mMemberId = memberId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

}
