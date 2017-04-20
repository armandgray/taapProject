package com.armandgray.taap.models;

public class Drill {

    private String title;
    private int imageId;

    public Drill(String title, int imageId) {
        this.title = title;
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }
}
