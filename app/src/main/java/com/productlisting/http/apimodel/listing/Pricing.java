package com.productlisting.http.apimodel.listing;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pricing implements Parcelable {

    @SerializedName("price")
    @Expose
    private Double price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.price);
    }

    public Pricing() {
    }

    protected Pricing(Parcel in) {
        this.price = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Pricing> CREATOR = new Parcelable.Creator<Pricing>() {
        @Override
        public Pricing createFromParcel(Parcel source) {
            return new Pricing(source);
        }

        @Override
        public Pricing[] newArray(int size) {
            return new Pricing[size];
        }
    };
}
