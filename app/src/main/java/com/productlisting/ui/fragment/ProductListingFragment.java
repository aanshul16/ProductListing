package com.productlisting.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.productlisting.R;
import com.productlisting.http.Communicator;
import com.productlisting.http.apimodel.ErrorEvent;
import com.productlisting.http.apimodel.listing.Product;
import com.productlisting.http.apimodel.listing.ProductListResponse;
import com.productlisting.root.App;
import com.productlisting.ui.adapter.EndlessRecyclerOnScrollListener;
import com.productlisting.ui.adapter.ProductListingAdapter;
import com.productlisting.util.ConnectivityUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * UI which contain Grid View Adapter
 */
public class ProductListingFragment extends Fragment {

    private static final String TAG = "ProductListingFragment";
    private ProgressBar loadingIndicator;
    private RecyclerView recyclerView;
    private ProductListingAdapter myImageHoldingRecyclerViewAdapter;
    private ProductListingAdapter.OnListingItemClickListener itemClickListener;
    private TextView emptyTextView;
    private List<Product> productList;
    private Context context;

    @Inject
    Communicator communicator;


    public ProductListingFragment() {
    }

    public static ProductListingFragment newInstance() {
        return new ProductListingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getActivity().getApplication()).getComponent().inject(this);
        productList = new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProductListingAdapter.OnListingItemClickListener) {
            itemClickListener = (ProductListingAdapter.OnListingItemClickListener) context;
        } else {
            throw new RuntimeException("Class Cast Excecption");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_list, container, false);

        loadingIndicator = view.findViewById(R.id.loadIndicator);
        emptyTextView = view.findViewById(R.id.empty);

        // Set the adapter
        context = view.getContext();
        recyclerView = view.findViewById(R.id.recyclerView);
        myImageHoldingRecyclerViewAdapter = new ProductListingAdapter(productList, getActivity(), itemClickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(myImageHoldingRecyclerViewAdapter);
        if (ConnectivityUtils.isConnected(getActivity())) {
            addDataToList(0);
            loadingIndicator.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.VISIBLE);
            emptyTextView.setText(R.string.no_internet);
        }
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int page) {
                addDataToList(page);
                loadingIndicator.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    private void addDataToList(int page) {
        communicator.getImages(getActivity(), page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductListResponse>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorEvent errorEvent = new ErrorEvent("Not able to load images , try after some time");
                        onErrorEvent(errorEvent);
                    }

                    @Override
                    public void onNext(ProductListResponse productListResponse) {
                        onServerEvent(productListResponse.getProducts());
                    }
                });
    }

    /**
     * Receives Response in case of Success
     *
     * @param product
     */
    public void onServerEvent(List<Product> product) {
        loadingIndicator.setVisibility(View.GONE);
        updateUIPostExecute(product);
    }

    /**
     * Receives Response in case of failure
     *
     * @param errorEvent
     */
    public void onErrorEvent(ErrorEvent errorEvent) {
        loadingIndicator.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.VISIBLE);
        emptyTextView.setText(errorEvent.getErrorMsg());
        recyclerView.setVisibility(View.GONE);
    }

    /**
     * In validedate Adapter
     *
     * @param response
     */
    public void updateUIPostExecute(List<Product> response) {
        myImageHoldingRecyclerViewAdapter.setValues(response);
        myImageHoldingRecyclerViewAdapter.notifyDataSetChanged();
    }
}
