package com.idpz.bazarayesh.Models;

public class MainItem {

    public String id;

    public int Image;

    public String color;

    public String title;

    public String tag;


    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public MainItem(String id, int image, String color, String title) {
        this.id = id;
        Image = image;
        this.color = color;
        this.title = title;
    }

    public MainItem(int image, String title) {
        Image = image;
        this.title = title;
    }


    public MainItem(int image, String title,String tag) {
        Image = image;
        this.title = title;
        this.tag=tag;

    }




    public MainItem() {
    }

    public MainItem(String title) {
        this.title = title;
    }

    public MainItem( String title,String id) {
        this.id = id;
        this.title = title;
    }
}
