
package com.idpz.bazarayesh.Models.SingelAdBride;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ad {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("shour")
    @Expose
    private String shour;
    @SerializedName("ehour")
    @Expose
    private String ehour;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShour() {
        return shour;
    }

    public void setShour(String shour) {
        this.shour = shour;
    }

    public String getEhour() {
        return ehour;
    }

    public void setEhour(String ehour) {
        this.ehour = ehour;
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
