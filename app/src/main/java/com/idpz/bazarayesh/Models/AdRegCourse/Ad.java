
package com.idpz.bazarayesh.Models.AdRegCourse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ad {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mem_id")
    @Expose
    private Integer memId;
    @SerializedName("scourse")
    @Expose
    private String scourse;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("ecourse")
    @Expose
    private String ecourse;
    @SerializedName("course_name")
    @Expose
    private String courseName;
    @SerializedName("evidence")
    @Expose
    private String evidence;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("pub")
    @Expose
    private Integer pub;
    @SerializedName("rmv")
    @Expose
    private Integer rmv;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
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

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public String getScourse() {
        return scourse;
    }

    public void setScourse(String scourse) {
        this.scourse = scourse;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEcourse() {
        return ecourse;
    }

    public void setEcourse(String ecourse) {
        this.ecourse = ecourse;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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
