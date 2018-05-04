package com.productlisting.ui.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.productlisting.R;
import com.productlisting.ui.adapter.ProductListingAdapter;
import com.productlisting.ui.fragment.ProductDetailFragment;
import com.productlisting.ui.fragment.ProductListingFragment;
import com.productlisting.util.FragmentManagerUtil;

import static com.productlisting.util.AppConstants.PRODUCT_DETAIL_PAGE;
import static com.productlisting.util.AppConstants.PRODUCT_LISTING_PAGE;

/**
 * Main Activity to handle for all image search
 */
public class ProductListingActivity extends AppCompatActivity implements ProductListingAdapter.OnListingItemClickListener {

    private ImageView imageViewTransition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_listing);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        productListingFragment();
    }

    private void productListingFragment() {
        ProductListingFragment productListingFragment = ProductListingFragment.newInstance();
        FragmentManagerUtil.createFragment(R.id.product_fragment, productListingFragment,
                PRODUCT_LISTING_PAGE, this, false, null);
    }

    private void productDetailFragment(Integer productId) {
        ProductDetailFragment productDetailFragment = ProductDetailFragment.newInstance(productId, imageViewTransition);
        FragmentManagerUtil.createFragment(R.id.product_fragment, productDetailFragment,
                PRODUCT_DETAIL_PAGE, this, true, imageViewTransition);
    }

    @Override
    public void onItemClick(Integer productId, ImageView itemView) {
        imageViewTransition = itemView;
        productDetailFragment(productId);
        Log.i("Anshul", "productId" + productId);
    }
}
