
package com.idpz.bazarayesh.Models.SingleAdDiscount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ad {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ad_event")
    @Expose
    private String adEvent;
    @SerializedName("sdate")
    @Expose
    private String sdate;
    @SerializedName("edate")
    @Expose
    private String edate;
    @SerializedName("affair")
    @Expose
    private String affair;
    @SerializedName("specified")
    @Expose
    private Object specified;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("mem_id")
    @Expose
    private Integer memId;
    @SerializedName("ex_date")
    @Expose
    private String exDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdEvent() {
        return adEvent;
    }

    public void setAdEvent(String adEvent) {
        this.adEvent = adEvent;
    }

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public String getAffair() {
        return affair;
    }

    public void setAffair(String affair) {
        this.affair = affair;
    }

    public Object getSpecified() {
        return specified;
    }

    public void setSpecified(Object specified) {
        this.specified = specified;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public String getExDate() {
        return exDate;
    }

    public void setExDate(String exDate) {
        this.exDate = exDate;
    }

}
