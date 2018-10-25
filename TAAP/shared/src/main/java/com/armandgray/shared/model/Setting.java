package com.armandgray.shared.model;

import com.armandgray.shared.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "settings", indices = {@Index(value = "title", unique = true)})
public class Setting implements Comparable<Setting> {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private String title;

    private int imageResId;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private UXPreference preference;

    public Setting() {
        // Default Constructor For Room Object Creation
    }

    @Ignore
    public Setting(@NonNull String title, int imageResId, @NonNull UXPreference.Category preferenceCategory) {
        this.title = title;
        this.imageResId = imageResId;
        this.preference = new UXPreference(title, preferenceCategory);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }

    @NonNull
    public UXPreference getPreference() {
        return preference;
    }

    // VisibleForRoom
    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @SuppressWarnings("unused") // VisibleForRoom
    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }

    // VisibleForRoom
    public void setPreference(@NonNull UXPreference preference) {
        this.preference = preference;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(@Nullable Object that) {
        return that instanceof Setting && this.hashCode() == that.hashCode()
                && this.title.equals(((Setting) that).title)
                && this.preference.getCategory() == ((Setting) that).preference.getCategory();
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Setting(%d){%s, %s}", id, title, preference);
    }

    @Override
    public int compareTo(Setting that) {
        return that == null ? 1 : this.id - that.id;
    }

    public static class Defaults extends ArrayList<Setting> {

        private static final Setting.Defaults DEFAULTS;

        static {
            DEFAULTS = new Setting.Defaults();
        }

        private Defaults() {
            this.add(new Setting("Voice",
                    R.drawable.ic_dribbble_white_48dp, UXPreference.Category.VOICE));
            this.add(new Setting("Workout",
                    R.drawable.ic_dumbbell_white_24dp, UXPreference.Category.WORKOUT));
            this.add(new Setting("Data",
                    R.drawable.ic_data_usage_white_24dp, UXPreference.Category.DATA));
            this.add(new Setting("Location",
                    R.drawable.ic_location_white_24dp, UXPreference.Category.LOCATION));
        }

        @NonNull
        public static List<Setting> getDefaults() {
            return DEFAULTS;
        }
    }
}
