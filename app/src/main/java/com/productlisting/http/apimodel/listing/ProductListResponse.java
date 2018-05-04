package com.productlisting.http.apimodel.listing;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Success Response POJO
 */
public class ProductListResponse implements Parcelable {
    @SerializedName("products")
    @Expose
    private List<Product> products = null;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.products);
    }

    public ProductListResponse() {
    }

    protected ProductListResponse(Parcel in) {
        this.products = new ArrayList<Product>();
        in.readList(this.products, Product.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProductListResponse> CREATOR = new Parcelable.Creator<ProductListResponse>() {
        @Override
        public ProductListResponse createFromParcel(Parcel source) {
            return new ProductListResponse(source);
        }

        @Override
        public ProductListResponse[] newArray(int size) {
            return new ProductListResponse[size];
        }
    };
}
