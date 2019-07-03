
package com.idpz.bazarayesh.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseListMember {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("members")
    @Expose
    private List<Data> members = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Data> getMembers() {
        return members;
    }

    public void setMembers(List<Data> members) {
        this.members = members;
    }

}
