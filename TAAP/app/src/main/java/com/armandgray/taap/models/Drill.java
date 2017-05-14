package com.armandgray.taap.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Drill implements Parcelable {

    public static final String ALL = "ALL";
    public static final String BALL_HANDLING = "Ball Handling";
    public static final String SHOOTING = "Shooting";
    public static final String PASSING = "Passing";
    public static final String FUNDAMENTALS = "Fundamentals";
    public static final String DEFENSE = "Defense";
    public static final String OFFENSE = "Offense";
    public static final String CONDITIONING = "Conditioning";
    public static final String[] SHOOTING_ARRAY = {SHOOTING};
    public static final String[] DEFENSE_ARRAY = {DEFENSE};
    public static final String[] CONDITIONING_ARRAY = {CONDITIONING};
    public static final String[] OFFENSE_ARRAY = {OFFENSE};
    public static final String[] BALL_HANDLING_ARRAY = {BALL_HANDLING};
    public static final String[] PASSING_ARRAY = {PASSING};
    public static final String[] DRILL_TYPES = {
            BALL_HANDLING, SHOOTING, PASSING, FUNDAMENTALS, DEFENSE, OFFENSE, CONDITIONING};

    private String title;
    private int imageId;
    private String[] category;
    private int drillId;

    public Drill(String title, int imageId, String[] category) {
        this.title = title;
        this.imageId = imageId;
        this.category = category;
    }

    public int getDrillId() {
        return drillId;
    }

    public void setDrillId(int drillId) {
        this.drillId = drillId;
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
        if (drillsList != null) { dataList.addAll(drillsList); }
        return getFilteredListOnQuery(query, dataList);
    }

    private static ArrayList<Drill> getFilteredListOnQuery(String query, ArrayList<Drill> dataList) {
        for (int i = 0; i < dataList.size(); i++) {
            if (!dataList.get(i).getTitle().toLowerCase().contains(query.toLowerCase())) {
                dataList.remove(i);
                i--;
            }
        }
        return dataList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.imageId);
        dest.writeStringArray(this.category);
        dest.writeInt(this.drillId);
    }

    protected Drill(Parcel in) {
        this.title = in.readString();
        this.imageId = in.readInt();
        this.category = in.createStringArray();
        this.drillId = in.readInt();
    }

    public static final Parcelable.Creator<Drill> CREATOR = new Parcelable.Creator<Drill>() {
        @Override
        public Drill createFromParcel(Parcel source) {
            return new Drill(source);
        }

        @Override
        public Drill[] newArray(int size) {
            return new Drill[size];
        }
    };
}
