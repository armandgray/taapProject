package com.armandgray.shared.model;

import android.annotation.SuppressLint;

import com.armandgray.shared.R;
import com.armandgray.shared.permission.DangerousPermission;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.TypeConverter;

@SuppressLint("UseSparseArrays")
public class UXPreference {

    private final String title;
    private final Category category;
    private final List<Value> values;

    UXPreference(String title, Category category) {
        this.title = title;
        this.category = category;
        this.values = category.items.stream()
                .map(Value::new)
                .collect(Collectors.toList());
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public Category getCategory() {
        return category;
    }

    @NonNull
    public List<Value> getValues() {
        return values;
    }

    public int getValue(@NonNull Item item) {
        return getValue(item, false);
    }

    public int getValue(@NonNull Item item, boolean scaleValue) {
        Value value = values.stream()
                .filter(next -> next.item.equals(item))
                .findFirst()
                .orElse(null);

        int scale = scaleValue ? item.getScale().getFactor() : 1;
        return value == null ? 0 : value.value * scale;
    }

    public boolean isEnabled(@NonNull Item item) {
        Value value = values.stream()
                .filter(next -> next.item.equals(item))
                .findFirst()
                .orElse(null);

        return value != null && value.isEnabled();
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "UXPreference{%s}", values);
    }

    public static class Value {

        @NonNull
        private final Item item;
        private int value;

        Value(@NonNull Item item) {
            this.item = item;
            this.value = item.defaultValue;
        }

        @NonNull
        public Item getItem() {
            return item;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public boolean isEnabled() {
            return this.item.typeConstant == TypeConstant.TOGGLE && this.value == 0;
        }

        public void setEnabled(boolean enabled) {
            setValue(enabled ? 0 : 1);
        }

        @NonNull
        @Override
        public String toString() {
            switch (item.getTypeConstant()) {
                case NUMBER_RANGE:
                    return String.format(Locale.getDefault(), "Value{%s -> %d}", item, value);

                case TOGGLE:
                    return String.format(Locale.getDefault(), "Value{%s -> %s}", item,
                            isEnabled() ? "Enabled" : "Disabled");

                case TRIGGERED:
                    return String.format(Locale.getDefault(), "Value{%s}", item);

                default:
                    return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
            }
        }
    }

    public enum Category {

        // Settings
        VOICE(DangerousPermission.MICROPHONE, Item.CALL_OUT, Item.CLAP, Item.VOICE_TIMEOUT, Item.RESET),
        DATA(Item.EXPORT, Item.RESET),
        LOCATION(DangerousPermission.LOCATION, Item.GYM_LOCATION, Item.COURT_LOCATION, Item.RESET),
        WORKOUT(Item.AUTO, Item.TIMEOUT, Item.ICONS, Item.SCREEN_TAPS, Item.CLEAR, Item.VIBRATE,
                Item.BREAK_LIMIT, Item.RESET),

        // Drill
        REPS_BASED(true, Item.REPS, Item.GOAL, Item.RESET),
        TIME_BASED(true, Item.REPS, Item.GOAL, Item.TIME, Item.RESET);

        @VisibleForTesting
        final List<Item> items;

        private final boolean isDrill;

        @NonNull
        private final DangerousPermission permission;

        Category(Item... items) {
            this(DangerousPermission.NONE, false, items);
        }

        Category(@NonNull DangerousPermission permission, Item... items) {
            this(permission, false, items);
        }

        Category(boolean isDrill, Item... items) {
            this(DangerousPermission.NONE, isDrill, items);
        }

        Category(@NonNull DangerousPermission permission, boolean isDrill, Item... items) {
            this.items = Arrays.asList(items);
            this.isDrill = isDrill;
            this.permission = permission;
        }

        public boolean isDrillCategory() {
            return this.isDrill;
        }

        @NonNull
        public DangerousPermission getPermission() {
            return permission;
        }

        @NonNull
        @Override
        public String toString() {
            return String.format(Locale.getDefault(), "Category{%s:%s}", name(), items);
        }
    }

    public enum Item {

        // Location
        GYM_LOCATION("Gym", "Enable Gym Location Updates for Logging Purposes", TypeConstant.TOGGLE, 0, R.drawable.ic_location_white_24dp, "Feature May Impact Battery"),
        COURT_LOCATION("Court", "Enable Auto Court Location Updates for Drills", TypeConstant.TOGGLE, 0, R.drawable.ic_gps_fixed_white_24dp, "Feature May Impact Battery"),

        // Voice
        CLAP("Clap", "Enable Double/Single Clap for Make/Miss", TypeConstant.TOGGLE, 1, R.drawable.ic_thumbs_up_down_white_24dp, "Performs Best With AutoTracking"),
        CALL_OUT("Call Out", "Enable Calling Out Make/Miss", TypeConstant.TOGGLE, 1, R.drawable.ic_settings_voice_white_24dp, "Performs Best With AutoTracking"),
        VOICE_TIMEOUT("Timeout", "Set Timeout For Voice Recognition", TypeConstant.NUMBER_RANGE, R.drawable.ic_clock_white_24dp, 5, 1, 30, Scale.SECONDS_SCALE), // in secs

