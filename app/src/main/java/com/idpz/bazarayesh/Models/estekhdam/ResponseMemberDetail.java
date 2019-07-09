package com.idpz.bazarayesh.Models.estekhdam;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idpz.bazarayesh.Models.Data;

public class ResponseMemberDetail {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("member")
    @Expose
    private Data member;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getMember() {
        return member;
    }

    public void setMember(Data member) {
        this.member = member;
    }

}