package com.armandgray.shared.model;

import com.armandgray.shared.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

@Entity(tableName = "drills", indices = {@Index(value = "title", unique = true)})
public class Drill {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private String title;

    @ColumnInfo(name = "image_res_id")
    private int imageResId;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private List<Type> type;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private UXPreference preference;

    public Drill() {
        // Default Constructor For Room Object Creation
    }

    @Ignore
    public Drill(@NonNull String title, int imageResId, @NonNull List<Type> type) {
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

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object that) {
        return that instanceof Drill && this.title.equals(((Drill) that).title);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Drill{%s:%s}", title, type.toString());
    }

    public enum Type {

        SHOOTING(R.drawable.ic_dribbble_white_48dp),
        FUNDAMENTALS(R.drawable.ic_key_white_48dp);

        public static final List<Type> SHOOTING_ONLY = asList(SHOOTING);
        public static final List<Type> SHOOTING_FUNDAMENTALS = asList(SHOOTING, FUNDAMENTALS);

        private final int imageResId;

        Type(int imageResId) {
            this.imageResId = imageResId;
        }

        public int getImageResId() {
            return imageResId;
        }

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

        private static final Drill DEFAULT;
        private static final Defaults DEFAULTS;

        static {
            DEFAULT = new Drill("Free Throws", R.drawable.ic_dribbble_white_48dp, Type.SHOOTING_FUNDAMENTALS);
            DEFAULTS = new Defaults();
        }


        private Defaults() {
            int drawable = R.drawable.ic_dribbble_white_48dp;
            List<Type> fundamentals = Type.SHOOTING_FUNDAMENTALS;
            List<Type> shootingOnly = Type.SHOOTING_ONLY;

            // Fundamentals
            this.add(DEFAULT);
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

        @NonNull
        public static List<Drill> getDefaults() {
            return DEFAULTS;
        }

        @NonNull
        public static Drill getDefault() {
            return DEFAULT;
        }
    }
}
