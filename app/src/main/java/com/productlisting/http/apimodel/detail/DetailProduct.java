package com.productlisting.http.apimodel.detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DetailProduct implements Parcelable {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("images")
    @Expose
    private List<DetailImage> images = null;
    @SerializedName("pricing")
    @Expose
    private DetailPricing pricing;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<DetailImage> getImages() {
        return images;
    }

    public void setImages(List<DetailImage> images) {
        this.images = images;
    }

    public DetailPricing getPricing() {
        return pricing;
    }

    public void setPricing(DetailPricing pricing) {
        this.pricing = pricing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeList(this.images);
        dest.writeParcelable(this.pricing, flags);
    }

    public DetailProduct() {
    }

    protected DetailProduct(Parcel in) {
        this.title = in.readString();
        this.desc = in.readString();
        this.images = new ArrayList<DetailImage>();
        in.readList(this.images, DetailImage.class.getClassLoader());
        this.pricing = in.readParcelable(DetailPricing.class.getClassLoader());
    }

    public static final Parcelable.Creator<DetailProduct> CREATOR = new Parcelable.Creator<DetailProduct>() {
        @Override
        public DetailProduct createFromParcel(Parcel source) {
            return new DetailProduct(source);
        }

        @Override
        public DetailProduct[] newArray(int size) {
            return new DetailProduct[size];
        }
    };
}
