
package com.idpz.bazarayesh.Models.AdRecruiment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ad {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("expertise")
    @Expose
    private String expertise;
    @SerializedName("lvl")
    @Expose
    private String lvl;
    @SerializedName("conditions")
    @Expose
    private String conditions;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("mem_id")
    @Expose
    private Integer memId;
    @SerializedName("pub")
    @Expose
    private Integer pub;
    @SerializedName("rmv")
    @Expose
    private Integer rmv;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("paid")
    @Expose
    private Integer paid;
    @SerializedName("ex_date")
    @Expose
    private String exDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getLvl() {
        return lvl;
    }

    public void setLvl(String lvl) {
        this.lvl = lvl;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
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

    public Integer getPub() {
        return pub;
    }

    public void setPub(Integer pub) {
        this.pub = pub;
    }

    public Integer getRmv() {
        return rmv;
    }

    public void setRmv(Integer rmv) {
        this.rmv = rmv;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public String getExDate() {
        return exDate;
    }

    public void setExDate(String exDate) {
        this.exDate = exDate;
    }

}
