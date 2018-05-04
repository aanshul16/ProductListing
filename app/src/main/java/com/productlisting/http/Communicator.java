package com.productlisting.http;

import android.content.Context;

import com.productlisting.http.apimodel.detail.DetailProductResponse;
import com.productlisting.http.apimodel.listing.ProductListResponse;
import com.productlisting.util.ConnectivityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

import static com.productlisting.util.AppConstants.PAGE;
import static com.productlisting.util.AppConstants.PAGE_SIZE;

/**
 * Helper class to Hit API
 */
@Module
public class Communicator {

    private static final String LISTING_URL = "https://api.redmart.com/v1.6.0/catalog/";
    private static final String PRODUCT_URL = "https://api.redmart.com/v1.6.0/catalog/products/";

    @Provides
    public Communicator provideCommunicatorInstance() {
        return new Communicator();
    }

    private Retrofit retrofitInstance(String url) {
        //Here a logging interceptor is created
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(request);
            }
        });

        //The Retrofit builder will have the client attached, in order to get connection logs
        return new Retrofit.Builder()
                .client(httpClient.build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build();
    }

    public Observable<ProductListResponse> getImages(final Context context, int pageCount) {

        MoreInfoApiService service = retrofitInstance(LISTING_URL).create(MoreInfoApiService.class);
        Map<String, Integer> map = new HashMap<>();
        map.put(PAGE, pageCount);
        if (ConnectivityUtils.isConnectedFast(context)) {
            map.put(PAGE_SIZE, 20 + pageCount * 10);
        } else {
            map.put(PAGE_SIZE, 10 + +pageCount * 10);
        }

        return service.getImages(map);
    }

    public Observable<DetailProductResponse> getProductDetail(final Context context, long productId) {

        MoreInfoApiService service = retrofitInstance(PRODUCT_URL).create(MoreInfoApiService.class);
        return service.getProductDetail(productId);
    }

}
