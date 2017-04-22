package com.armandgray.taap.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Drill {

    public static final String ALL = "ALL";
    public static final String BALL_HANDLING = "Ball Handling";
    public static final String SHOOTING = "Shooting";
    public static final String PASSING = "Passing";
    public static final String FUNDAMENTALS = "Fundamentals";
    public static final String[] SHOOTING_ARRAY = {SHOOTING};
    public static final String[] BALL_HANDLING_ARRAY = {BALL_HANDLING};
    public static final String[] PASSING_ARRAY = {PASSING};
    public static final String[] DRILL_TYPES = {BALL_HANDLING, SHOOTING, PASSING, FUNDAMENTALS};

    private String title;
    private int imageId;
    private String[] category;

    public Drill(String title, int imageId, String[] category) {
        this.title = title;
        this.imageId = imageId;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }

    public String[] getCategory() {
        return category;
    }

    @NonNull
    public static ArrayList<Drill> getQueryResultList(ArrayList<Drill> drillsList, String query) {
        ArrayList<Drill> dataList = new ArrayList<>();
        if (drillsList != null) { dataList = drillsList; }
        return getFilteredListOnQuery(query, dataList);
    }

    private static ArrayList<Drill> getFilteredListOnQuery(String query, ArrayList<Drill> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            if (!dataList.get(i).getTitle().toLowerCase().contains(query)) {
                dataList.remove(i);
                i--;
            }
        }
        return dataList;
    }

}
