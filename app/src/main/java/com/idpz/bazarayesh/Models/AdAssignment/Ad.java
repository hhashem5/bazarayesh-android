
package com.idpz.bazarayesh.Models.AdAssignment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ad {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("options")
    @Expose
    private String options;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
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
