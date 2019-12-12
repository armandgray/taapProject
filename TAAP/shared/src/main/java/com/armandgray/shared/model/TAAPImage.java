package com.armandgray.shared.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

/**
 * Created by armandgray on 7/13/18
 */
public class TAAPImage implements Parcelable {

    public static final DiffUtil.ItemCallback<TAAPImage> DIFF_CALLBACK = new DiffUtil.ItemCallback<TAAPImage>() {
        @Override
        public boolean areItemsTheSame(@NonNull TAAPImage old, @NonNull TAAPImage newItem) {
            return old.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull TAAPImage old, @NonNull TAAPImage newItem) {
            return old.getImageUrl().equals(newItem.getImageUrl());
        }
    };

    private static final String IMAGE_URL_FORMAT = "https://farm%d.staticflickr.com/%s/%s_%s%s.jpg";
    private static final int DEFAULT = 0;
    private static final int FULL = 1;
    private static final int THUMBNAIL = 2;

    @Expose
    private final String id;

    @Expose
    private final String owner;

    @Expose
    private final String secret;

    @Expose
    private final String server;

    @Expose
    private final int farm;

    @Expose
    private final String title;

    @SuppressWarnings("SpellCheckingInspection")
    @SerializedName("ispublic")
    @Expose
    private final int isPublic;

    @SuppressWarnings("SpellCheckingInspection")
    @SerializedName("isfriend")
    @Expose
    private final int isFriend;

    @SuppressWarnings("SpellCheckingInspection")
    @SerializedName("isfamily")
    @Expose
    private final int isFamily;

    @SuppressWarnings("unused")
    public TAAPImage(String id, String owner, String secret, String server, int farm, String title,
                     int isPublic, int isFriend, int isFamily) {
        this.id = id;
        this.owner = owner;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
        this.title = title;
        this.isPublic = isPublic;
        this.isFriend = isFriend;
        this.isFamily = isFamily;
    }

    @NonNull
    private String getFormattedSize(int size) {
        switch (size) {
            case FULL:
                return "_o";

            case THUMBNAIL:
                return "_t";

            default:
                return "_b";
        }
    }

    @SuppressWarnings("unused")
    public String getOwner() {
        return owner;
    }

    public String getTitle() {
        return title;
    }

    @SuppressWarnings("unused")
    public boolean isPublic() {
        return isPublic == 1;
    }

    @SuppressWarnings("unused")
    public boolean isFriend() {
        return isFriend == 1;
    }

    @SuppressWarnings("unused")
    public boolean isFamily() {
        return isFamily == 1;
    }

    public String getImageUrl() {
        return getFormattedUrl(farm, server, id, secret, DEFAULT);
    }

    public String getThumbnailUrl() {
        return getFormattedUrl(farm, server, id, secret, THUMBNAIL);
    }

    private String getFormattedUrl(int farm, String server, String id, String secret, int size) {
        return String.format(Locale.getDefault(), IMAGE_URL_FORMAT,
                farm, server, id, secret, getFormattedSize(size));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.owner);
        dest.writeString(this.secret);
        dest.writeString(this.server);
        dest.writeInt(this.farm);
        dest.writeString(this.title);
        dest.writeInt(this.isPublic);
        dest.writeInt(this.isFriend);
        dest.writeInt(this.isFamily);
    }

    private TAAPImage(Parcel in) {
        this.id = in.readString();
        this.owner = in.readString();
        this.secret = in.readString();
        this.server = in.readString();
        this.farm = in.readInt();
        this.title = in.readString();
        this.isPublic = in.readInt();
        this.isFriend = in.readInt();
        this.isFamily = in.readInt();
    }

    public static final Parcelable.Creator<TAAPImage> CREATOR = new Parcelable
            .Creator<TAAPImage>() {
        @Override
        public TAAPImage createFromParcel(Parcel source) {
            return new TAAPImage(source);
        }

        @Override
        public TAAPImage[] newArray(int size) {
            return new TAAPImage[size];
        }
    };

    @Override
    public String toString() {
        return String.format(Locale.getDefault(),
                "TAAPImage(%s: %s) [farm: %d, server: %s, id: %s, secret: %s] -> %s",
                owner, title, farm, server, id, secret, getImageUrl());
    }
}
