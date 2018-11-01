package com.armandgray.shared.permission;

import android.Manifest;

import java.util.Arrays;

import androidx.annotation.NonNull;

public enum DangerousPermission {

    ERROR("", "", "", 0),
    NONE("", "", "", 0),
    LOCATION("Location Access", "Location access used for Auto Tracking & Gyms", Manifest.permission.ACCESS_FINE_LOCATION, 1),
    MICROPHONE("Microphone Access", "Microphone access used for Call Out & Clap", Manifest.permission.RECORD_AUDIO, 2);

    @NonNull
    private final String title;

    @NonNull
    private final String rationale;

    @NonNull
    private final String key;

    private final int code;

    DangerousPermission(@NonNull String title, @NonNull String rationale,
                        @NonNull String key, int code) {
        this.title = title;
        this.rationale = rationale;
        this.key = key;
        this.code = code;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getRationale() {
        return rationale;
    }

    @NonNull
    public String getKey() {
        return key;
    }

    public int getCode() {
        return code;
    }

    @NonNull
    public static DangerousPermission getPermission(int code) {
        return Arrays.stream(DangerousPermission.values())
                .filter(permission -> equals(permission, code))
                .findFirst()
                .orElse(ERROR);
    }

    private static boolean equals(DangerousPermission permission, int code) {
        return permission != null && permission.code == code;
    }
}
