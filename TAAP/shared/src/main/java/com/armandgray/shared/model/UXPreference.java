package com.armandgray.shared.model;

import android.annotation.SuppressLint;

import com.armandgray.shared.R;
import com.armandgray.shared.helpers.StringHelper;
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

import static com.armandgray.shared.model.UXPreference.Constants.DEFAULT_GOAL;
import static com.armandgray.shared.model.UXPreference.Constants.DEFAULT_REPS;
import static com.armandgray.shared.model.UXPreference.Constants.DEFAULT_TIME;
import static com.armandgray.shared.model.UXPreference.Constants.INT_SCALE;
import static com.armandgray.shared.model.UXPreference.Constants.NUMBER_RANGE;
import static com.armandgray.shared.model.UXPreference.Constants.PERCENT_SCALE;
import static com.armandgray.shared.model.UXPreference.Constants.TOGGLE;
import static com.armandgray.shared.model.UXPreference.Constants.TRIGGERED;

@SuppressLint("UseSparseArrays")
public class UXPreference {

    private static final String TAG = StringHelper.toLogTag(UXPreference.class.getName());

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

        int scale = scaleValue ? item.getScaleFactor() : 1;
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
            return this.item.typeConstant == Constants.TOGGLE && this.value == 0;
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
        VOICE(Item.CALL_OUT, Item.CLAP, Item.PERMISSION),
        DATA(Item.RESET),
        LOCATION(Item.GYM_LOCATION, Item.COURT_LOCATION, Item.PERMISSION),
        WORKOUT(Item.AUTO, Item.TIMEOUT, Item.MINUS, Item.SCREEN_TAPS, Item.CLEAR, Item.VIBRATE,
                Item.BREAK_LIMIT, Item.PERMISSION, Item.RESET),

        // Drill
        REPS_BASED(true, Item.REPS, Item.GOAL, Item.RESET),
        TIME_BASED(true, Item.REPS, Item.GOAL, Item.TIME, Item.RESET);

        @VisibleForTesting
        final List<Item> items;

        private final boolean isDrill;

        Category(Item... items) {
            this(false, items);
        }

        Category(boolean isDrill, Item... items) {
            this.isDrill = isDrill;
            this.items = Arrays.asList(items);
        }

        public boolean isDrillCategory() {
            return this.isDrill;
        }

        @NonNull
        @Override
        public String toString() {
            return String.format(Locale.getDefault(), "Category{%s:%s}", name(), items);
        }
    }

    public enum Item {

        // Location
        GYM_LOCATION("Gym", "Enable Gym Location Updates for Logging Purposes", Constants.TOGGLE, 1, R.drawable.ic_location_white_24dp),
        COURT_LOCATION("Court", "Enable Auto Court Location Updates for Drills", Constants.TOGGLE, 1, R.drawable.ic_gps_fixed_white_24dp),

        // Voice
        CLAP("Clap", "Enable Double/Single Clap for Make/Miss", Constants.TOGGLE, 1, R.drawable.ic_thumbs_up_down_white_24dp),
        CALL_OUT("Call Out", "Enable Calling Out Make/Miss", Constants.TOGGLE, 1, R.drawable.ic_settings_voice_white_24dp),

        // Workout
        BREAK_LIMIT("Break Limit", "Set Break Limit For New Workouts", Constants.NUMBER_RANGE, R.drawable.ic_control_point_duplicate_white_24dp, 30, 15, 60, Constants.MINUTES_SCALE), // in mins
        TIMEOUT("Timeout", "Set Timeout For Single Set", Constants.NUMBER_RANGE, R.drawable.ic_clock_white_24dp, 30, 10, 60, Constants.SECONDS_SCALE), // in secs
        SCREEN_TAPS("Taps", "Enable Single/Double Finger Taps for Make/Miss", Constants.TOGGLE, 0, R.drawable.ic_gesture_two_double_tap_white_48dp),
        AUTO("Auto Tracking", "Enable Auto Drill Tracking", Constants.TOGGLE, 1, R.drawable.ic_flash_auto_white_24dp),
        MINUS("Minus", "Enable Minus Icon For Miss", Constants.TOGGLE, 0, R.drawable.ic_remove_white_24dp),
        VIBRATE("Vibrate", "Enable Vibration On Set Completions", Constants.TOGGLE, 1, R.drawable.ic_vibration_white_24dp),
        CLEAR("Clear", "Enable Clear Current Performance", Constants.TOGGLE, 1, R.drawable.ic_refresh_white_24dp),

        // Drill
        REPS("Reps", "Set Target Reps For Completion", Constants.NUMBER_RANGE, R.drawable.ic_human_handsup_white_48dp, DEFAULT_REPS, 1, 50, INT_SCALE), // in int
        GOAL("Goal", "Set Target Goal For Success", Constants.NUMBER_RANGE, R.drawable.ic_star_white_24dp, DEFAULT_GOAL, 0, 100, PERCENT_SCALE), // in percent
        TIME("Time", "Set Target Time For Completion", Constants.NUMBER_RANGE, R.drawable.ic_timer_white_24dp, DEFAULT_TIME, 0, 600, INT_SCALE), // in seconds

        // Misc
        PERMISSION("Permission", "Provide App Permissions", R.drawable.ic_assignment_white_24dp),
        RESET("Reset", "Reset To Default Settings", R.drawable.ic_delete_sweep_white_24dp),
        TEST("Test", "test", -1, 0, R.drawable.ic_delete_sweep_white_24dp);

        @NonNull
        private final String text;

        @NonNull
        private final String description;

        private final int typeConstant;

        private final int imageResId;

        private final int defaultValue;

        private final int minValue;

        private final int maxValue;

        private final int scaleFactor;

        Item(@NonNull String text, @NonNull String description, int imageResId) {
            this(text, description, Constants.TRIGGERED, imageResId, 0, 0, 0, 0);
        }

        Item(@NonNull String text, @NonNull String description,
             int typeConstant, int enabled, int imageResId) {
            this(text, description, typeConstant, imageResId, enabled, 0, 0, 0);
        }

        Item(@NonNull String text, @NonNull String description, int typeConstant, int imageResId,
             int value, int minValue, int maxValue, int scaleFactor) {
            this.text = text;
            this.description = description;
            this.typeConstant = typeConstant;
            this.imageResId = imageResId;
            this.defaultValue = value;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.scaleFactor = scaleFactor;
        }

        @NonNull
        public String getText() {
            return text;
        }

        @NonNull
        public String getDescription() {
            return description;
        }

        public int getTypeConstant() {
            return typeConstant;
        }

        public int getImageResId() {
            return imageResId;
        }

        public int getDefault() {
            return getDefault(false);
        }

        public int getDefault(boolean scaleValue) {
            return defaultValue * (scaleValue ? scaleFactor : 1);
        }

        public int getMin() {
            return minValue;
        }

        public int getMax() {
            return maxValue;
        }

        public int getScaleFactor() {
            return scaleFactor;
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

    public static class Constants {

        public static final int TRIGGERED = 0;
        public static final int NUMBER_RANGE = 1;
        public static final int TOGGLE = 2;

        public static final int SECONDS_SCALE = 1000;
        public static final int MINUTES_SCALE = 60 * 1000;
        public static final int INT_SCALE = 1;
        public static final int PERCENT_SCALE = 100;

        static final int DEFAULT_REPS = 10;
        static final int DEFAULT_GOAL = 70;
        static final int DEFAULT_TIME = 60;
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
