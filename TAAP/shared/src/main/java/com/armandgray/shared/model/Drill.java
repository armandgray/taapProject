package com.armandgray.shared.model;

import com.armandgray.shared.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "drills", indices = {@Index(value = "title", unique = true)})
public class Drill {

    @PrimaryKey
    private int id;

    @NonNull
    private String title;

    @ColumnInfo(name = "image_res_id")
    private int imageResId;

    @TypeConverters(Type.Converter.class)
    @NonNull
    private List<Type> type;

    @TypeConverters(UXPreference.Converter.class)
    @NonNull
    private UXPreference preference;

    @Ignore
    private boolean isActive;

    public Drill(@NonNull String title, int imageResId, @NonNull List<Type> type) {
        this.id = title.hashCode();
        this.title = title;
        this.imageResId = imageResId;
        this.type = type;
        this.preference = new UXPreference(title, UXPreference.Category.REPS_BASED);
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @SuppressWarnings("WeakerAccess") // VisibleForRoom
    public int getReps() {
        int scaleFactor = UXPreference.Item.REPS.getScaleFactor();
        return this.preference.getValue(UXPreference.Item.REPS) / scaleFactor;
    }

    @SuppressWarnings("WeakerAccess") // VisibleForRoom
    public double getGoal() {
        int scaleFactor = UXPreference.Item.GOAL.getScaleFactor();
        return (double) this.preference.getValue(UXPreference.Item.GOAL) / scaleFactor;
    }

    public int getImageResId() {
        return imageResId;
    }

    @NonNull
    public List<Type> getType() {
        return type;
    }

    @NonNull
    public UXPreference getPreference() {
        return preference;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }


    @SuppressWarnings("WeakerAccess") // VisibleForRoom
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setType(@NonNull List<Type> type) {
        this.type = type;
    }

    public void setPreference(@NonNull UXPreference preference) {
        this.preference = preference;
    }

    @SuppressWarnings("WeakerAccess") // VisibleForRoom
    public void setActive(boolean active) {
        this.isActive = active;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Drill{%s:%s}", title, type.toString());
    }

    public enum Type {

        SHOOTING,
        FUNDAMENTALS;

        public static final List<Type> SHOOTING_ONLY = asList(SHOOTING);
        public static final List<Type> SHOOTING_FUNDAMENTALS = asList(SHOOTING, FUNDAMENTALS);

        public static List<Type> asList(Type... types) {
            return Arrays.asList(types);
        }

        public static class Converter {

            private static Gson gson = new Gson();

            @TypeConverter
            public static List<Type> toTypes(String data) {
                java.lang.reflect.Type type = new TypeToken<List<Type>>() {}.getType();
                return data == null ? Collections.emptyList() : gson.fromJson(data, type);
            }

            @TypeConverter
            public static String toString(List<Type> types) {
                return gson.toJson(types);
            }
        }
    }

    public static class Defaults extends ArrayList<Drill> {

        private static final Defaults DEFAULTS = new Defaults();

        private Defaults() {
            int drawable = R.drawable.ic_dribbble_white_48dp;
            List<Type> fundamentals = Type.SHOOTING_FUNDAMENTALS;
            List<Type> shootingOnly = Type.SHOOTING_ONLY;

            // Fundamentals
            this.add(new Drill("Free Throws", drawable, fundamentals));
            this.add(new Drill("Left Finesse", drawable, fundamentals));
            this.add(new Drill("Top Finesse", drawable, fundamentals));
            this.add(new Drill("Right Finesse", drawable, fundamentals));
            // 15fts
            this.add(new Drill("Left-Corner 15ft", drawable, shootingOnly));
            this.add(new Drill("Left-Wing 15ft", drawable, shootingOnly));
            this.add(new Drill("Top 15ft", drawable, shootingOnly));
            this.add(new Drill("Right-Wing 15ft", drawable, shootingOnly));
            this.add(new Drill("Right-Corner 15ft", drawable, shootingOnly));
            // 3s
            this.add(new Drill("Left-Corner 3s", drawable, shootingOnly));
            this.add(new Drill("Left-Wing 3s", drawable, shootingOnly));
            this.add(new Drill("Top 15ft", drawable, shootingOnly));
            this.add(new Drill("Right-Wing 3s", drawable, shootingOnly));
            this.add(new Drill("Right-Corner 3s", drawable, shootingOnly));
            // NBA
            this.add(new Drill("Left-Corner NBA", drawable, shootingOnly));
            this.add(new Drill("Left-Wing NBA", drawable, shootingOnly));
            this.add(new Drill("Top 15ft", drawable, shootingOnly));
            this.add(new Drill("Right-Wing NBA", drawable, shootingOnly));
            this.add(new Drill("Right-Corner NBA", drawable, shootingOnly));
        }

        public static List<Drill> getDefaults() {
            return DEFAULTS;
        }
    }
}
