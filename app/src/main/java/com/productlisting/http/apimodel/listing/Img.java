package com.productlisting.http.apimodel.listing;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Img implements Parcelable {

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

    public Img() {
    }

    protected Img(Parcel in) {
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Img> CREATOR = new Parcelable.Creator<Img>() {
        @Override
        public Img createFromParcel(Parcel source) {
            return new Img(source);
        }

        @Override
        public Img[] newArray(int size) {
            return new Img[size];
        }
    };
}
