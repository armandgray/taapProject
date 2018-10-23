package com.armandgray.shared.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

public class WorkoutLocation {

    private final String title;

    public WorkoutLocation(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "Location{%s}", title);
    }

    public static class Converter {

        private static Gson gson = new Gson();

        @TypeConverter
        public static WorkoutLocation toLocation(String data) {
            Type type = new TypeToken<WorkoutLocation>() {}.getType();
            return data != null ? gson.fromJson(data, type) : null;
        }

        @TypeConverter
        public static String toString(WorkoutLocation location) {
            return gson.toJson(location);
        }
    }
}
