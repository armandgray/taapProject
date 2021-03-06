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
        int scaleFactor = UXPreference.Item.REPS.getScale().getFactor();
        return this.preference.getValue(UXPreference.Item.REPS) / scaleFactor;
    }

    @SuppressWarnings("WeakerAccess") // VisibleForRoom
    public double getGoal() {
        int scaleFactor = UXPreference.Item.GOAL.getScale().getFactor();
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
        MID_RANGE(R.drawable.ic_looks_two_white_24dp),
        THREE_RANGE(R.drawable.ic_looks_three_white_24dp),
        NBA_RANGE(R.drawable.ic_looks_white_24dp),
        FUNDAMENTALS(R.drawable.ic_key_white_48dp);

        public static final List<Type> SHOOTING_ONLY = asList(SHOOTING);
        public static final List<Type> SHOOTING_FUNDAMENTALS = asList(SHOOTING, FUNDAMENTALS);
        public static final List<Type> SHOOTING_15FT = asList(SHOOTING, MID_RANGE);
        public static final List<Type> SHOOTING_THREES = asList(SHOOTING, THREE_RANGE);
        public static final List<Type> SHOOTING_NBA = asList(SHOOTING, NBA_RANGE);

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
            DEFAULT = new Drill("Free Throws", R.drawable.ic_vertical_align_bottom_white_24dp, Type.SHOOTING_FUNDAMENTALS);
            DEFAULTS = new Defaults();
        }


        private Defaults() {
            // Fundamentals
            this.add(DEFAULT);
            this.add(new Drill("Left Finesse", R.drawable.ic_dribbble_white_48dp, Type.SHOOTING_FUNDAMENTALS));
            this.add(new Drill("Top Finesse", R.drawable.ic_dribbble_white_48dp, Type.SHOOTING_FUNDAMENTALS));
            this.add(new Drill("Right Finesse", R.drawable.ic_dribbble_white_48dp, Type.SHOOTING_FUNDAMENTALS));
            // AdHoc
            this.add(new Drill("1-Man Shooting 15ft", R.drawable.ic_looks_one_white_24dp, Type.SHOOTING_15FT));
            this.add(new Drill("1-Man Shooting 3s", R.drawable.ic_looks_two_white_24dp, Type.SHOOTING_NBA));
            this.add(new Drill("1-Man Shooting NBA", R.drawable.ic_looks_white_24dp, Type.SHOOTING_NBA));
            // 15fts
            this.add(new Drill("Left-Corner 15ft", R.drawable.ic_looks_two_white_24dp, Type.SHOOTING_15FT));
            this.add(new Drill("Left-Wing 15ft", R.drawable.ic_looks_two_white_24dp, Type.SHOOTING_15FT));
            this.add(new Drill("Top 15ft", R.drawable.ic_looks_two_white_24dp, Type.SHOOTING_15FT));
            this.add(new Drill("Right-Wing 15ft", R.drawable.ic_looks_two_white_24dp, Type.SHOOTING_15FT));
            this.add(new Drill("Right-Corner 15ft", R.drawable.ic_looks_two_white_24dp, Type.SHOOTING_15FT));
            // 3s
            this.add(new Drill("Left-Corner 3s", R.drawable.ic_looks_three_white_24dp, Type.SHOOTING_THREES));
            this.add(new Drill("Left-Wing 3s", R.drawable.ic_looks_three_white_24dp, Type.SHOOTING_THREES));
            this.add(new Drill("Top 3s", R.drawable.ic_looks_three_white_24dp, Type.SHOOTING_THREES));
            this.add(new Drill("Right-Wing 3s", R.drawable.ic_looks_three_white_24dp, Type.SHOOTING_THREES));
            this.add(new Drill("Right-Corner 3s", R.drawable.ic_looks_three_white_24dp, Type.SHOOTING_THREES));
            // NBA
            this.add(new Drill("Left-Corner NBA", R.drawable.ic_looks_white_24dp, Type.SHOOTING_NBA));
            this.add(new Drill("Left-Wing NBA", R.drawable.ic_looks_white_24dp, Type.SHOOTING_NBA));
            this.add(new Drill("Top NBA", R.drawable.ic_looks_white_24dp, Type.SHOOTING_NBA));
            this.add(new Drill("Right-Wing NBA", R.drawable.ic_looks_white_24dp, Type.SHOOTING_NBA));
            this.add(new Drill("Right-Corner NBA", R.drawable.ic_looks_white_24dp, Type.SHOOTING_NBA));
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
