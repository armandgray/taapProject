package com.armandgray.shared.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Locale;

/**
 * Created by armandgray on 7/13/18
 */
public class TAAPResponse implements Parcelable {

    @SerializedName("photos")
    @Expose
    private final FlickrResponseData data;

    @SuppressWarnings("unused")
    public TAAPResponse(FlickrResponseData data) {
        this.data = data;
    }

    public FlickrResponseData getData() {
        return data;
    }

    public static final class FlickrResponseData implements Parcelable {

        @Expose
        private final int page;

        @Expose
        private final int pages;

        @SuppressWarnings("SpellCheckingInspection")
        @SerializedName("perpage")
        @Expose
        private final int perPage;

        @Expose
        private final String total;

        @SerializedName("photo")
        @Expose
        private final List<TAAPImage> images;

        @SuppressWarnings("unused")
        public FlickrResponseData(int page, int pages, int perPage, String total,
                                  List<TAAPImage> images) {
            this.page = page;
            this.pages = pages;
            this.perPage = perPage;
            this.total = total;
            this.images = images;
        }

        @SuppressWarnings("unused")
        public int getPage() {
            return page;
        }

        public int getPages() {
            return pages;
        }

        @SuppressWarnings("unused")
        public int getPerPage() {
            return perPage;
        }

        @SuppressWarnings("unused")
        public String getTotal() {
            return total;
        }

        public List<TAAPImage> getImages() {
            return images;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.page);
            dest.writeInt(this.pages);
            dest.writeInt(this.perPage);
            dest.writeString(this.total);
            dest.writeTypedList(this.images);
        }

        FlickrResponseData(Parcel in) {
            this.page = in.readInt();
            this.pages = in.readInt();
            this.perPage = in.readInt();
            this.total = in.readString();
            this.images = in.createTypedArrayList(TAAPImage.CREATOR);
        }

        public static final Creator<FlickrResponseData> CREATOR = new Creator<FlickrResponseData>() {
            @Override
            public FlickrResponseData createFromParcel(Parcel source) {
                return new FlickrResponseData(source);
            }

            @Override
            public FlickrResponseData[] newArray(int size) {
                return new FlickrResponseData[size];
            }
        };


        @Override
        public String toString() {
            return String.format(Locale.getDefault(), "FlickrResponseData-Page %d/%d (%d/%s) -> %s",
                    page, pages, perPage, total, images);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }

    private TAAPResponse(Parcel in) {
        this.data = in.readParcelable(FlickrResponseData.class.getClassLoader());
    }

    public static final Creator<TAAPResponse> CREATOR = new Creator<TAAPResponse>() {
        @Override
        public TAAPResponse createFromParcel(Parcel source) {
            return new TAAPResponse(source);
        }

        @Override
        public TAAPResponse[] newArray(int size) {
            return new TAAPResponse[size];
        }
    };

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "TAAPResponse(Data: %s)", data);
    }
}
