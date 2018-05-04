package com.productlisting.http;

import com.productlisting.http.apimodel.detail.DetailProductResponse;
import com.productlisting.http.apimodel.listing.ProductListResponse;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface MoreInfoApiService {
    @GET("search?")
    Observable<ProductListResponse> getImages(@QueryMap Map<String, Integer> options);

    @GET("{PRODUCT_ID}")
    Observable<DetailProductResponse> getProductDetail(@Path("PRODUCT_ID") long product_id);
}
