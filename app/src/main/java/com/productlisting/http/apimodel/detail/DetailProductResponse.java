package com.productlisting.http.apimodel.detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailProductResponse implements Parcelable {
    @SerializedName("product")
    @Expose
    private DetailProduct product;

    public DetailProduct getProduct() {
        return product;
    }

    public void setProduct(DetailProduct product) {
        this.product = product;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.product, flags);
    }

    public DetailProductResponse() {
    }

    protected DetailProductResponse(Parcel in) {
        this.product = in.readParcelable(DetailProduct.class.getClassLoader());
    }

    public static final Parcelable.Creator<DetailProductResponse> CREATOR = new Parcelable.Creator<DetailProductResponse>() {
        @Override
        public DetailProductResponse createFromParcel(Parcel source) {
            return new DetailProductResponse(source);
        }

        @Override
        public DetailProductResponse[] newArray(int size) {
            return new DetailProductResponse[size];
        }
    };
}
