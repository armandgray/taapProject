package com.armandgray.shared.model;

import com.armandgray.shared.R;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;

public class Drill {

    private final int id;

    private final int imageResId;

    private boolean isActive;

    @NonNull
    private final String title;

    @NonNull
    private final List<Category> category;

    @NonNull
    private final PerformanceRate performanceRate;

    public Drill(@NonNull String title, int imageResId, @NonNull List<Category> category) {
        this.id = title.hashCode();
        this.title = title;
        this.imageResId = imageResId;
        this.category = category;
        this.performanceRate = new PerformanceRate();
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
    public PerformanceRate getPerformance() {
        return performanceRate;
    }

    public boolean isActive() {
        return this.isActive;
    }

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
    }

    public static class Defaults {

        private static final List<Drill> DEFAULTS;

        static {
            int drawable = R.drawable.ic_dribbble_white_48dp;
            DEFAULTS = Arrays.asList(
                    // Fundamentals
                    new Drill("Free Throws", drawable, Drill.Category.SHOOTING_FUNDAMENTALS),
                    new Drill("Left Finesse", drawable, Drill.Category.SHOOTING_FUNDAMENTALS),
                    new Drill("Top Finesse", drawable, Drill.Category.SHOOTING_FUNDAMENTALS),
                    new Drill("Right Finesse", drawable, Drill.Category.SHOOTING_FUNDAMENTALS),
                    // 15fts
                    new Drill("Left-Corner 15ft", drawable, Drill.Category.SHOOTING_ONLY),
                    new Drill("Left-Wing 15ft", drawable, Drill.Category.SHOOTING_ONLY),
                    new Drill("Top 15ft", drawable, Drill.Category.SHOOTING_ONLY),
                    new Drill("Right-Wing 15ft", drawable, Drill.Category.SHOOTING_ONLY),
                    new Drill("Right-Corner 15ft", drawable, Drill.Category.SHOOTING_ONLY),
                    // 3s
                    new Drill("Left-Corner 3s", drawable, Drill.Category.SHOOTING_ONLY),
                    new Drill("Left-Wing 3s", drawable, Drill.Category.SHOOTING_ONLY),
                    new Drill("Top 15ft", drawable, Drill.Category.SHOOTING_ONLY),
                    new Drill("Right-Wing 3s", drawable, Drill.Category.SHOOTING_ONLY),
                    new Drill("Right-Corner 3s", drawable, Drill.Category.SHOOTING_ONLY),
                    // NBA
                    new Drill("Left-Corner NBA", drawable, Drill.Category.SHOOTING_ONLY),
                    new Drill("Left-Wing NBA", drawable, Drill.Category.SHOOTING_ONLY),
                    new Drill("Top 15ft", drawable, Drill.Category.SHOOTING_ONLY),
                    new Drill("Right-Wing NBA", drawable, Drill.Category.SHOOTING_ONLY),
                    new Drill("Right-Corner NBA", drawable, Drill.Category.SHOOTING_ONLY)
            );
        }

        private Defaults() {
            // Helper
        }

        public static Drill getDefault() {
            return DEFAULTS.get(0);
        }

        public static List<Drill> getDefaults() {
            return DEFAULTS;
        }
    }
}
