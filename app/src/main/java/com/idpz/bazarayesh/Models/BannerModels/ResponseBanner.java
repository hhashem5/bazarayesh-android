
package com.idpz.bazarayesh.Models.BannerModels;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBanner {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("baners")
    @Expose
    private List<Baner> baners = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Baner> getBaners() {
        return baners;
    }

    public void setBaners(List<Baner> baners) {
        this.baners = baners;
    }

}
