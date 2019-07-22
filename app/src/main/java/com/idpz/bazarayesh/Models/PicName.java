package com.idpz.bazarayesh.Models;

/**
 * Created by h on 09/29/2018.
 */

public class PicName {
    private int id;
    private String name;
    private String url;
    private String memo;

    public PicName(String id, String jsj, String name, String memo0) {
    }

    public PicName() {
    }

    public PicName(int id, String name, String url, String memo) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.memo = memo;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