        // Workout
        BREAK_LIMIT("Break Limit", "Set Break Limit For New Workouts", TypeConstant.NUMBER_RANGE, R.drawable.ic_control_point_duplicate_white_24dp, 30, 15, 60, Scale.MINUTES_SCALE), // in mins
        TIMEOUT("Timeout", "Set Timeout For Single Set", TypeConstant.NUMBER_RANGE, R.drawable.ic_clock_white_24dp, 30, 10, 60, Scale.SECONDS_SCALE), // in secs
        VIBRATE("Vibrate", "Set Vibration Length On Set Completions", TypeConstant.NUMBER_RANGE, R.drawable.ic_vibration_white_24dp, 5, 0, 30, Scale.TENTHS_OF_SECONDS_SCALE), // in tenths_secs
        SCREEN_TAPS("Taps", "Enable Single/Double Finger Taps for Make/Miss", TypeConstant.TOGGLE, 1, R.drawable.ic_gesture_two_double_tap_white_48dp, ""),
        AUTO("Auto Tracking", "Enable Auto Drill Tracking", TypeConstant.TOGGLE, 1, R.drawable.ic_flash_auto_white_24dp, "Feature May Impact Battery"),
        ICONS("Icons", "Enable Plus/Minus Icon For Make/Miss", TypeConstant.TOGGLE, 0, R.drawable.ic_remove_white_24dp, ""),
        CLEAR("Clear", "Enable Clear Current Performance", TypeConstant.TOGGLE, 1, R.drawable.ic_refresh_white_24dp, ""),

        // Drill
        REPS("Reps", "Set Target Reps For Completion", TypeConstant.NUMBER_RANGE, R.drawable.ic_human_handsup_white_48dp, 10, 1, 50, Scale.INT_SCALE), // in int
        GOAL("Goal", "Set Target Goal For Success", TypeConstant.NUMBER_RANGE, R.drawable.ic_star_white_24dp, 70, 0, 100, Scale.PERCENT_SCALE), // in percent
        TIME("Time", "Set Target Time For Completion", TypeConstant.NUMBER_RANGE, R.drawable.ic_timer_white_24dp, 60, 0, 600, Scale.INT_SCALE), // in seconds

        // Misc
        EXPORT("Export", "Export Performance Data", R.drawable.ic_share_white_24dp),
        RESET("Reset", "Reset To Default Settings", R.drawable.ic_delete_sweep_white_24dp),
        TEST("Test", "test", TypeConstant.NONE, 0, R.drawable.ic_delete_sweep_white_24dp, "");

        @NonNull
        private final String text;

        @NonNull
        private final String description;

        @NonNull
        private final TypeConstant typeConstant;

        private final int imageResId;

        private final int defaultValue;

        private final int minValue;

        private final int maxValue;

        @NonNull
        private final Scale scale;

        @NonNull
        private final String warning;

        Item(@NonNull String text, @NonNull String description, int imageResId) {
            this(text, description, TypeConstant.TRIGGERED, imageResId, 0, 0, 0, Scale.NONE, "");
        }

        Item(@NonNull String text, @NonNull String description, @NonNull TypeConstant typeConstant,
             int enabled, int imageResId, @NonNull String warning) {
            this(text, description, typeConstant, imageResId, enabled, 0, 0, Scale.NONE, warning);
        }

        Item(@NonNull String text, @NonNull String description, @NonNull TypeConstant typeConstant,
             int imageResId, int value, int minValue, int maxValue, @NonNull Scale scale) {
            this(text, description, typeConstant, imageResId, value, minValue, maxValue, scale, "");
        }

        Item(@NonNull String text, @NonNull String description, @NonNull TypeConstant typeConstant,
             int imageResId, int value, int minValue, int maxValue, @NonNull Scale scale,
             @NonNull String warning) {
            this.text = text;
            this.description = description;
            this.typeConstant = typeConstant;
            this.imageResId = imageResId;
            this.defaultValue = value;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.scale = scale;
            this.warning = warning;
        }

        @NonNull
        public String getText() {
            return text;
        }

        @NonNull
        public String getDescription() {
            return description;
        }

        @NonNull
        public TypeConstant getTypeConstant() {
            return typeConstant;
        }

        public int getImageResId() {
            return imageResId;
        }

        public int getDefault() {
            return getDefault(false);
        }

        public int getDefault(boolean scaleValue) {
            return defaultValue * (scaleValue ? scale.getFactor() : 1);
        }

        public boolean isDefaultEnabled() {
            return typeConstant == TypeConstant.TOGGLE && defaultValue == 0;
        }

        public int getMin() {
            return minValue;
        }

        public int getMax() {
            return maxValue;
        }

        @NonNull
        public Scale getScale() {
            return scale;
        }

        @NonNull
        public String getWarning() {
            return warning;
        }

        public boolean hasWarning() {
            return warning.length() != 0;
        }

        @NonNull
        @Override
        public String toString() {
            switch (getTypeConstant()) {
                case NUMBER_RANGE:
                    return String.format(Locale.getDefault(), "Item{%s:%d < %d < %d}",
                            name(), minValue, defaultValue, maxValue);

                case TOGGLE:
                    return String.format(Locale.getDefault(), "Item{%s: %s}",
                            name(), defaultValue == 0 ? "Enabled" : "Disabled");

                case TRIGGERED:
                    return String.format(Locale.getDefault(), "Item{%s}", name());

                default:
                    return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
            }
        }
    }

    public enum TypeConstant {NONE, TRIGGERED, NUMBER_RANGE, TOGGLE}

    public enum Scale {

        NONE(0),
        TENTHS_OF_SECONDS_SCALE(100),
        SECONDS_SCALE(1000),
        MINUTES_SCALE(60 * 1000),
        INT_SCALE(1),
        PERCENT_SCALE(100);

        private final int factor;

        Scale(int factor) {
            this.factor = factor;
        }

        public int getFactor() {
            return factor;
        }
    }

    public static class Converter {

        private static Gson gson = new Gson();

        @TypeConverter
        public static UXPreference toPreference(String data) {
            Type type = new TypeToken<UXPreference>() {
            }.getType();
            return data != null ? gson.fromJson(data, type) : null;
        }

        @TypeConverter
        public static String toString(UXPreference preference) {
            return gson.toJson(preference);
        }
    }
}
