
package com.idpz.bazarayesh.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("manager_name")
    @Expose
    private String managerName;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("teaser")
    @Expose
    private Integer teaser;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("phone1")
    @Expose
    private String phone1;
    @SerializedName("phone2")
    @Expose
    private String phone2;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("telegram")
    @Expose
    private String telegram;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("service")
    @Expose
    private List<Service> service ;
    @SerializedName("award")
    @Expose
    private List<Award> award;
    @SerializedName("course")
    @Expose
    private List<Course> course ;
    @SerializedName("workplace_pic")
    @Expose
    private List<WorkplacePic> workplacePic ;
    @SerializedName("famous_customer")
    @Expose
    private List<FamousCustomer> famousCustomer;

    @SerializedName("pub")
    @Expose
    private int pub;

    @SerializedName("view")
    @Expose
    private int view;

//    @SerializedName("Workshops")
//    @Expose
//    private List<> todo


    public int getPub() {
        return pub;
    }

    public void setPub(int pub) {
        this.pub = pub;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getTeaser() {
        return teaser;
    }

    public void setTeaser(Integer teaser) {
        this.teaser = teaser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public List<Service> getService() {
        return service;
    }

    public void setService(List<Service> service) {
        this.service = service;
    }

    public List<Award> getAward() {
        return award;
    }

    public void setAward(List<Award> award) {
        this.award = award;
    }

    public List<Course> getCourse() {
        return course;
    }

    public void setCourse(List<Course> course) {
        this.course = course;
    }

    public List<WorkplacePic> getWorkplacePic() {
        return workplacePic;
    }

    public void setWorkplacePic(List<WorkplacePic> workplacePic) {
        this.workplacePic = workplacePic;
    }

    public List<FamousCustomer> getFamousCustomer() {
        return famousCustomer;
    }

    public void setFamousCustomer(List<FamousCustomer> famousCustomer) {
        this.famousCustomer = famousCustomer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
