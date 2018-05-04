package com.productlisting.http.apimodel.detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailPricing implements Parcelable {
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

    public DetailPricing() {
    }

    protected DetailPricing(Parcel in) {
        this.price = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<DetailPricing> CREATOR = new Parcelable.Creator<DetailPricing>() {
        @Override
        public DetailPricing createFromParcel(Parcel source) {
            return new DetailPricing(source);
        }

        @Override
        public DetailPricing[] newArray(int size) {
            return new DetailPricing[size];
        }
    };
}
