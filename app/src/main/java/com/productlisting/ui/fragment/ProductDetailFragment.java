package com.productlisting.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.productlisting.R;
import com.productlisting.http.Communicator;
import com.productlisting.http.apimodel.detail.DetailProduct;
import com.productlisting.http.apimodel.detail.DetailProductResponse;
import com.productlisting.root.App;
import com.productlisting.ui.adapter.ImageSliderAdapter;
import com.productlisting.util.ConnectivityUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProductDetailFragment extends Fragment {

    private long mProductId;
    @BindView(R.id.image_slider)
    public ViewPager mProductImageSlider;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.titleDesc)
    TextView description;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.empty)
    TextView empty;
    @BindView(R.id.detailView)
    LinearLayout detailLayout;
    @BindView(R.id.loadIndicator)
    ProgressBar progressBar;

    @Inject
    Communicator communicator;
    private String transitionName;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    /**
     * @param productId
     * @param imageViewTransition
     * @return A new instance of fragment ProductDetailFragment.
     */
    public static ProductDetailFragment newInstance(long productId, ImageView imageViewTransition) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM2, ViewCompat.getTransitionName(imageViewTransition));
        args.putLong(ARG_PARAM1, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);
        if (getArguments() != null) {
            mProductId = getArguments().getLong(ARG_PARAM1);
            transitionName = getArguments().getString(ARG_PARAM2);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        ButterKnife.bind(this, view);
        if (ConnectivityUtils.isConnected(getActivity())) {
            openDetailPage(mProductId);
            detailLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            empty.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void openDetailPage(long mProductId) {
        communicator.getProductDetail(getActivity(), mProductId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DetailProductResponse>() {

                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(DetailProductResponse productListResponse) {
                        onServerEvent(productListResponse.getProduct());
                    }
                });
    }

    private void onServerEvent(DetailProduct product) {
        if (product != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mProductImageSlider.setTransitionName(transitionName);
            }
            description.setText(product.getDesc());
            title.setText(product.getTitle());
            price.setText(String.format(getString(R.string.item_price),
                    String.valueOf(product.getPricing().getPrice())));
            mProductImageSlider.setAdapter(new ImageSliderAdapter(getActivity(), product));
        }
    }
}
