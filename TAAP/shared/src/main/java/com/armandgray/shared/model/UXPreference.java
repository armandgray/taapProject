package com.armandgray.shared.model;

import android.annotation.SuppressLint;

import com.armandgray.shared.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import static com.armandgray.shared.model.UXPreference.Constants.DEFAULT_GOAL;
import static com.armandgray.shared.model.UXPreference.Constants.DEFAULT_REPS;
import static com.armandgray.shared.model.UXPreference.Constants.DEFAULT_TIME;
import static com.armandgray.shared.model.UXPreference.Constants.INT_SCALE;
import static com.armandgray.shared.model.UXPreference.Constants.PERCENT_SCALE;

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

    @SuppressWarnings("ConstantConditions")
    public Item getItem(@NonNull String itemName) {
        return values.stream()
                .map(value -> value.item)
                .reduce((reduce, next) -> next.equals(Item.valueOf(itemName)) ? next : reduce)
                .get();
    }

    @SuppressWarnings("ConstantConditions")
    public int getValue(@NonNull Item item) {
        return values.stream()
                .reduce((reduce, next) -> next.item.equals(item) ? next : reduce)
                .get().value;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "UXPreference{%s}", values);
    }

    public static class Value {

        private final Item item;
        private int value;

        Value(Item item) {
            this.item = item;
            this.value = item.defaultValue;
        }

        public Item getItem() {
            return item;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        @NonNull
        @Override
        public String toString() {
            return String.format(Locale.getDefault(), "Value{%s -> %d}", item, value);
        }
    }

    public enum Category {

        DATA(),
        REPS_BASED(Item.REPS, Item.GOAL),
        TIME_BASED(Item.REPS, Item.GOAL, Item.TIME);

        public static final List<Category> DRILL = Arrays.asList(REPS_BASED, TIME_BASED);

        private final List<Item> items;

        Category(Item... items) {
            this.items = Arrays.asList(items);
        }

        public boolean isDrillCategory() {
            return DRILL.contains(this);
        }

        @NonNull
        @Override
        public String toString() {
            return String.format(Locale.getDefault(), "Category{%s:%s}", name(), items);
        }
    }

    public enum Item {

        REPS("Reps", R.drawable.ic_human_handsup_white_48dp, DEFAULT_REPS, 1, 50, INT_SCALE), // in int
        GOAL("Goal", R.drawable.ic_star_white_24dp, DEFAULT_GOAL, 0, 100, PERCENT_SCALE), // in percent
        TIME("Time", R.drawable.ic_timer_white_24dp, DEFAULT_TIME, 0, 600, INT_SCALE); // in seconds

        private final String text;
        private final int imageResId;
        private final int defaultValue;
        private final int minValue;
        private final int maxValue;
        private final int scaleFactor;

        Item(String text, int imageResId, int value, int minValue, int maxValue, int scaleFactor) {
            this.text = text;
            this.imageResId = imageResId;
            this.defaultValue = value;
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.scaleFactor = scaleFactor;
        }

        public String getText() {
            return text;
        }

        public int getImageResId() {
            return imageResId;
        }

        public int getDefault() {
            return defaultValue;
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
            return String.format(Locale.getDefault(), "Item{%s:%d < %d < %d}",
                    name(), minValue, defaultValue, maxValue);
        }
    }

    public static class Constants {

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
            Type type = new TypeToken<UXPreference>() {}.getType();
            return data != null ? gson.fromJson(data, type) : null;
        }

        @TypeConverter
        public static String toString(UXPreference preference) {
            return gson.toJson(preference);
        }
    }
}
