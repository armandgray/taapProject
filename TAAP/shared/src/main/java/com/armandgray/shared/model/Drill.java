package com.armandgray.shared.model;

import com.armandgray.shared.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "drills")
public class Drill {

    @PrimaryKey
    private int id;

    @NonNull
    private String title;

    @ColumnInfo(name = "image_res_id")
    private int imageResId;

    @TypeConverters(Category.Converter.class)
    @NonNull
    private List<Category> category;

    @Ignore
    private boolean isActive;

    @Ignore
    @NonNull
    private final Performance performance;

    public Drill(@NonNull String title, int imageResId, @NonNull List<Category> category) {
        this.title = title;
        this.imageResId = imageResId;
        this.category = category;
        this.performance = new Performance(this.title.hashCode());
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }

    @NonNull
    public List<Category> getCategory() {
        return category;
    }

    @NonNull
    public Performance getPerformance() {
        return performance;
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

    @SuppressWarnings("WeakerAccess")
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    public void setCategory(@NonNull List<Category> category) {
        this.category = category;
    }

    @SuppressWarnings("WeakerAccess")
    public void setActive(boolean active) {
        this.isActive = active;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Drill{%s:%s}", title, category.toString());
    }

    public enum Category {

        SHOOTING,
        FUNDAMENTALS;

        public static final List<Category> SHOOTING_ONLY = asList(SHOOTING);
        public static final List<Category> SHOOTING_FUNDAMENTALS = asList(SHOOTING, FUNDAMENTALS);

        public static List<Category> asList(Category... categories) {
            return Arrays.asList(categories);
        }

        public static class Converter {

            private static Gson gson = new Gson();

            @TypeConverter
            public static List<Category> toCategories(String data) {
                Type type = new TypeToken<List<Category>>() {}.getType();
                return data == null ? Collections.emptyList() : gson.fromJson(data, type);
            }

            @TypeConverter
            public static String toString(List<Category> categories) {
                return gson.toJson(categories);
            }
        }
    }

    public static class Defaults extends ArrayList<Drill> {

        private static final Defaults DEFAULTS = new Defaults();

        private Defaults() {
            int drawable = R.drawable.ic_dribbble_white_48dp;
            // Fundamentals
            this.add(new Drill("Free Throws", drawable, Category.SHOOTING_FUNDAMENTALS));
            this.add(new Drill("Left Finesse", drawable, Category.SHOOTING_FUNDAMENTALS));
            this.add(new Drill("Top Finesse", drawable, Category.SHOOTING_FUNDAMENTALS));
            this.add(new Drill("Right Finesse", drawable, Category.SHOOTING_FUNDAMENTALS));
            // 15fts
            this.add(new Drill("Left-Corner 15ft", drawable, Category.SHOOTING_ONLY));
            this.add(new Drill("Left-Wing 15ft", drawable, Category.SHOOTING_ONLY));
            this.add(new Drill("Top 15ft", drawable, Category.SHOOTING_ONLY));
            this.add(new Drill("Right-Wing 15ft", drawable, Category.SHOOTING_ONLY));
            this.add(new Drill("Right-Corner 15ft", drawable, Category.SHOOTING_ONLY));
            // 3s
            this.add(new Drill("Left-Corner 3s", drawable, Category.SHOOTING_ONLY));
            this.add(new Drill("Left-Wing 3s", drawable, Category.SHOOTING_ONLY));
            this.add(new Drill("Top 15ft", drawable, Category.SHOOTING_ONLY));
            this.add(new Drill("Right-Wing 3s", drawable, Category.SHOOTING_ONLY));
            this.add(new Drill("Right-Corner 3s", drawable, Category.SHOOTING_ONLY));
            // NBA
            this.add(new Drill("Left-Corner NBA", drawable, Category.SHOOTING_ONLY));
            this.add(new Drill("Left-Wing NBA", drawable, Category.SHOOTING_ONLY));
            this.add(new Drill("Top 15ft", drawable, Category.SHOOTING_ONLY));
            this.add(new Drill("Right-Wing NBA", drawable, Category.SHOOTING_ONLY));
            this.add(new Drill("Right-Corner NBA", drawable, Category.SHOOTING_ONLY));
        }

        public static List<Drill> getDefaults() {
            return DEFAULTS;
        }
    }
}
