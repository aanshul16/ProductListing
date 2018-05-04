package com.productlisting.http.apimodel.detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailImage implements Parcelable {
    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public DetailImage() {
    }

    protected DetailImage(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<DetailImage> CREATOR = new Parcelable.Creator<DetailImage>() {
        @Override
        public DetailImage createFromParcel(Parcel source) {
            return new DetailImage(source);
        }

        @Override
        public DetailImage[] newArray(int size) {
            return new DetailImage[size];
        }
    };
}
